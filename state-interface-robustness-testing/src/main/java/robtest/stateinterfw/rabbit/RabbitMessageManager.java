package robtest.stateinterfw.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.MessageManager;

public class RabbitMessageManager extends MessageManager implements IRabbitMessageManager {
    private ITestExecutionContext _testExecutionContext;

    public RabbitMessageManager() {
        this._testExecutionContext = null;
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
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setHost(messageDevice.getUrl());
            factory.setPassword(messageDevice.getPassword());
            factory.setUsername(messageDevice.getUser());
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new IllegalArgumentException(exc.getMessage(), exc);
        }
    }

    @Override
    public void unbind() {

    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
