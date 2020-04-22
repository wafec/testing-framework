package robtest.stateinterfw;

import com.google.inject.Inject;

public class Starter implements IStarter {
    private ITestManager _testManager;

    @Inject
    public Starter(ITestManager testManager) {
        _testManager = testManager;
    }

    @Override
    public void start() {

    }
}
