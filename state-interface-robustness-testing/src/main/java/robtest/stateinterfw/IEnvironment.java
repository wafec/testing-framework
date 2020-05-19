package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;

public interface IEnvironment extends IEntity {
    int getId();
    String getName();
    String getState();
}
