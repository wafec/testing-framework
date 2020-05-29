package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.data.IEntity;

public interface IRabbitTestBind extends IEntity {
    IRabbitBind getOldBind();
    IRabbitBind getSourceBind();
    IRabbitBind getDestinationBind();
    IRabbitMessageDevice getMessageDevice();
}
