package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.TestManager;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.rabbit.IRabbitMessageManager;
import robtest.stateinterfw.virtualbox.IVirtualBoxEnvironmentManager;

public class OpenStackTestManager extends TestManager implements IOpenStackTestManager {
    @Inject
    public OpenStackTestManager(IVirtualBoxEnvironmentManager environmentManager, IRabbitMessageManager messageManager,
                                IOpenStackTargetStateMonitor targetStateMonitor,
                                IOpenStackTestDriver testDriver,
                                IOpenStackFaultManager faultManager,
                                IRepository repository) {
        super(environmentManager, messageManager, targetStateMonitor,
                testDriver, faultManager, repository);
    }

    @Override
    public void handleGoldenRun(ITestExecutionContext testExecutionContext) {
        testExecutionContext.setVolatileUserContent(null);
        super.handleGoldenRun(testExecutionContext);
    }
}
