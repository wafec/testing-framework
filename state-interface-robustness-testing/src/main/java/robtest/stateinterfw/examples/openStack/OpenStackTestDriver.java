package robtest.stateinterfw.examples.openStack;

import robtest.stateinterfw.ITestDriver;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.TestDriver;

public class OpenStackTestDriver extends TestDriver implements IOpenStackTestDriver {
    private ITestExecutionContext _testExecutionContext;

    @Override
    public void initialize(ITestExecutionContext testExecutionContext) {
        _testExecutionContext = testExecutionContext;
    }

    @Override
    public void executeNext() {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
