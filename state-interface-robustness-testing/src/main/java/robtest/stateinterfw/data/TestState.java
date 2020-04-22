package robtest.stateinterfw.data;

import robtest.stateinterfw.IState;
import robtest.stateinterfw.ITestState;

public class TestState implements ITestState {
    private int id;
    private State state;
    private int order;
    private TestInput testInput;

    public TestState() {

    }

    public TestState(State state, int order) {
        this.state = state;
        this.order = order;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public IState getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setTestInput(TestInput testInput) {
        this.testInput = testInput;
    }

    public TestInput getTestInput() {
        return testInput;
    }
}
