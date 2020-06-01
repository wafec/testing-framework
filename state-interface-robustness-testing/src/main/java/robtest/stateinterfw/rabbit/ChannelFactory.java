package robtest.stateinterfw.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChannelFactory {
    private ChannelFactory() {

    }

    public static Channel createChannel(IRabbitMessageDevice messageDevice) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(messageDevice.getUrl());
        factory.setUsername(messageDevice.getUser());
        factory.setPassword(messageDevice.getPassword());
        Channel channel = null;
        try {
            Connection con = factory.newConnection();
            channel = con.createChannel();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return channel;
    }
}
