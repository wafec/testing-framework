package robtest.stateinterfw.data;

import org.hibernate.transform.ResultTransformer;

import java.util.List;

public interface IRepository {
    void save(IEntity entity);
    void update(IEntity entity);
    void remove(IEntity entity);
    <T extends IData> List<T> query(String queryString, Class<T> entityClass);
    List query(String queryString, ResultTransformer resultTransformer);
    <T extends IEntity> T get(int id, Class<T> entityClass);
    void Synchronize(IEntity entity);
}
