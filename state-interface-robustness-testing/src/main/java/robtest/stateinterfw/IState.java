package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;

public interface IState extends IEntity {
    String getName();
    int getTimeout();
    boolean isRequired();
}
