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
                if (inputIndex < 0) {
                    inputIndex = 0;
                    testInput = (TestInput) testCase.get(0);
                }
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
        if (testInput == null) {
            if (testCase.size() > 0) {
                testInput = (TestInput) testCase.get(0);
                inputIndex = 0;
            }
        } else {
            var list = new ArrayList<>(this.testCase.getTestInputs());
            inputIndex = list.indexOf(testInput);
            if (inputIndex < 0) {
                inputIndex = 0;
                testInput = (TestInput) testCase.get(0);
            }
        }
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
        if (testCase == null)
            testCase = testInput.getTestCase();
        var list = new ArrayList<>(testCase.getTestInputs());
        var index = list.indexOf(testInput);
        if (index == -1) {
            testCase = testInput.getTestCase();
            list  = new ArrayList<>(testCase.getTestInputs());
            index = list.indexOf(testInput);
        }
        inputIndex = index;
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
