package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.data.IEntity;

public interface IQueue extends IEntity {
    IRabbitMessageDevice getMessageDevice();
    String getName();
    String getExchange();
    String getExchangeType();
    String getRoutingKey();
}
