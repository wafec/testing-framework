package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestCaseFault;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestInput;

import java.util.Iterator;

public class TestCaseFault implements ITestCaseFault {
    private int id;
    private TestCase originalTestCase;

    public TestCaseFault(TestCase originalTestCase) {
        this.originalTestCase = originalTestCase;
    }

    public TestCaseFault() {

    }

    @Override
    public ITestCase getOriginalTestCase() {
        return originalTestCase;
    }

    public void setOriginalTestCase(TestCase originalTestCase) {
        this.originalTestCase = originalTestCase;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ITestInput get(int index) {
        if (originalTestCase != null) {
            return originalTestCase.get(index);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public int size() {
        if (originalTestCase != null) {
            return originalTestCase.size();
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public ITestCase pureClone() {
        if (originalTestCase == null)
            throw new NullPointerException();
        ITestCase testClone = originalTestCase.pureClone();
        return new TestCaseFault((TestCase)testClone);
    }

    @Override
    public String getUniqueIdentifier() {
        if (originalTestCase == null)
            throw new NullPointerException();
        return originalTestCase.getUniqueIdentifier();
    }

    @Override
    public Iterator<ITestInput> iterator() {
        if (originalTestCase != null) {
            return originalTestCase.iterator();
        } else {
            return null;
        }
    }
}
