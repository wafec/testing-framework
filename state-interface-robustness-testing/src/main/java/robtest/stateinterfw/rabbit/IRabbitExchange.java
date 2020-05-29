package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.data.IEntity;

public interface IRabbitExchange extends IEntity  {
    String getName();
    String getExchangeType();
    String getVirtualHost();
    IRabbitMessageDevice getMessageDevice();
}
