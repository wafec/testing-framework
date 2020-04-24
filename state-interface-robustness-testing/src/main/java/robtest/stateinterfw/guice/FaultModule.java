package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.FaultManager;
import robtest.stateinterfw.IFaultManager;

public class FaultModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IFaultManager.class).to(FaultManager.class);
    }
}
