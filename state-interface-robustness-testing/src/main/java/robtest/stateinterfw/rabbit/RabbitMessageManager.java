package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.MessageManager;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RabbitMessageManager extends MessageManager implements IRabbitMessageManager {
    private ITestExecutionContext _testExecutionContext;
    private IRabbitQueueDiscover _queueDiscover;
    private IRabbitManagementFactory managementFactory;
    private IRabbitTestBindBuilder testBindBuilder;
    private IRabbitMessageDevice _messageDevice;
    private IRepository repository;

    @Inject
    public RabbitMessageManager(IRabbitQueueDiscover queueDiscover,
                                IRabbitManagementFactory managementFactory,
                                IRabbitTestBindBuilder testBindBuilder,
                                IRepository repository) {
        this._testExecutionContext = null;
        this._queueDiscover = queueDiscover;
        this.managementFactory = managementFactory;
        this.testBindBuilder = testBindBuilder;
        this.repository = repository;
    }

    private Channel createChannel() {
        return createChannel(this._messageDevice);
    }

    private Channel createChannel(IRabbitMessageDevice messageDevice) {
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
                bind(testBind);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void unbind(IRabbitBind bind) throws IOException {
        var channel = createChannel();
        channel.queueUnbind(bind.getDestination().getName(), bind.getSource().getName(), bind.getRoutingKey());
    }

    private void bind(IRabbitBind bind) throws IOException {
        var channel = createChannel();
        channel.exchangeDeclare(bind.getSource().getName(), bind.getSource().getExchangeType());
        channel.queueDeclare(bind.getDestination().getName(), false, false, false, null);
        channel.queueBind(bind.getDestination().getName(), bind.getSource().getName(), bind.getRoutingKey());
    }

    private void bind(IRabbitTestBind testBind) throws IOException {
        unbind((IRabbitBind) testBind.getOldBind());
        bind(testBind.getSourceBind());
        bind(testBind.getDestinationBind());
    }

    @Override
    public void unbind() {
        List<RabbitTestBind> testBindList = repository.query("from RabbitTestBind where messageDevice.id = :id",
                RabbitTestBind.class, Param.list("id", this._messageDevice.getId()));
        for (var testBind : testBindList) {
            try {
                unbind(testBind.getSourceBind());
                unbind(testBind.getDestinationBind());
                bind(testBind.getOldBind());
                repository.remove(testBind.getSourceBind());
                repository.remove(testBind.getDestinationBind());
                repository.remove(testBind);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
