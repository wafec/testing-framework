package robtest.stateinterfw.files.jackson;

import com.fasterxml.jackson.annotation.JsonGetter;
import robtest.stateinterfw.files.IFileTestState;

public class FileTestState implements IFileTestState {
    private int order;
    private String name;
    private int timeout;
    private boolean required;

    public FileTestState() {

    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @JsonGetter("required")
    @Override
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
