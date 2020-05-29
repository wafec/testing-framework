package robtest.stateinterfw.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.DisposableBean;
import robtest.stateinterfw.data.ISqlManager;
import robtest.stateinterfw.data.ISqlSession;

public class SqlManager implements ISqlManager, DisposableBean  {
    private static SessionFactory sessionFactory = new Configuration().configure("hbm/hibernate.cfg.xml").buildSessionFactory();

    private Session session;

    public SqlManager() {

    }

    public void createSession(ISqlSession sqlSession)
    {
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
        }
        try {
            sqlSession.run(session);
        }
        catch (HibernateException hex) {
            hex.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        if (session != null && session.isOpen()) {
            session.close();
        }
        session = null;
    }
}
