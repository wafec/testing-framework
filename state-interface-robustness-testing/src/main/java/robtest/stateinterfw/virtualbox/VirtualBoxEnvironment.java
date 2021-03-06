package robtest.stateinterfw.virtualbox;

import robtest.stateinterfw.data.Environment;

public class VirtualBoxEnvironment extends Environment implements IVirtualBoxEnvironment {
    private int environmentId;
    private String snapshot;
    private int priority;

    public VirtualBoxEnvironment() {
        super();
    }

    public VirtualBoxEnvironment(String name, String state) {
        super(name, state);
    }

    public void setEnvironmentId(int environmentId) {
        this.environmentId = environmentId;
    }

    public int getEnvironmentId() {
        return environmentId;
    }

    @Override
    public int getId() {
        return environmentId;
    }

    public void setId(int environmentId) {
        this.environmentId = environmentId;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
