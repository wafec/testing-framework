package robtest.stateinterfw.openStack.cli.models;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ServerResult {
    private String id;
    private String image;
    private String name;
    private String flavor;
    private String status;
    private String vmState;
    private String taskState;
    private String powerState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVmState() {
        return vmState;
    }

    @JsonSetter("vm_state")
    public void setVmState(String vmState) {
        this.vmState = vmState;
    }

    public String getTaskState() {
        return taskState;
    }

    @JsonSetter("task_state")
    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getPowerState() {
        return powerState;
    }

    @JsonSetter("power_state")
    public void setPowerState(String powerState) {
        this.powerState = powerState;
    }

    @Override
    public String toString() {
        return String.format("<OSServer(id=%s, image=%s, name=%s, flavor=%s, status=%s, vmState=%s, taskState=%s, powerState=%s)>",
                id, image, name, flavor, status, vmState, taskState, powerState);
    }
}
