package robtest.stateinterfw;

public abstract class TargetStateMonitor implements ITargetStateMonitor {
    protected abstract ITestExecutionContext getTestExecutionContext();

    @Override
    public void notify(IState state) {

    }

    @Override
    public void notify(ITestStateOutput testStateOutput) {

    }
}
