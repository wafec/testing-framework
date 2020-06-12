package robtest.stateinterfw.web.models;

public class VirtualBoxCreateRequestModel {
    private String name;
    private String snapshot;
    private int priority;
    private String operation;
    private Integer id;

    public VirtualBoxCreateRequestModel() {

    }

    public String getName() {
        return name;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
