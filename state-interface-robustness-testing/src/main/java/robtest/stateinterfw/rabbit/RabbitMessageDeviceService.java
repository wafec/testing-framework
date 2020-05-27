package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.IMessageDevice;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;

import java.util.ArrayList;
import java.util.List;

public class RabbitMessageDeviceService implements IRabbitMessageDeviceService {
    private IRepository repository;

    public RabbitMessageDeviceService(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<IQueueBind> listBoundQueues(IRabbitMessageDevice messageDevice) {
        var result = repository.query("from QueueBind where queue.messageDevice.id = :queueId or testQueue.messageDevice.id = :testQueueId",
                QueueBind.class, Param.list("queueId", messageDevice.getId()).add("testQueueId", messageDevice.getId()).all());
        return new ArrayList<>(result);
    }

    @Override
    public void deleteBoundQueue(IQueueBind queueBind) {
        repository.remove(queueBind.getQueue());
        repository.remove(queueBind.getTestQueue());
        repository.remove(queueBind);
    }

    @Override
    public IQueue createQueue(String name, String exchange, String exchangeType, String routingKey, IRabbitMessageDevice messageDevice) {
        Queue queue = new Queue();
        queue.setName(name);
        queue.setExchange(exchange);
        queue.setRoutingKey(routingKey);
        queue.setExchangeType(exchangeType);
        queue.setMessageDevice((RabbitMessageDevice) messageDevice);
        return queue;
    }

    @Override
    public IQueueBind createQueueBind(IQueue queue, IQueue testQueue) {
        QueueBind queueBind = new QueueBind();
        queueBind.setQueue((Queue) queue);
        queueBind.setTestQueue((Queue) testQueue);
        return queueBind;
    }

    @Override
    public void deleteQueue(IQueue queue) {
        repository.remove(queue);
    }

    @Override
    public void saveQueue(IQueue queue) {
        repository.save(queue);
    }

    @Override
    public void saveQueueBind(IQueueBind queueBind) {
        repository.save(queueBind);
    }
}
