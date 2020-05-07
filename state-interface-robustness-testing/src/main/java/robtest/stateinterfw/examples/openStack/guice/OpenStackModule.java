package robtest.stateinterfw.examples.openStack.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.examples.openStack.*;
import robtest.stateinterfw.faults.DefaultMutatorCatalog;

public class OpenStackModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IOpenStackCommandLine.class).to(OpenStackCommandLine.class);
        bind(IOpenStackTargetStateMonitor.class).to(OpenStackTargetStateMonitor.class);
        bind(IOpenStackTestDriver.class).to(OpenStackTestDriver.class);
        bind(IOpenStackTestManager.class).to(OpenStackTestManager.class);
        bind(IOpenStackFaultManager.class).to(OpenStackFaultManager.class);
        bind(IOpenStackMutatorCatalog.class).to(OpenStackMutatorCatalog.class);
        bind(IOpenStackFaultMutatorFilter.class).to(OpenStackFaultMutatorFilter.class);
        bind(IOpenStackMutatorMessageUtils.class).to(OpenStackMutatorMessageUtils.class);
    }
}
