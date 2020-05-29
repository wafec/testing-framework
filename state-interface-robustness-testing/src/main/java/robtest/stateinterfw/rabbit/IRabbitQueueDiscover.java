package robtest.stateinterfw.rabbit;

import java.util.List;

public interface IRabbitQueueDiscover {
    List<IRabbitBind> listBindings(IRabbitMessageDevice rabbitMessageDevice);
}
