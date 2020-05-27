package robtest.stateinterfw.rabbit;

import java.util.List;

public interface IRabbitMessageDeviceService {
    List<IQueueBind> listBoundQueues(IRabbitMessageDevice messageDevice);
    void deleteBoundQueue(IQueueBind queueBind);
    IQueue createQueue(String name, String exchange, String exchangeType, String routingKey, IRabbitMessageDevice messageDevice);
    IQueueBind createQueueBind(IQueue queue, IQueue testQueue);
    void deleteQueue(IQueue queue);
    void saveQueue(IQueue queue);
    void saveQueueBind(IQueueBind queueBind);
}
