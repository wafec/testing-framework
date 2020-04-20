package robtest.stateinterfw.hbm;

import robtest.stateinterfw.IMutantTestCase;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestInput;

public class MutantTestCase implements IMutantTestCase {
    private int id;
    private TestCase originalTestCase;

    public MutantTestCase(TestCase originalTestCase) {
        this.originalTestCase = originalTestCase;
    }

    public MutantTestCase() {

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
        return new MutantTestCase((TestCase)testClone);
    }
}
