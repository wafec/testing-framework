package robtest.stateinterfw;

public abstract class TestDriver implements ITestDriver {
    protected abstract ITestExecutionContext getTestExecutionContext();
}
