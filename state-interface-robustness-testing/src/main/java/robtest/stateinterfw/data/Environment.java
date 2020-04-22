package robtest.stateinterfw.data;

import robtest.stateinterfw.IEnvironment;

public abstract class Environment implements IEnvironment {
    private int id;
    private String name;
    private String state;

    public Environment() { }

    public Environment(String name, String state) {
        this.name = name;
        this.state = state;
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

    @Override
    public String getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }
}
