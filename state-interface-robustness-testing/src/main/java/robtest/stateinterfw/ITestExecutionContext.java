package robtest.stateinterfw;

public interface ITestExecutionContext {
    int getId();
    ITestCase get();
    ITestInput getCurrent();
}
