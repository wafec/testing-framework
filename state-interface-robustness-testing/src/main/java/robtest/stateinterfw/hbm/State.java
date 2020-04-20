package robtest.stateinterfw.hbm;

import robtest.stateinterfw.IState;

public class State implements IState {
    private int id;
    private String name;
    private int timeout;
    private boolean required;

    public State() {

    }

    public State(String name, int timeout, boolean required) {
        this.name = name;
        this.timeout = timeout;
        this.required = required;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
