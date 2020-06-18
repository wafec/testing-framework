package robtest.stateinterfw.data.os;

import robtest.stateinterfw.data.IEntity;

public class OSImage implements IEntity {
    private int id;
    private String uid;
    private String name;
    private String diskFormat;
    private String containerFormat;
    private OSTest test;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiskFormat() {
        return diskFormat;
    }

    public void setDiskFormat(String diskFormat) {
        this.diskFormat = diskFormat;
    }

    public String getContainerFormat() {
        return containerFormat;
    }

    public void setContainerFormat(String containerFormat) {
        this.containerFormat = containerFormat;
    }

    public OSTest getTest() {
        return test;
    }

    public void setTest(OSTest test) {
        this.test = test;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
