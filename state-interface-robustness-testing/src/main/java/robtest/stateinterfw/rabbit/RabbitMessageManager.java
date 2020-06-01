package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.MessageManager;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.ITransactionRepository;
import robtest.stateinterfw.data.Param;

import java.io.IOException;
import java.util.List;

public class RabbitMessageManager extends MessageManager implements IRabbitMessageManager {
    private ITestExecutionContext _testExecutionContext;
    private IRabbitQueueDiscover _queueDiscover;
    private IRabbitManagementFactory managementFactory;
    private IRabbitTestBindBuilder testBindBuilder;
    private IRabbitMessageDevice _messageDevice;
    private final IRepository repository;
    private final ITransactionRepository transactionRepository;
    private IRabbitTestBindHandler rabbitTestBindHandler;

    @Inject
    public RabbitMessageManager(IRabbitQueueDiscover queueDiscover,
                                IRabbitManagementFactory managementFactory,
                                IRabbitTestBindBuilder testBindBuilder,
                                IRepository repository,
                                ITransactionRepository transactionRepository,
                                IRabbitTestBindHandler rabbitTestBindHandler) {
        this._testExecutionContext = null;
        this._queueDiscover = queueDiscover;
        this.managementFactory = managementFactory;
        this.testBindBuilder = testBindBuilder;
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.rabbitTestBindHandler = rabbitTestBindHandler;
    }

    private Channel createChannel() {
        return createChannel(this._messageDevice);
    }

    private Channel createChannel(IRabbitMessageDevice messageDevice) {
        return ChannelFactory.createChannel(messageDevice);
    }


    @Override
    public void bind(ITestExecutionContext testExecutionContext) {
        this._testExecutionContext = testExecutionContext;
        for (var i = 0; i < this._testExecutionContext.getSpecs().getMessageDeviceCount(); i++) {
            this.bind((IRabbitMessageDevice) testExecutionContext.getSpecs().getMessageDevice(i));
        }
    }

    @Override
    public void bind(IRabbitMessageDevice messageDevice) {
        try {
            this._messageDevice = messageDevice;
            List<IRabbitBind> bindings = _queueDiscover.listBindings(messageDevice);
            for (var binding : bindings) {
                IRabbitTestBind testBind = testBindBuilder.create(binding);
                if (testBind != null)
                    bind(testBind);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void unbind(IRabbitBind bind) throws IOException {
        final var channel = createChannel();
        try(Connection connection = channel.getConnection(); channel ) {
            channel.queueUnbind(bind.getDestination().getName(), bind.getSource().getName(), bind.getRoutingKey());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void bind(IRabbitBind bind) throws IOException {
        final var channel = createChannel();
        try (Connection connection = channel.getConnection(); channel) {
            channel.exchangeDeclare(bind.getSource().getName(), bind.getSource().getExchangeType());
            channel.queueDeclare(bind.getDestination().getName(), false, false, false, null);
            channel.queueBind(bind.getDestination().getName(), bind.getSource().getName(), bind.getRoutingKey());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void bind(IRabbitTestBind testBind) throws IOException {
        unbind((IRabbitBind) testBind.getOldBind());
        bind(testBind.getSourceBind());
        bind(testBind.getDestinationBind());
        rabbitTestBindHandler.control(testBind);
    }

    private void deleteQueue(String name) {
        try {
            var channel = createChannel();
            channel.queueDelete(name);
            channel.close();
            channel.getConnection().close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void unbind() {
        List<RabbitTestBind> testBindList = repository.query("from RabbitTestBind where messageDevice.id = :id",
                RabbitTestBind.class, Param.list("id", this._messageDevice.getId()));
        for (var testBind : testBindList) {
            try (transactionRepository) {
                unbind(testBind.getSourceBind());
                unbind(testBind.getDestinationBind());
                bind(testBind.getOldBind());
                deleteQueue(testBind.getSourceBind().getDestination().getName());
                transactionRepository.remove(testBind.getSourceBind().getDestination());
                transactionRepository.remove(testBind.getSourceBind());
                transactionRepository.remove(testBind.getDestinationBind());
                transactionRepository.remove(testBind);
            } catch (Exception exc) {
                exc.printStackTrace();
            } finally {
                rabbitTestBindHandler.leaveControl(testBind);
            }
        }
    }

    @Override
    public void unbind(IRabbitMessageDevice messageDevice) {
        this._messageDevice = messageDevice;
        unbind();
    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
