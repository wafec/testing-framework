package robtest.stateinterfw.files.text;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.State;
import robtest.stateinterfw.data.TestCase;
import robtest.stateinterfw.files.AbstractTestCaseLoader;
import robtest.stateinterfw.files.IFileTestCase;
import robtest.stateinterfw.files.IFileTestInput;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.files.jackson.FileTestCase;
import robtest.stateinterfw.files.jackson.FileTestInput;
import robtest.stateinterfw.files.jackson.FileTestInputArgument;
import robtest.stateinterfw.files.jackson.FileTestState;
import robtest.stateinterfw.files.mapper.IFileMapper;
import robtest.stateinterfw.services.IStateManageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

public class PureTestCaseLoader extends AbstractTestCaseLoader implements ITextTestCaseLoader {
    private final IStateManageService _stateManageService;

    @Inject
    public PureTestCaseLoader(IRepository repository, IFileMapper fileMapper, IStateManageService stateManageService) {
        super(fileMapper, repository);
        this._stateManageService = stateManageService;
    }

    @Override
    public IFileTestCase load(String path) {
        System.out.println(String.format("PureTestCaseLoader %s", path));
        try {
            FileTestCase testCase = new FileTestCase();
            testCase.setFileInputs(new ArrayList<>());
            for(var line : Files.readAllLines(Path.of(path))) {
                var args = line.split(" ");
                var lineIdentifier = args[0];
                switch (lineIdentifier) {
                    case "?": parseQuestion(args, testCase); break;
                    case "I": parseInput(args, testCase); break;
                    case "S": parseState(args, testCase); break;
                }
            }
            defineStateOrder(testCase);
            return testCase;
        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    protected void defineStateOrder(FileTestCase testCase) {
        for (var input : testCase.getFileInputs()) {
            for (int i = 0; i < input.getStates().size(); i++) {
                FileTestState state = (FileTestState) input.getStates().get(i);
                state.setOrder(i);
            }
        }
    }

    protected void parseQuestion(String[] args, FileTestCase testCase) {
        ArgPairCollection pairs = ArgPairCollection.parse(args);
        var pair = pairs.get("name");
        testCase.setUniqueIdentifier(pair.getValue());
    }

    protected void parseInput(String[] args, FileTestCase testCase) {
        System.out.println(String.join(" ", args));
        ArgPairCollection pairs = ArgPairCollection.parse(args);
        var event = args[1];
        FileTestInput testInput = new FileTestInput();
        testInput.setAction(event);
        var locked = Optional.ofNullable(pairs.get("locked")).map(ArgPair::getValue).map(String::toLowerCase).map(s -> s.equals("true")).orElse(false);
        pairs.remove("locked");
        testInput.setLocked(locked);
        testInput.setArguments(new ArrayList<>());
        testInput.setFileStates(new ArrayList<>());
        parseArgs(args, testInput);
        testCase.getFileInputs().add(testInput);
    }

    protected void parseArgs(String[] args, FileTestInput testInput) {
        ArgPairCollection pairs = ArgPairCollection.parse(args);
        for (var argument : pairs.getList()) {
            FileTestInputArgument inputArgument = new FileTestInputArgument();
            inputArgument.setName(argument.getName());
            inputArgument.setDataValue(argument.getValue());
            inputArgument.setDataType(Optional.ofNullable(argument.getModifier()).orElse("string"));
            testInput.getArguments().add(inputArgument);
        }
    }

    protected void parseState(String[] args, FileTestCase testCase) {
        ArgPairCollection pairs = ArgPairCollection.parse(args);
        var stateName = args[1];
        FileTestState state = new FileTestState();
        State stateData = new State();
        stateData.setName(stateName);
        stateData.setRequired(pairs.getValue("required").map(String::toLowerCase).map(s -> s.equals("true")).orElse(false));
        stateData.setTimeout(pairs.getValue("timeout").map(Integer::parseInt).orElse(60));
        _stateManageService.addOrUpdate(stateData);
        state.setStateId(stateData.getId());
        FileTestInput testInput = (FileTestInput) testCase.getLast();
        testInput.getFileStates().add(state);
    }
}
