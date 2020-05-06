package robtest.stateinterfw;

public interface ITestManager {
    void handle(ITestCase testCase, ITestSpecs testSpecs, IFaultStrategy faultStrategy);
    void replicate(ITestCase testCase, ITestSpecs testSpecs, IFaultStrategy faultStrategy);
    void handleOngoing(ITestCase testCase);
}
