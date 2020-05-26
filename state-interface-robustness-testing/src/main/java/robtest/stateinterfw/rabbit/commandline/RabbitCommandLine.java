package robtest.stateinterfw.rabbit.commandline;

import com.google.inject.Inject;
import com.rabbitmq.client.*;
import org.apache.commons.cli.*;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.rabbit.RabbitMessageDevice;

public class RabbitCommandLine implements IRabbitCommandLine {
    private IRepository repository;

    @Inject
    public RabbitCommandLine(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("id").hasArg().required().build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            testRabbitConnectivity(Integer.parseInt(commandLine.getOptionValue("id")));
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    private void testRabbitConnectivity(int id) {
        RabbitMessageDevice messageDevice = repository.get(id, RabbitMessageDevice.class);
        testRabbitConnectivitySend(messageDevice);
        testRabbitConnectivityReceive(messageDevice);
        testRabbitConnectivityDelete(messageDevice);
    }

    private void testRabbitConnectivity(RabbitMessageDevice messageDevice, IRabbitCommandLineConsumer callback) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(messageDevice.getUrl());
        factory.setUsername(messageDevice.getUser());
        factory.setPassword(messageDevice.getPassword());
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            callback.accept(channel);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void testRabbitConnectivitySend(RabbitMessageDevice messageDevice) {
        System.out.println(" [x] Test send");
        testRabbitConnectivity(messageDevice, (channel -> {
            String exchangeName = "TEST_EXCHANGE";
            String queueName = "TEST_QUEUE";
            channel.exchangeDeclare(exchangeName, "fanout");
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, exchangeName, queueName);
            String message = "Test message!";
            channel.basicPublish(exchangeName, queueName, null, message.getBytes());
        }));
    }

    private void testRabbitConnectivityReceive(RabbitMessageDevice messageDevice) {
        System.out.println(" [x] Test receive");
        testRabbitConnectivity(messageDevice, (channel -> {
            String queueName = "TEST_QUEUE";
            GetResponse response = channel.basicGet(queueName, true);
            if (response == null) {
                System.out.println(" [x] No message");
            } else {
                byte[] body = response.getBody();
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        }));
    }

    private void testRabbitConnectivityDelete(RabbitMessageDevice messageDevice) {
        System.out.println(" [x] Test delete");
        testRabbitConnectivity(messageDevice, channel -> {
            String queueName = "TEST_QUEUE";
            String exchangeName = "TEST_EXCHANGE";
            channel.queueDelete(queueName);
            channel.exchangeDelete(exchangeName);
        });
    }
}
