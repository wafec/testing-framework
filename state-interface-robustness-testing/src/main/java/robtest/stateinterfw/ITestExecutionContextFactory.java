package robtest.stateinterfw;

public interface ITestExecutionContextFactory {
    ITestExecutionContext create(ITestCase testCase);
    ITestExecutionContext create(ITestCase testCase, ITestInput testInput);
}
