package robtest.stateinterfw;

public interface ITestState extends IOrderedElement {
    int getId();
    IState getState();
}
