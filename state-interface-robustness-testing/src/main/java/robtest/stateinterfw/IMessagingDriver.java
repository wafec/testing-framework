package robtest.stateinterfw;

public interface IMessagingDriver {
    void receive(IMessage message);
    void bind(ITestExecutionContext testExecutionContext);
    void unbind();
}
