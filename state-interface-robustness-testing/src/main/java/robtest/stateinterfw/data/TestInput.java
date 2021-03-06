package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestInput;
import robtest.stateinterfw.ITestInputArgs;
import robtest.stateinterfw.ITestMessageCollection;
import robtest.stateinterfw.ITestStateCollection;

import java.util.Set;
import java.util.stream.Collectors;

public class TestInput implements ITestInput {
    private int id;
    private String action;
    private boolean locked;
    private Set<TestInputArgument> testInputArguments;
    private Set<TestState> testStates;
    private Set<TestMessage> testMessages;
    private TestCase testCase;
    private int order;

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

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        var args = "";
        if (testInputArguments != null && testInputArguments.size() > 0)
            args = String.join(",", testInputArguments.stream().map(a -> {
                return String.format("%s %s=%s", a.getDataType(), a.getName(), a.getDataValue());
            }).collect(Collectors.toList()));
        return String.format("%s(%s)", action, args);
    }

    public String toHtml() {
        var args = "";
        if (testInputArguments != null && testInputArguments.size() > 0) {
            args = String.join(", ", testInputArguments.stream().map(a -> {
                var type = String.format("<span class=\"text-primary\">%s</span>", a.getDataType());
                var name = String.format("<span class=\"text-muted\">%s</span>", a.getName());
                return String.format("%s %s=%s", type, name, a.getDataValue());
            }).collect(Collectors.toList()));
        }
        var name = String.format("<span class=\"font-italic\">%s</span>", action);
        return String.format("%s(%s)", name, args);
    }
}
