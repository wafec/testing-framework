package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.ITestInput;

public class TestExecutionContext implements ITestExecutionContext, IEntity {
    private int id;
    private TestCase testCase;
    private TestInput testInput;
    private MutantTestCase mutantTestCase;

    public TestExecutionContext() {

    }

    public TestExecutionContext(TestCase testCase, TestInput testInput) {
        this.testCase = testCase;
        this.testInput = testInput;
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
        if (mutantTestCase != null)
            return mutantTestCase;
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

    public void setTestInput(TestInput testInput) {
        this.testInput = testInput;
    }

    public TestInput getTestInput() {
        return this.testInput;
    }

    public void setMutantTestCase(MutantTestCase mutantTestCase) {
        this.mutantTestCase = mutantTestCase;
    }

    public MutantTestCase getMutantTestCase() {
        return mutantTestCase;
    }
}
