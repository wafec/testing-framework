package robtest.stateinterfw.data.os;

import robtest.stateinterfw.data.IEntity;

public class OSFlavor implements IEntity {
    private int id;
    private String uid;
    private String name;
    private int vcpus;
    private int ram;
    private int disk;
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

    public int getVcpus() {
        return vcpus;
    }

    public void setVcpus(int vcpus) {
        this.vcpus = vcpus;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
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
