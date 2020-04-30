package robtest.stateinterfw;

public abstract class MessageManager implements IMessageManager {
    protected abstract ITestExecutionContext getTestExecutionContext();

    @Override
    public void receive(IMessage message) {

    }
}
