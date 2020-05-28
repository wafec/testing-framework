package robtest.stateinterfw.rabbit.commandline;

import com.google.inject.Inject;
import com.rabbitmq.client.*;
import org.apache.commons.cli.*;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.rabbit.*;

public class RabbitCommandLine implements IRabbitCommandLine {
    private IRepository repository;
    private IRabbitManagementApi managementApi;

    @Inject
    public RabbitCommandLine(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("id").hasArg().required().build());
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(Option.builder().longOpt("management").build());
        optionGroup.addOption(Option.builder().longOpt("connectivity").build());
        options.addOptionGroup(optionGroup);
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            var id = Integer.parseInt(commandLine.getOptionValue("id"));
            if (commandLine.hasOption("connectivity"))
                testRabbitConnectivity(id);
            else if (commandLine.hasOption("management"))
                testRabbitManagement(id);
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

    private void testRabbitManagement(int id) {
        RabbitMessageDevice messageDevice = repository.get(id, RabbitMessageDevice.class);
        managementApi = new RabbitManagementApi(messageDevice.getUrl(), 15672, messageDevice.getUser(), messageDevice.getPassword());
        for (var bind : managementApi.listBindings(null)) {
            System.out.println(String.format("%s %s, %s %s, %s", bind.getSource(), bind.getSourceType(), bind.getDestination(), bind.getDestinationType(), bind.getRoutingKey()));
        }
    }
}
