package robtest.stateinterfw.data.openStack;

import robtest.stateinterfw.data.IEntity;

public class OSNetwork implements IEntity {
    private int id;
    private String uid;
    private String name;
    private OSTest test;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OSTest getTest() {
        return test;
    }

    public void setTest(OSTest test) {
        this.test = test;
    }
}
