package robtest.guice;

import com.google.inject.AbstractModule;
import robtest.CommandLine;
import robtest.ICommandLine;
import robtest.os.cli.commandline.IOSCommandLine;
import robtest.os.cli.commandline.OSCommandLine;

public class RobTestModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ICommandLine.class).to(CommandLine.class);
        bind(IOSCommandLine.class).to(OSCommandLine.class);
    }
}
