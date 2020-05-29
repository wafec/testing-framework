package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.data.IEntity;

public interface IRabbitQueue extends IEntity {
    String getName();
    String getVirtualHost();
    IRabbitMessageDevice getMessageDevice();
}
