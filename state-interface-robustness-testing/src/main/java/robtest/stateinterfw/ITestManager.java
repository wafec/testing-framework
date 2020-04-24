package robtest.stateinterfw;

public interface ITestManager {
    void handle(ITestCase testCase, ITestSpecs testSpecs);
    void replicate(ITestCase testCase, ITestSpecs testSpecs);
    void handleOngoing(ITestCase testCase);
}
