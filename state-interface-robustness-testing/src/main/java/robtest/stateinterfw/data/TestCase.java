package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestInput;

import java.util.Iterator;
import java.util.Set;

public class TestCase implements ITestCase {
    private int id;
    private Set<TestInput> testInputs;

    public TestCase() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public ITestInput get(int index) {
        if (index < 0 || index >= testInputs.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i = 0;
        ITestInput result = null;
        for (Iterator<TestInput> it = testInputs.iterator(); it.hasNext() && i <= index; i++) {
            result = it.next();
        }
        return result;
    }

    @Override
    public int size() {
        return testInputs.size();
    }

    @Override
    public ITestCase pureClone() {
        throw new UnsupportedOperationException();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<TestInput> getTestInputs() {
        return this.testInputs;
    }

    public void setTestInputs(Set<TestInput> testInputs) {
        this.testInputs = testInputs;
    }
}
