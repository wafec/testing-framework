package robtest.stateinterfw.data;

import org.hibernate.Session;

public interface ISqlSession {
    boolean run(Session session);
}
