package robtest.stateinterfw.rabbit;

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
    }

    @Override
    public void unbind() {

    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
