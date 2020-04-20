package robtest.stateinterfw.hbm;

import robtest.stateinterfw.ITestInput;
import robtest.stateinterfw.ITestInputArgs;
import robtest.stateinterfw.ITestMessageCollection;
import robtest.stateinterfw.ITestStateCollection;

import java.util.Set;

public class TestInput implements ITestInput {
    private int id;
    private String action;
    private boolean locked;
    private Set<TestInputArgument> testInputArguments;
    private Set<TestState> testStates;
    private Set<TestMessage> testMessages;

    public TestInput() {

    }

    public TestInput(String action, boolean locked) {
        this.action = action;
        this.locked = locked;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public ITestInputArgs getArgs() {
        return new TestInputArgs(this.testInputArguments);
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public ITestStateCollection getStates() {
        return new TestStateCollection(this.testStates);
    }

    @Override
    public ITestMessageCollection getMessages() {
        return new TestMessageCollection(this.testMessages);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setTestInputArguments(Set<TestInputArgument> testInputArguments) {
        this.testInputArguments = testInputArguments;
    }

    public Set<TestInputArgument> getTestInputArguments() {
        return this.testInputArguments;
    }

    public void setTestStates(Set<TestState> testStates) {
        this.testStates = testStates;
    }

    public Set<TestState> getTestStates() {
        return this.testStates;
    }

    public void setTestMessages(Set<TestMessage> testMessages) {
        this.testMessages = testMessages;
    }

    public Set<TestMessage> getTestMessages() {
        return this.testMessages;
    }
}
