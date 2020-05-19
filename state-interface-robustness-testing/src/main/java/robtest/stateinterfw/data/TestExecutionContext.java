package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.ITestInput;
import robtest.stateinterfw.ITestSpecs;

public class TestExecutionContext implements ITestExecutionContext, IEntity {
    private int id;
    private TestCase testCase;
    private TestInput testInput;
    private TestCaseFault testCaseFault;
    private TestSpecs testSpecs;

    public TestExecutionContext() {

    }

    public TestExecutionContext(TestCase testCase, TestInput testInput) {
        this(testCase, testInput, null);
    }

    public TestExecutionContext(TestCase testCase, TestInput testInput, TestSpecs testSpecs) {
        this.testCase = testCase;
        this.testInput = testInput;
        this.testSpecs = testSpecs;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ITestCase get() {
        if (testCaseFault != null)
            return testCaseFault;
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public TestCase getTestCase() {
        return this.testCase;
    }

    @Override
    public ITestInput getCurrent() {
        return testInput;
    }

    @Override
    public ITestSpecs getSpecs() {
        return testSpecs;
    }

    public void setTestSpecs(TestSpecs testSpecs) {
        this.testSpecs = testSpecs;
    }

    public TestSpecs getTestSpecs() {
        return this.testSpecs;
    }

    public void setTestInput(TestInput testInput) {
        this.testInput = testInput;
    }

    public TestInput getTestInput() {
        return this.testInput;
    }

    public void setTestCaseFault(TestCaseFault testCaseFault) {
        this.testCaseFault = testCaseFault;
    }

    public TestCaseFault getTestCaseFault() {
        return testCaseFault;
    }
}
