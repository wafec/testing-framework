package robtest.stateinterfw.web.models;

public class StateCreateRequestModel {
    private String name;
    private boolean required;
    private int timeout;

    public StateCreateRequestModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
