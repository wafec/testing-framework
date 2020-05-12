package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;

public interface ITestInput extends IEntity {
    int getId();
    String getAction();
    ITestInputArgs getArgs();
    boolean isLocked();
    ITestStateCollection getStates();
    ITestMessageCollection getMessages();
}
