package robtest.stateinterfw;

public interface ITestExecutionContext {
    ITestCase get();
    ITestInput getCurrent();
}
