package robtest.stateinterfw.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;

public class QueueBindDeliverCallback implements DeliverCallback {
    private IQueue newQueue;
    private IQueue oldQueue;

    public QueueBindDeliverCallback(IQueue newQueue, IQueue oldQueue) {
        this.newQueue = newQueue;
        this.oldQueue = oldQueue;
    }

    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        IRabbitMessageDevice messageDevice = oldQueue.getMessageDevice();
        factory.setHost(messageDevice.getUrl());
        factory.setUsername(messageDevice.getUser());
        factory.setPassword(messageDevice.getPassword());
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.basicPublish(oldQueue.getExchange(), oldQueue.getRoutingKey(), message.getProperties(), message.getBody());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
