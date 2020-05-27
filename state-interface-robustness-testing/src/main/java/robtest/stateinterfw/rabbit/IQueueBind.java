package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.data.IEntity;

public interface IQueueBind extends IEntity {
    IQueue getQueue();
    IQueue getTestQueue();
}
