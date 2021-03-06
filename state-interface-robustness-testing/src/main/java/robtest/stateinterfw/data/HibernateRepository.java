package robtest.stateinterfw.data;

import com.google.inject.Inject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.DisposableBean;
import robtest.stateinterfw.data.mapper.IDataMapper;

import java.util.List;

public class HibernateRepository implements IRepository, DisposableBean, ITransactionRepositoryFactory {
    protected final ISqlManager _sqlManager;
    protected final IDataMapper _mapper;
    protected boolean _commit = true;

    @Inject
    public HibernateRepository(ISqlManager sqlManager, IDataMapper mapper) {
        this._sqlManager = sqlManager;
        this._mapper = mapper;
    }

    @Override
    public void save(IEntity entity) {
        _sqlManager.createSession((ISqlTransactionSession) session -> {
            session.save(entity);
            return _commit;
        });
    }

    @Override
    public void update(IEntity entity) {
        _sqlManager.createSession((ISqlTransactionSession) session -> {
            session.update(entity);
            return _commit;
        });
    }

    @Override
    public void remove(IEntity entity) {
        _sqlManager.createSession((ISqlTransactionSession) session -> {
            session.remove(entity);
            return _commit;
        });
    }

    @Override
    public <T extends IData> List<T> query(String queryString, Class<T> entityClass, IParam[] parameters) {
        Object[] result = new Object[1];
        _sqlManager.createSession(session -> {
            Query<T> q = session.createQuery(queryString, entityClass);
            if (parameters != null) {
                for (IParam param : parameters) {
                    q.setParameter(param.getName(), param.getValue());
                }
            }
            result[0] = q.list();
            return false;
        });
        return (List<T>)result[0];
    }

    @Override
    public <T extends IData> T querySingleResult(String queryString, Class<T> entityClass, IParam... parameters) {
        List<T> result = query(queryString, entityClass, parameters);
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public Object querySingleResult(String queryString, ResultTransformer resultTransformer, IParam... parameters) {
        List result = query(queryString, resultTransformer, parameters);
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List query(String queryString, ResultTransformer resultTransformer, IParam[] parameters) {
        Object[] result = new Object[1];
        _sqlManager.createSession(session -> {
            Query q = session.createQuery(queryString);
            if (parameters != null) {
                for (IParam param : parameters) {
                    q.setParameter(param.getName(), param.getValue());
                }
            }
            result[0] = q.setResultTransformer(resultTransformer).getResultList();
            return false;
        });
        return (List)result[0];
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
    public void sync(IEntity entity) {
        _sqlManager.createSession(session -> {
            Object source = session.get(entity.getClass(), entity.getId());
            _mapper.map(source, entity);
            return false;
        });
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public ITransactionRepository getTransaction() {
        return new HibernateTransactionRepository(_sqlManager, this._mapper);
    }
}
