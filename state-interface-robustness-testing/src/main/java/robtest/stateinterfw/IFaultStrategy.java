package robtest.stateinterfw;

public interface IFaultStrategy {
    ITestSuite apply(ITestSuite testSuite);
}
