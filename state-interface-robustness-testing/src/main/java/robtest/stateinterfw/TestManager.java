package robtest.stateinterfw;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IEntity;
import robtest.stateinterfw.data.IRepository;

public class TestManager implements ITestManager {
    private IEnvironmentManager _environmentManager;
    private IMessagingDriver _messagingDriver;
    private IStateMonitoringDriver _stateMonitoringDriver;
    private ITestDriver _testDriver;
    private IFaultManager _faultManager;

    private IRepository _repository;
    private ITestExecutionContextFactory _testExecutionContextFactory;

    @Inject
    public TestManager(IEnvironmentManager environmentManager,
                       IMessagingDriver messagingDriver,
                       IStateMonitoringDriver stateMonitoringDriver,
                       ITestDriver testDriver,
                       IFaultManager faultManager,
                       IRepository repository,
                       ITestExecutionContextFactory testExecutionContextFactory) {
        this._environmentManager = environmentManager;
        this._messagingDriver = messagingDriver;
        this._stateMonitoringDriver = stateMonitoringDriver;
        this._testDriver = testDriver;
        this._faultManager = faultManager;
        this._repository = repository;
        this._testExecutionContextFactory = testExecutionContextFactory;
    }

    private void handleGoldenRun(ITestCase testCase) {
        try {
            ITestExecutionContext testExecutionContext = _testExecutionContextFactory.create(testCase);
            _repository.save((IEntity) testExecutionContext);
            _environmentManager.initialize(testExecutionContext);
            _messagingDriver.bind(testExecutionContext);
            _stateMonitoringDriver.monitor(testExecutionContext);
            _testDriver.initialize(testExecutionContext);
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
                ITestExecutionContext testExecutionContext = _testExecutionContextFactory.create(mutantTestCase);
                _repository.save((IEntity) testExecutionContext);
                _environmentManager.initialize(testExecutionContext);
                _messagingDriver.bind(testExecutionContext);
                _stateMonitoringDriver.monitor(testExecutionContext);
                _testDriver.initialize(testExecutionContext);
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
