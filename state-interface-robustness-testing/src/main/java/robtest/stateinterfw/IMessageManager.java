package robtest.stateinterfw;

public interface IMessageManager {
    void receive(IMessage message);
    void bind(ITestExecutionContext testExecutionContext);
    void unbind();
}
