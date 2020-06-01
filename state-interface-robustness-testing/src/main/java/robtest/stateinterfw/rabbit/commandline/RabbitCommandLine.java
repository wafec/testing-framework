package robtest.stateinterfw.rabbit.commandline;

import com.google.inject.Inject;
import com.rabbitmq.client.*;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import robtest.core.commandline.AbstractCommandLine;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.rabbit.*;
import robtest.stateinterfw.rabbit.management.*;

import java.io.IOException;

public class RabbitCommandLine extends AbstractCommandLine implements IRabbitCommandLine {
    private IRepository repository;
    private IRabbitManagementApi managementApi;
    private IRabbitMessageManager messageManager;

    @Inject
    public RabbitCommandLine(IRepository repository, IRabbitMessageManager messageManager) {
        this.repository = repository;
        this.messageManager = messageManager;

        add("management", this::management);
        add("connectivity", this::connectivity);
        add("manager", this::manager);
        add("send", this::send);
        add("consume", this::consume);
    }

    @Override
    public void run(String... args) {
        parse(args);
    }

    private void management(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("id").hasArg().required().build());
        options.addOption(Option.builder().longOpt("action").hasArg().build());
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            int id = Integer.parseInt(commandLine.getOptionValue("id"));
            String action = commandLine.getOptionValue("action");
            testRabbitManagement(id, action);
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    private void connectivity(String[] args) {

    }

    private void manager(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("id").hasArg().build());
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(Option.builder().longOpt("bind").build());
        optionGroup.addOption(Option.builder().longOpt("unbind").build());
        options.addOptionGroup(optionGroup);
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            int id = Integer.parseInt(commandLine.getOptionValue("id"));

            var messageDevice = repository.get(id, RabbitMessageDevice.class);
            if (commandLine.hasOption("bind"))
                messageManager.bind(messageDevice);
            else if (commandLine.hasOption("unbind"))
                messageManager.unbind(messageDevice);
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    private void send(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("id").required().hasArg().build());
        options.addOption(Option.builder().longOpt("exchange").required().hasArg().build());
        options.addOption(Option.builder().longOpt("routingKey").required().hasArg().build());
        options.addOption(Option.builder().longOpt("message").required().hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            int id = Integer.parseInt(commandLine.getOptionValue("id"));
            String exchange = commandLine.getOptionValue("exchange");
            String routingKey = commandLine.getOptionValue("routingKey");
            String message = commandLine.getOptionValue("message");
            var messageDevice = repository.get(id, RabbitMessageDevice.class);
            final Channel channel = ChannelFactory.createChannel(messageDevice);
            try (var connection = channel.getConnection(); channel) {
                channel.basicPublish(exchange, routingKey, null, message.getBytes());
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    private void consume(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("id").required().hasArg().build());
        options.addOption(Option.builder().longOpt("queue").required().hasArg().build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            int id = Integer.parseInt(commandLine.getOptionValue("id"));
            String queue = commandLine.getOptionValue("queue");
            var messageDevice = repository.get(id, RabbitMessageDevice.class);
            final Channel channel = ChannelFactory.createChannel(messageDevice);
            try {
                channel.basicConsume(queue, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        long deliveryTag = envelope.getDeliveryTag();
                        System.out.println(String.format("[x] Message: %s", new String(body, "UTF-8")));
                        channel.basicAck(deliveryTag, false);
                        try {
                            channel.close();
                            channel.getConnection().close();
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                    }
                });
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
    }

    private void queueInfo(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("queue").required().hasArg().build());
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            String queue = commandLine.getOptionValue("queue");
            var result = managementApi.detailQueue(queue, null);
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

    private void testRabbitManagement(int id, String action) {
        RabbitMessageDevice messageDevice = repository.get(id, RabbitMessageDevice.class);
        managementApi = new RabbitManagementApi(messageDevice.getUrl(), 15672, messageDevice.getUser(), messageDevice.getPassword());
        if (!StringUtils.isEmpty(action) && action.equals("clear")) {
            for (var queue : managementApi.listQueues(null)) {
                try {
                    managementApi.queueDelete(queue.getName(), null);
                } catch (Exception exc) {

                }
            }
            for (var exchange : managementApi.listExchanges(null)) {
                try {
                    managementApi.exchangeDelete(exchange.getName(), null);
                } catch (Exception exc) {

                }
            }
        } else {
            managementApi.exchangeDeclare(new ExchangeDeclareModel("test_e", "fanout"), null);
            managementApi.queueDeclare(new QueueDeclareModel("test_q"), null);
            managementApi.queueBind(new QueueBindModel("test_q", "test_e", "test_r"), null);
        }
    }
}
