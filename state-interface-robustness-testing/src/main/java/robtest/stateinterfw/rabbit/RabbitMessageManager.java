package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.IMessage;
import robtest.stateinterfw.IMessageManager;
import robtest.stateinterfw.ITestExecutionContext;

public class RabbitMessageManager implements IMessageManager {
    private ITestExecutionContext _testExecutionContext;

    public RabbitMessageManager() {
        this._testExecutionContext = null;
    }

    @Override
    public void receive(IMessage message) {

    }

    @Override
    public void bind(ITestExecutionContext testExecutionContext) {
        this._testExecutionContext = testExecutionContext;
    }

    @Override
    public void unbind() {

    }
}
