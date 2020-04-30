package robtest.stateinterfw.examples.openStack.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.examples.openStack.*;

public class OpenStackModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IOpenStackCommandLine.class).to(OpenStackCommandLine.class);
        bind(IOpenStackTargetStateMonitor.class).to(OpenStackTargetStateMonitor.class);
        bind(IOpenStackTestDriver.class).to(OpenStackTestDriver.class);
        bind(IOpenStackTestManager.class).to(OpenStackTestManager.class);
    }
}
