package robtest.guice;

import com.google.inject.AbstractModule;
import robtest.CommandLine;
import robtest.ICommandLine;

public class RobTestModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ICommandLine.class).to(CommandLine.class);
    }
}
