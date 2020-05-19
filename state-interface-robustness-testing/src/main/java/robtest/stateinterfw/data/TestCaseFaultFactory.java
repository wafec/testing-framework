package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestCaseFault;
import robtest.stateinterfw.ITestCaseFaultFactory;
import robtest.stateinterfw.ITestCase;

public class TestCaseFaultFactory implements ITestCaseFaultFactory {
    @Override
    public ITestCaseFault create(ITestCase testCase) {
        TestCaseFault testCaseFault = new TestCaseFault();
        testCaseFault.setOriginalTestCase((TestCase) testCase);
        return testCaseFault;
    }
}
