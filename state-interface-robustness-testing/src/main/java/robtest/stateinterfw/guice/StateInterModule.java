package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.IStateInterCommandLine;
import robtest.stateinterfw.ITestManager;
import robtest.stateinterfw.StateInterCommandLine;
import robtest.stateinterfw.TestManager;

public class StateInterModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IStateInterCommandLine.class).to(StateInterCommandLine.class);
    }
}
