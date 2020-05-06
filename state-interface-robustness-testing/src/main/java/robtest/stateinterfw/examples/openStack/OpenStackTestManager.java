package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.IFaultManager;
import robtest.stateinterfw.ITestExecutionContextFactory;
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
                                IRepository repository,
                                ITestExecutionContextFactory testExecutionContextFactory) {
        super(environmentManager, messageManager, targetStateMonitor,
                testDriver, faultManager, repository, testExecutionContextFactory);
    }
}
