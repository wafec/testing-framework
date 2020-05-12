package robtest.stateinterfw.files.mapper;

import com.google.inject.Inject;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestInput;
import robtest.stateinterfw.ITestInputArgument;
import robtest.stateinterfw.ITestState;
import robtest.stateinterfw.data.*;
import robtest.stateinterfw.files.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FileMapper implements IFileMapper {
    private IRepository _repository;

    @Inject
    public FileMapper(IRepository repository) {
        _repository = repository;
    }

    @Override
    public <T extends IData> T map(IFileObject fileObject, Class<T> dataClass) {
        if ((dataClass.equals(TestCase.class) || dataClass.equals(ITestCase.class)) &&
            fileObject instanceof IFileTestCase) {
            TestCase testCase = new TestCase();
            return (T) map((IFileTestCase) fileObject, testCase);
        }
        throw new UnsupportedOperationException();
    }

    private ITestCase map(IFileTestCase fileTestCase, TestCase testCase) {
        testCase.setUniqueIdentifier(fileTestCase.getUniqueIdentifier());
        List<TestInput> testInputs = new ArrayList<>();
        for (int i = 0; i < fileTestCase.size(); i++) {
            IFileTestInput fileTestInput = fileTestCase.get(i);
            TestInput testInput = new TestInput();
            map(fileTestInput, testInput);
            testInput.setTestCase(testCase);
            testInputs.add(testInput);
        }
        testCase.setTestInputs(new HashSet<>(testInputs));
        return testCase;
    }

    private ITestInput map(IFileTestInput fileTestInput, TestInput testInput) {
        testInput.setAction(fileTestInput.getAction());
        List<TestInputArgument> testInputArguments = new ArrayList<>();
        for (int i = 0; i < fileTestInput.getArgs().size(); i++) {
            IFileTestInputArgument fileTestInputArgument = fileTestInput.getArgs().get(i);
            TestInputArgument testInputArgument = new TestInputArgument();
            map(fileTestInputArgument, testInputArgument);
            testInputArgument.setTestInput(testInput);
            testInputArguments.add(testInputArgument);
        }
        testInput.setTestInputArguments(new HashSet<>(testInputArguments));
        List<TestState> testStates = new ArrayList<>();
        for (int i = 0; i < fileTestInput.getStates().size(); i++) {
            IFileTestState fileTestState = fileTestInput.getStates().get(i);
            TestState testState = new TestState();
            map(fileTestState, testState);
            testState.setTestInput(testInput);
            testStates.add(testState);
        }
        testInput.setTestStates(new HashSet<>(testStates));
        return testInput;
    }

    private ITestInputArgument map(IFileTestInputArgument fileTestInputArgument, TestInputArgument testInputArgument) {
        testInputArgument.setName(fileTestInputArgument.getName());
        testInputArgument.setDataType(fileTestInputArgument.getDataType());
        testInputArgument.setDataValue(fileTestInputArgument.getDataValue());
        return testInputArgument;
    }

    private ITestState map(IFileTestState fileTestState, TestState testState) {
        State state = _repository.get(fileTestState.getStateId(), State.class);
        if (state == null)
            throw new IllegalArgumentException(String.format("State %d not found", fileTestState.getStateId()));
        testState.setState(state);
        testState.setOrder(fileTestState.getOrder());
       return testState;
    }
}
