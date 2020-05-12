package robtest.stateinterfw;

public interface ITestState extends IOrderedElement {
    IState getState();
    int getOrder();
    ITestStateOutput getOutput(int index);
    int getOutputCount();
}
