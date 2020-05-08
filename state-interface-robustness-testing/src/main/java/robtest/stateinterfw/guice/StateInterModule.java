package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.*;
import robtest.stateinterfw.commandline.IStateInterCommandLine;
import robtest.stateinterfw.commandline.StateInterCommandLine;
import robtest.stateinterfw.data.MutantTestCaseFactory;
import robtest.stateinterfw.data.TestExecutionContextFactory;

public class StateInterModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IStateInterCommandLine.class).to(StateInterCommandLine.class);
        bind(ITestExecutionContextFactory.class).to(TestExecutionContextFactory.class);
        bind(IMutantTestCaseFactory.class).to(MutantTestCaseFactory.class);
    }
}
