package robtest.stateinterfw.faults.strategies;

import robtest.stateinterfw.ITestSuite;

public class FullStrategy implements IFullStrategy {
    @Override
    public ITestSuite apply(ITestSuite testSuite) {
        return testSuite;
    }
}
