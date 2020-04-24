package robtest.stateinterfw;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IEntity;
import robtest.stateinterfw.data.IRepository;

public class TestManager implements ITestManager {
    private IEnvironmentManager _environmentManager;
    private IMessageManager _messageManager;
    private ITargetStateMonitor _targetStateMonitor;
    private ITestDriver _testDriver;
    private IFaultManager _faultManager;

    private IRepository _repository;
    private ITestExecutionContextFactory _testExecutionContextFactory;

    @Inject
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

    private void handleGoldenRun(ITestCase testCase, ITestSpecs testSpecs) {
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
    public void handle(ITestCase testCase, ITestSpecs testSpecs) {
        this.handleGoldenRun(testCase, testSpecs);
        this.handleFaultInjection(testCase, testSpecs);
    }

    private void handleFaultInjection(ITestCase testCase, ITestSpecs testSpecs) {
        ITestSuite testSuite = _faultManager.generate(testCase);
        for (int i = 0; i < testSuite.size(); i++) {
            IMutantTestCase mutantTestCase = (IMutantTestCase) testSuite.get(i);
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
    public void replicate(ITestCase testCase, ITestSpecs testSpecs) {
        handle(testCase.pureClone(), testSpecs);
    }

    @Override
    public void handleOngoing(ITestCase testCase) {

    }
}
