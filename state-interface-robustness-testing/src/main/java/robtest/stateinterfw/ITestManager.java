package robtest.stateinterfw;

public interface ITestManager {
    void handle(ITestCase testCase);
    void replicate(ITestCase testCase);
}
