package robtest.stateinterfw;

public interface ITestMessage extends IOrderedElement {
    int getId();
    IMessage getMessage();
}
