package robtest.stateinterfw.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
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
        Transaction tx = session.getTransaction();
        try {
            if (sqlSession instanceof ISqlTransactionSession && !tx.getStatus().equals(TransactionStatus.ACTIVE))
                tx = session.beginTransaction();
            if (sqlSession.run(session)) {
                if (tx != null) {
                    if (!tx.getStatus().equals(TransactionStatus.COMMITTED))
                        tx.commit();
                }
            }
        }
        catch (HibernateException hex) {
            hex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void commit() {
        if (session != null && session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
            session.getTransaction().commit();
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
