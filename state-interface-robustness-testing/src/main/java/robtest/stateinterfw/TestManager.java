package robtest.stateinterfw;

public class TestManager implements ITestManager {
    private IEnvironmentManager _environmentManager;
    private IMessagingDriver _messagingDriver;
    private IStateMonitoringDriver _stateMonitoringDriver;
    private ITestDriver _testDriver;
    private IFaultManager _faultManager;

    public TestManager(IEnvironmentManager environmentManager,
                       IMessagingDriver messagingDriver,
                       IStateMonitoringDriver stateMonitoringDriver,
                       ITestDriver testDriver,
                       IFaultManager faultManager) {
        this._environmentManager = environmentManager;
        this._messagingDriver = messagingDriver;
        this._stateMonitoringDriver = stateMonitoringDriver;
        this._testDriver = testDriver;
        this._faultManager = faultManager;
    }

    private void handleGoldenRun(ITestCase testCase) {
        try {
            _environmentManager.initialize(testCase);
            _messagingDriver.bind(testCase);
            _stateMonitoringDriver.monitor(testCase);
            _testDriver.initialize(testCase);
            while (_testDriver.hasNext()) {
                _testDriver.executeNext();
            }
        }
        finally {
            _testDriver.close();
            _stateMonitoringDriver.close();
            _messagingDriver.unbind();
            _environmentManager.clear();
        }
    }

    @Override
    public void handle(ITestCase testCase) {
        this.handleGoldenRun(testCase);
        this.handleFaultInjection(testCase);
    }

    private void handleFaultInjection(ITestCase testCase) {
        ITestSuite testSuite = _faultManager.generate(testCase);
        for (int i = 0; i < testSuite.size(); i++) {
            IMutantTestCase mutantTestCase = (IMutantTestCase) testSuite.get(i);
            try {
                _environmentManager.initialize(mutantTestCase);
                _messagingDriver.bind(mutantTestCase);
                _stateMonitoringDriver.monitor(mutantTestCase);
                _testDriver.initialize(mutantTestCase);
                while(_testDriver.hasNext()) {
                    _testDriver.executeNext();
                }
            }
            finally {
                _testDriver.close();
                _stateMonitoringDriver.close();
                _messagingDriver.unbind();
                _environmentManager.clear();
            }
        }
    }

    @Override
    public void replicate(ITestCase testCase) {
        handle(testCase.pureClone());
    }
}
