package robtest.stateinterfw.data;

import org.hibernate.transform.ResultTransformer;

import java.util.List;

public interface IRepository {
    void save(IEntity entity);
    void update(IEntity entity);
    void remove(IEntity entity);
    <T extends IData> List<T>query(String queryString, Class<T> entityClass, IParam... parameters);
    List query(String queryString, ResultTransformer resultTransformer, IParam... parameters);
    <T extends IData> T querySingleResult(String queryString, Class<T> entityClass, IParam... parameters);
    Object querySingleResult(String queryString, ResultTransformer resultTransformer, IParam... parameters);
    <T extends IEntity> T get(int id, Class<T> entityClass);
    void sync(IEntity entity);
}
