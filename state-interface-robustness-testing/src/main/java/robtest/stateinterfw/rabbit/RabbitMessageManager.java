package robtest.stateinterfw.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.MessageManager;

import java.util.HashMap;
import java.util.Map;

public class RabbitMessageManager extends MessageManager implements IRabbitMessageManager {
    private ITestExecutionContext _testExecutionContext;
    private Map<IRabbitMessageDevice, Channel> _deviceChannelMap;

    public RabbitMessageManager() {
        this._testExecutionContext = null;
        this._deviceChannelMap = new HashMap<>();
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
