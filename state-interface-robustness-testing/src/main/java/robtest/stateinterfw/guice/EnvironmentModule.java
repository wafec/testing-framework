package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.IEnvironmentManager;
import robtest.stateinterfw.IStarter;
import robtest.stateinterfw.Starter;
import robtest.stateinterfw.virtualbox.VirtualBoxEnvironmentManager;

public class EnvironmentModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IEnvironmentManager.class).to(VirtualBoxEnvironmentManager.class);
        bind(IStarter.class).to(Starter.class);
    }
}
