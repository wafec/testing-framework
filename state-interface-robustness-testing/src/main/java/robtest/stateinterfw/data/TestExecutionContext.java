package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.ITestInput;
import robtest.stateinterfw.ITestSpecs;

import java.util.ArrayList;
import java.util.List;

public class TestExecutionContext implements ITestExecutionContext, IEntity {
    private int id;
    private TestCase testCase;
    private TestInput testInput;
    private TestCaseFault testCaseFault;
    private TestSpecs testSpecs;
    private Object volatileUserContent;
    private List<TestInput> inputs;
    private int inputIndex;

    public TestExecutionContext() {

    }

    public TestExecutionContext(TestCase testCase, TestInput testInput) {
        this(testCase, testInput, null);
    }

    public TestExecutionContext(TestCase testCase, TestInput testInput, TestSpecs testSpecs) {
        this.testCase = testCase;
        this.testInput = testInput;
        this.testSpecs = testSpecs;

        if (this.testCase != null && this.testCase.size() > 0) {
            inputs = new ArrayList<>(this.testCase.getTestInputs());
            inputIndex = 0;
            if (this.testInput != null) {
                inputIndex = inputs.indexOf(this.testInput);
            } else {
                this.testInput = inputs.get(0);
            }
        }
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
    public int moveForward() {
        if (inputIndex < inputs.size() - 1) {
            inputIndex++;
            this.testInput = inputs.get(inputIndex);
            return inputIndex;
        } else {
            return -1;
        }
    }

    @Override
    public int moveBackward() {
        if (inputIndex > 0) {
            inputIndex--;
            this.testInput = inputs.get(inputIndex);
            return inputIndex;
        } else {
            return -1;
        }
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

    public Object getVolatileUserContent() {
        return volatileUserContent;
    }

    public void setVolatileUserContent(Object volatileUserContent) {
        this.volatileUserContent = volatileUserContent;
    }
}
