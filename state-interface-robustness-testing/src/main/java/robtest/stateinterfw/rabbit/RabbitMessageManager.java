package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.MessageManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RabbitMessageManager extends MessageManager implements IRabbitMessageManager {
    private ITestExecutionContext _testExecutionContext;
    private Map<IRabbitMessageDevice, Channel> _deviceChannelMap;
    private IRabbitQueueDiscover _queueDiscover;
    private IRabbitMessageDeviceService _messageDeviceService;

    @Inject
    public RabbitMessageManager(IRabbitQueueDiscover queueDiscover, IRabbitMessageDeviceService messageDeviceService) {
        this._testExecutionContext = null;
        this._deviceChannelMap = new HashMap<>();
        this._queueDiscover = queueDiscover;
        this._messageDeviceService = messageDeviceService;
    }

    private Channel connect(IRabbitMessageDevice rabbitMessageDevice) {
        if (_deviceChannelMap.containsKey(rabbitMessageDevice))
            return _deviceChannelMap.get(rabbitMessageDevice);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMessageDevice.getUrl());
        factory.setUsername(rabbitMessageDevice.getUser());
        factory.setPassword(rabbitMessageDevice.getPassword());
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            _deviceChannelMap.put(rabbitMessageDevice, channel);
            return channel;
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private void disconnect(IRabbitMessageDevice rabbitMessageDevice) {
        if (_deviceChannelMap.containsKey(rabbitMessageDevice)) {
            Channel channel = _deviceChannelMap.get(rabbitMessageDevice);
            try {
                channel.close();
                channel.getConnection().close();
            } catch (Exception exc) {
                exc.printStackTrace();
            } finally {
                _deviceChannelMap.remove(rabbitMessageDevice);
            }
        }
    }

    @Override
    public void bind(ITestExecutionContext testExecutionContext) {
        this._testExecutionContext = testExecutionContext;
        bind();
    }

    private void bind() {
        for (int i = 0; i < _testExecutionContext.getSpecs().getMessageDeviceCount(); i++) {
            IRabbitMessageDevice messageDevice = (IRabbitMessageDevice) _testExecutionContext.getSpecs().getMessageDevice(i);
            bind(messageDevice);
        }
    }

    private void bind(IRabbitMessageDevice messageDevice) {
        unbindFirst(messageDevice);
        List<IQueue> queueList = _queueDiscover.listQueues(messageDevice);
        for (IQueue queue : queueList) {
            createQueueBind(queue);
        }
    }

    private void createQueueBind(IQueue queue) {
        String newName = String.format("test_%s", queue.getName());
        String newRoutingKey = String.format("test_%s", queue.getRoutingKey());
        String newExchange = String.format("test_%s", queue.getExchange());
        IQueue newQueue = _messageDeviceService.createQueue(newName, queue.getExchange(), queue.getExchangeType(), queue.getRoutingKey(), queue.getMessageDevice());
        IQueue oldQueue = _messageDeviceService.createQueue(queue.getName(), newExchange, queue.getExchangeType(), newRoutingKey, queue.getMessageDevice());
        unbindQueue(queue);
        bindQueue(newQueue);
        bindQueue(oldQueue);
        bindNewToOldQueue(newQueue, oldQueue);
    }

    private void bindNewToOldQueue(IQueue newQueue, IQueue oldQueue) {
        Channel channel = connect(newQueue.getMessageDevice());
        try {
            var deliverCallback = new QueueBindDeliverCallback(newQueue, oldQueue);
            channel.basicConsume(newQueue.getName(), true, deliverCallback, consumerTag -> { });
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void unbindFirst(IRabbitMessageDevice messageDevice) {
        List<IQueueBind> boundQueues = this._messageDeviceService.listBoundQueues(messageDevice);
        for (IQueueBind queueBound : boundQueues) {
            unbindQueue(queueBound);
            _messageDeviceService.deleteBoundQueue(queueBound);
        }
    }

    private void unbindQueue(IQueueBind queueBound) {
        unbindQueue(queueBound.getQueue());
        unbindQueue(queueBound.getTestQueue());
        String name = queueBound.getQueue().getName();
        String exchange = queueBound.getTestQueue().getExchange();
        String exchangeType = queueBound.getTestQueue().getExchangeType();
        String routingKey = queueBound.getTestQueue().getRoutingKey();
        IQueue restored = _messageDeviceService.createQueue(name, exchange, exchangeType, routingKey, queueBound.getQueue().getMessageDevice());
        bindQueue(restored);
        _messageDeviceService.saveQueue(restored);
    }

    private void bindQueue(IQueue queue) {
        try {
            Channel channel = connect(queue.getMessageDevice());
            channel.exchangeDeclare(queue.getExchange(), queue.getExchangeType());
            channel.queueDeclare(queue.getName(), false, false, false, null);
            channel.queueBind(queue.getName(), queue.getExchange(), queue.getRoutingKey());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void unbindQueue(IQueue queue) {
        try {
            Channel channel = connect(queue.getMessageDevice());
            channel.queueUnbind(queue.getName(), queue.getExchange(), queue.getRoutingKey());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void unbind() {
        for (int i = 0; i < this._testExecutionContext.getSpecs().getMessageDeviceCount(); i++) {
            IRabbitMessageDevice messageDevice = (IRabbitMessageDevice) this._testExecutionContext.getSpecs().getMessageDevice(i);
            unbindFirst(messageDevice);
        }
    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
