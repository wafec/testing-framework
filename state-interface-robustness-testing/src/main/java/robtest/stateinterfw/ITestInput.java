package robtest.stateinterfw;

public interface ITestInput {
    int getId();
    String getAction();
    ITestInputArgs getArgs();
    boolean isLocked();
    ITestStateCollection getStates();
    ITestMessageCollection getMessages();
}
