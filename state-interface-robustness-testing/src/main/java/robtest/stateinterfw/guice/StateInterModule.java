package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.IStateInterCommandLine;
import robtest.stateinterfw.StateInterCommandLine;

public class StateInterModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IStateInterCommandLine.class).to(StateInterCommandLine.class);

    }
}
