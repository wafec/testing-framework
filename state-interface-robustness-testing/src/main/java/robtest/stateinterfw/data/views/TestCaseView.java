package robtest.stateinterfw.data.views;

import robtest.stateinterfw.data.IEntity;

public class TestCaseView implements IEntity {
    private int id;
    private String uid;
    private int inputCount;

    public TestCaseView() {

    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    public int getInputCount() {
        return inputCount;
    }
}
