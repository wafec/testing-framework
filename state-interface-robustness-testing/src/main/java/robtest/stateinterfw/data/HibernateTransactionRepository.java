package robtest.stateinterfw.data;

import com.google.inject.Inject;
import robtest.stateinterfw.data.mapper.IDataMapper;

public class HibernateTransactionRepository extends HibernateRepository implements ITransactionRepository {
    @Inject
    public HibernateTransactionRepository(ISqlManager sqlManager, IDataMapper dataMapper) {
        super(sqlManager, dataMapper);
        this._commit = false;
    }

    @Override
    public void close() throws Exception {
        if (this._sqlManager != null) {
            this._sqlManager.commit();
        }
    }
}
