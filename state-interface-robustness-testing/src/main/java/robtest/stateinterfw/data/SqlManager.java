package robtest.stateinterfw.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import robtest.stateinterfw.data.ISqlManager;
import robtest.stateinterfw.data.ISqlSession;

public class SqlManager implements ISqlManager {
    public SqlManager() {

    }

    public void createSession(ISqlSession sqlSession)
    {
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = new Configuration().configure("hbm/hibernate.cfg.xml").buildSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (sqlSession.run(session))
                transaction.commit();
            else
                transaction.rollback();
        }
        catch (HibernateException hex) {
            hex.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
        finally {
            if (session != null)
                session.close();
        }
    }
}