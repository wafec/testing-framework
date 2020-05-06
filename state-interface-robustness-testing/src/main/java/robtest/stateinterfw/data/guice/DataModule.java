package robtest.stateinterfw.data.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.data.HibernateRepository;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.ISqlManager;
import robtest.stateinterfw.data.SqlManager;
import robtest.stateinterfw.data.mapper.DataMapper;
import robtest.stateinterfw.data.mapper.IDataMapper;

public class DataModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ISqlManager.class).to(SqlManager.class);
        bind(IRepository.class).to(HibernateRepository.class);
        bind(IDataMapper.class).to(DataMapper.class);
    }
}
