package robtest.stateinterfw.rabbit;

import java.util.List;

public interface IRabbitQueueDiscover {
    List<IQueue> listQueues(IRabbitMessageDevice rabbitMessageDevice);
}
