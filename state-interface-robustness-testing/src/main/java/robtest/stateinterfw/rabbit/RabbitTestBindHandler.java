package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.ITransactionRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RabbitTestBindHandler implements IRabbitTestBindHandler {
    private static List<IRabbitTestBind> controls = new ArrayList<>();
    private static Map<Integer, Channel> channels = new HashMap<>();
    private ITransactionRepository transactionRepository;
    private IRepository repository;

    @Inject
    public RabbitTestBindHandler(IRepository repository, ITransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void control(IRabbitTestBind rabbitTestBind) {
        if (controls.stream().anyMatch(c -> c.getId() == rabbitTestBind.getId())) {
            leaveControl(rabbitTestBind);
        }
        Channel channel = ChannelFactory.createChannel(rabbitTestBind.getMessageDevice());
        try {
            channels.put(rabbitTestBind.getId(), channel);
            System.out.println(String.format("Destination: %s", rabbitTestBind.getSourceBind().getDestination().getName()));
            channel.basicConsume(rabbitTestBind.getSourceBind().getDestination().getName(), true, new CustomDeliverCallback(rabbitTestBind), consumerTag -> {});
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void leaveControl(IRabbitTestBind rabbitTestBind) {
        controls.removeIf(c -> c.getId() == rabbitTestBind.getId());
        Channel channel = channels.get(rabbitTestBind.getId());
        if (channel != null) {
            try {
                channel.close();
                channel.getConnection().close();
            } catch (Exception exc) {
                exc.printStackTrace();
            } finally {
                channels.remove(rabbitTestBind.getId());
            }
        }
    }

    public void destroy() {
        for (var rabbitTestBind : controls) {
            leaveControl(rabbitTestBind);
        }
        controls.clear();
        channels.clear();
    }

    public static class CustomDeliverCallback implements DeliverCallback {
        private IRabbitTestBind rabbitTestBind;

        public CustomDeliverCallback(IRabbitTestBind rabbitTestBind) {
            this.rabbitTestBind = rabbitTestBind;
        }

        @Override
        public void handle(String consumerTag, Delivery message) throws IOException {
            Channel _channel = ChannelFactory.createChannel(this.rabbitTestBind.getMessageDevice());
            try (Connection connection = _channel.getConnection(); Channel channel = _channel) {
                System.out.println(String.format("Message: %s", new String(message.getBody(), "UTF-8")));
                System.out.println(String.format("Destination: %s, Routing Key: %s", rabbitTestBind.getDestinationBind().getSource().getName(),
                        rabbitTestBind.getDestinationBind().getRoutingKey()));
                channel.basicPublish(this.rabbitTestBind.getDestinationBind().getSource().getName(),
                        this.rabbitTestBind.getDestinationBind().getRoutingKey(), message.getProperties(), message.getBody());
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
