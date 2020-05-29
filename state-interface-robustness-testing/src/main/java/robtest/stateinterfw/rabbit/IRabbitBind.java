package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.data.IEntity;

public interface IRabbitBind extends IEntity {
    IRabbitExchange getSource();
    IRabbitQueue getDestination();
    String getRoutingKey();
    String getVirtualHost();
    IRabbitMessageDevice getMessageDevice();
}
