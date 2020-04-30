package robtest.stateinterfw.examples.openStack;

import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.TargetStateMonitor;

public class OpenStackTargetStateMonitor extends TargetStateMonitor implements IOpenStackTargetStateMonitor {
    private ITestExecutionContext _testExecutionContext;

    @Override
    public void monitor(ITestExecutionContext testExecutionContext) {
        this._testExecutionContext = testExecutionContext;
    }

    @Override
    public void close() {

    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
