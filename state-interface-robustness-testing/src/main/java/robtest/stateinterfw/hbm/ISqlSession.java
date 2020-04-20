package robtest.stateinterfw.hbm;

import org.hibernate.Session;

public interface ISqlSession {
    boolean run(Session session);
}
