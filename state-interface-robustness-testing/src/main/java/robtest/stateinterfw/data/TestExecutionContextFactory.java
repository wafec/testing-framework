package robtest.stateinterfw.data;

import robtest.stateinterfw.*;

public class TestExecutionContextFactory implements ITestExecutionContextFactory {
    @Override
    public ITestExecutionContext create(ITestCase testCase, ITestSpecs testSpecs) {
        return create(testCase, testSpecs, null);
    }

    @Override
    public ITestExecutionContext create(ITestCase testCase, ITestSpecs testSpecs, ITestInput testInput) {
        TestExecutionContext testExecutionContext = new TestExecutionContext();
        if (testCase instanceof TestCase)
            testExecutionContext.setTestCase((TestCase) testCase);
        else
            testExecutionContext.setTestCaseFault((TestCaseFault) testCase);
        testExecutionContext.setTestInput((TestInput) testInput);
        testExecutionContext.setTestSpecs((TestSpecs) testSpecs);
        return testExecutionContext;
    }
}
