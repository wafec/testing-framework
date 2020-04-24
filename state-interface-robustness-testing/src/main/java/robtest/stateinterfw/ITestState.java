package robtest.stateinterfw;

public interface ITestState extends IOrderedElement {
    int getId();
    IState getState();
    int getOrder();
    ITestStateOutput getOutput(int index);
    int getOutputCount();
}
