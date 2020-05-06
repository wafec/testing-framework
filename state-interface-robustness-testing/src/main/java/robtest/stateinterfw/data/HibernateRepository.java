package robtest.stateinterfw.data;

import com.google.inject.Inject;
import robtest.stateinterfw.data.mapper.IDataMapper;

import java.util.List;

public class HibernateRepository implements IRepository {
    private ISqlManager _sqlManager;
    private IDataMapper _mapper;

    @Inject
    public HibernateRepository(ISqlManager sqlManager, IDataMapper mapper) {
        this._sqlManager = sqlManager;
        this._mapper = mapper;
    }

    @Override
    public void save(IEntity entity) {
        _sqlManager.createSession(session -> {
            session.save(entity);
            return true;
        });
    }

    @Override
    public void update(IEntity entity) {
        _sqlManager.createSession(session -> {
            session.update(entity);
            return true;
        });
    }

    @Override
    public void remove(IEntity entity) {
        _sqlManager.createSession(session -> {
            session.remove(entity);
            return true;
        });
    }

    @Override
    public <T extends IData> List<T> Query(String queryString, Class<T> entityClass) {
        Object[] result = new Object[1];
        _sqlManager.createSession(session -> {
            result[0] = session.createQuery(queryString, entityClass).list();
            return false;
        });
        return (List<T>)result[0];
    }

    @Override
    public <T extends IEntity> T get(int id, Class<T> entityClass) {
        Object[] result = new Object[1];
        _sqlManager.createSession(session -> {
            result[0] = session.get(entityClass, id);
            return false;
        });
        return (T)result[0];
    }

    @Override
    public void Synchronize(IEntity entity) {
        _sqlManager.createSession(session -> {
            Object source = session.get(entity.getClass(), entity.getId());
            _mapper.map(source, entity);
            return false;
        });
    }
}
