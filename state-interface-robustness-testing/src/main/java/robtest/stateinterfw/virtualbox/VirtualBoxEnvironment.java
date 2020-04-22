package robtest.stateinterfw.virtualbox;

import robtest.stateinterfw.data.Environment;

public class VirtualBoxEnvironment extends Environment {
    private String snapshot;
    private int priority;
    private String type;

    public VirtualBoxEnvironment() {
        super();
    }

    public VirtualBoxEnvironment(String name, String state, String type) {
        super(name, state);
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
