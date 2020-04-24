package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.ITestExecutionContextFactory;
import robtest.stateinterfw.data.*;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRepository.class).to(HbRepository.class);
        bind(ISqlManager.class).to(SqlManager.class);
        bind(ITestExecutionContextFactory.class).to(TestExecutionContextFactory.class);
    }
}
