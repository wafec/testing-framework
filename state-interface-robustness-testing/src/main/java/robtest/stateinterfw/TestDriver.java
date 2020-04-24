package robtest.stateinterfw;

public class TestDriver implements ITestDriver {
    private ITestExecutionContext _testExecutionContext;

    public TestDriver() {
        _testExecutionContext = null;
    }

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
}
