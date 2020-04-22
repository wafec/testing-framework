package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.data.HbRepository;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.ISqlManager;
import robtest.stateinterfw.data.SqlManager;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRepository.class).to(HbRepository.class);
        bind(ISqlManager.class).to(SqlManager.class);
    }
}
