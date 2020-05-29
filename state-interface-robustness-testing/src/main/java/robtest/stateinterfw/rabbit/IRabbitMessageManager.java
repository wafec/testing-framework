package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.IMessageManager;

public interface IRabbitMessageManager extends IMessageManager {
    void bind(IRabbitMessageDevice messageDevice);
}
