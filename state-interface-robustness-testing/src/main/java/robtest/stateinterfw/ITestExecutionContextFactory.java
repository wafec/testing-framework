package robtest.stateinterfw;

public interface ITestExecutionContextFactory {
    ITestExecutionContext create(ITestCase testCase, ITestSpecs testSpecs);
    ITestExecutionContext create(ITestCase testCase, ITestSpecs testSpecs, ITestInput testInput);
}
