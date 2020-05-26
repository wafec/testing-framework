package robtest.stateinterfw.data;

import robtest.stateinterfw.IMessageDevice;

public abstract class MessageDevice implements IMessageDevice {
    private int id;

    public MessageDevice() {

    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
