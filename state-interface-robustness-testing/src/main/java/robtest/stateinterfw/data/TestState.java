package robtest.stateinterfw.data;

import robtest.stateinterfw.IState;
import robtest.stateinterfw.ITestState;
import robtest.stateinterfw.ITestStateOutput;

import java.util.Iterator;
import java.util.Set;

public class TestState implements ITestState {
    private int id;
    private State state;
    private int order;
    private TestInput testInput;
    private Set<TestStateOutput> outputs;

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

    @Override
    public ITestStateOutput getOutput(int index) {
        if (outputs == null)
            throw new NullPointerException();
        if (index < 0 || index >= getOutputCount())
            throw new IndexOutOfBoundsException();
        ITestStateOutput output = null;
        int i = 0;
        for (Iterator<TestStateOutput> it = outputs.iterator(); it.hasNext() && i <= index; i++) {
            output = it.next();
        }
        return output;
    }

    @Override
    public int getOutputCount() {
        if (outputs == null)
            return 0;
        return outputs.size();
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

    public void setOutputs(Set<TestStateOutput> outputs) {
        this.outputs = outputs;
    }

    public Set<TestStateOutput> getOutputs() {
        return this.outputs;
    }
}
