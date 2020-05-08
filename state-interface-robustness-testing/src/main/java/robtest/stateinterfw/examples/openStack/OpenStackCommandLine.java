package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import org.apache.commons.cli.*;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestSpecs;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.TestCase;
import robtest.stateinterfw.data.TestSpecs;

public class OpenStackCommandLine implements IOpenStackCommandLine {
    private IOpenStackTestManager _openStackTestManager;
    private IRepository _repository;

    @Inject
    public OpenStackCommandLine(IOpenStackTestManager openStackTestManager,
                                IRepository repository) {
        this._openStackTestManager = openStackTestManager;
        this._repository = repository;
    }

    private void startTestManager(int caseId, int specsId) {
        System.out.println(String.format("Case: %d, Specs: %d\n", caseId, specsId));
        ITestCase testCase = _repository.get(caseId, TestCase.class);
        ITestSpecs testSpecs = _repository.get(specsId, TestSpecs.class);
        _openStackTestManager.handle(testCase, testSpecs, null);
    }

    @Override
    public void run(String... args) {
        System.out.println("Working");
        Options options = new Options();
        options.addOption(Option.builder()
                .longOpt("case")
                .required()
                .hasArg()
        .build());
        options.addOption(Option.builder()
                .longOpt("specs")
                .required()
                .hasArg()
        .build());
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            String caseOptValue = commandLine.getOptionValue("case");
            String specsOptValue = commandLine.getOptionValue("specs");
            startTestManager(Integer.parseInt(caseOptValue), Integer.parseInt(specsOptValue));
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }
}
