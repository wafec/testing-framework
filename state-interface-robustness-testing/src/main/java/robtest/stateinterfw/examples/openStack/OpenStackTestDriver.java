package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.TestDriver;

public class OpenStackTestDriver extends TestDriver implements IOpenStackTestDriver {
    private ITestExecutionContext _testExecutionContext;
    private IOpenStackTestInputCommand _testInputCommand;
    private boolean _hasNext = true;

    @Inject
    public OpenStackTestDriver(IOpenStackTestInputCommand testInputCommand) {
        _testInputCommand = testInputCommand;
    }

    @Override
    public void initialize(ITestExecutionContext testExecutionContext) {
        _testExecutionContext = testExecutionContext;
    }

    @Override
    public void executeNext() {
        var testInput = this._testExecutionContext.getCurrent();
        if (testInput != null && _hasNext) {
            var result = _testInputCommand.command(_testExecutionContext, testInput);
        }
        _hasNext = _testExecutionContext.moveForward() >= 0;
    }

    @Override
    public boolean hasNext() {
        return _hasNext;
    }

    @Override
    public void close() {

    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
