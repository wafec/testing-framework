package robtest.stateinterfw.data.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import robtest.stateinterfw.data.*;
import robtest.stateinterfw.data.mapper.DataMapper;
import robtest.stateinterfw.data.mapper.IDataMapper;

public class DataModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ISqlManager.class).to(SqlManager.class).in(Scopes.SINGLETON);
        bind(IRepository.class).to(HibernateRepository.class);
        bind(ITransactionRepository.class).to(HibernateTransactionRepository.class);
        bind(IDataMapper.class).to(DataMapper.class);
    }
}
