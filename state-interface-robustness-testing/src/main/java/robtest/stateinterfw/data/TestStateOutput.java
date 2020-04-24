package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestStateOutput;

import java.util.Date;

public class TestStateOutput implements ITestStateOutput {
    private int id;
    private Date time;
    private String text;
    private TestState testState;

    public TestStateOutput() {

    }

    public TestStateOutput(Date time, String text, TestState testState) {
        this.time = time;
        this.text = text;
        this.testState = testState;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTestState(TestState testState) {
        this.testState = testState;
    }

    public TestState getTestState() {
        return this.testState;
    }
}
