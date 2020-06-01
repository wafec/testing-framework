package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;
import robtest.stateinterfw.data.IRepository;

public abstract class TestManager implements ITestManager {
    private IEnvironmentManager _environmentManager;
    private IMessageManager _messageManager;
    private ITargetStateMonitor _targetStateMonitor;
    private ITestDriver _testDriver;
    private IFaultManager _faultManager;

    private IRepository _repository;
    private ITestExecutionContextFactory _testExecutionContextFactory;

    public TestManager(IEnvironmentManager environmentManager,
                       IMessageManager messageManager,
                       ITargetStateMonitor targetStateMonitor,
                       ITestDriver testDriver,
                       IFaultManager faultManager,
                       IRepository repository,
                       ITestExecutionContextFactory testExecutionContextFactory) {
        this._environmentManager = environmentManager;
        this._messageManager = messageManager;
        this._targetStateMonitor = targetStateMonitor;
        this._testDriver = testDriver;
        this._faultManager = faultManager;
        this._repository = repository;
        this._testExecutionContextFactory = testExecutionContextFactory;
    }

    @Override
    public void handleGoldenRun(ITestCase testCase, ITestSpecs testSpecs) {
        try {
            ITestExecutionContext testExecutionContext = _testExecutionContextFactory.create(testCase, testSpecs);
            _repository.save((IEntity) testExecutionContext);
            _environmentManager.initialize(testExecutionContext);
            _messageManager.bind(testExecutionContext);
            _targetStateMonitor.monitor(testExecutionContext);
            _testDriver.initialize(testExecutionContext);
            while (_testDriver.hasNext()) {
                _testDriver.executeNext();
            }
        }
        finally {
            _testDriver.close();
            _targetStateMonitor.close();
            _messageManager.unbind();
            _environmentManager.clear();
        }
    }

    @Override
    public void handle(ITestCase testCase, ITestSpecs testSpecs, IFaultStrategy faultStrategy) {
        this.handleGoldenRun(testCase, testSpecs);
        this.handleFaultInjection(testCase, testSpecs, faultStrategy);
    }

    private void handleFaultInjection(ITestCase testCase, ITestSpecs testSpecs, IFaultStrategy faultStrategy) {
        ITestSuite testSuite = _faultManager.generate(testCase, faultStrategy);
        for (int i = 0; i < testSuite.size(); i++) {
            ITestCaseFault mutantTestCase = (ITestCaseFault) testSuite.get(i);
            try {
                ITestExecutionContext testExecutionContext = _testExecutionContextFactory.create(mutantTestCase, testSpecs);
                _repository.save((IEntity) testExecutionContext);
                _environmentManager.initialize(testExecutionContext);
                _messageManager.bind(testExecutionContext);
                _targetStateMonitor.monitor(testExecutionContext);
                _testDriver.initialize(testExecutionContext);
                while(_testDriver.hasNext()) {
                    _testDriver.executeNext();
                }
            }
            finally {
                _testDriver.close();
                _targetStateMonitor.close();
                _messageManager.unbind();
                _environmentManager.clear();
            }
        }
    }

    @Override
    public void replicate(ITestCase testCase, ITestSpecs testSpecs, IFaultStrategy faultStrategy) {
        handle(testCase.pureClone(), testSpecs, faultStrategy);
    }

    @Override
    public void handleOngoing(ITestCase testCase) {

    }
}
