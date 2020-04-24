package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.*;
import robtest.stateinterfw.files.ITestCaseLoader;
import robtest.stateinterfw.files.jackson.TestCaseLoader;

public class FrameworkModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ITestDriver.class).to(TestDriver.class);
        bind(ITargetStateMonitor.class).to(TargetStateMonitor.class);
        bind(IMessageManager.class).to(RabbitMessageManager.class);
        bind(ITestCaseLoader.class).to(TestCaseLoader.class);
    }
}
