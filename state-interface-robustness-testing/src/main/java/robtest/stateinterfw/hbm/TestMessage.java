package robtest.stateinterfw.hbm;

import robtest.stateinterfw.IMessage;
import robtest.stateinterfw.ITestMessage;

public class TestMessage implements ITestMessage {
    private int id;
    private Message message;
    private int order;

    public TestMessage() {

    }

    public TestMessage(int id, Message message, int order) {
        this.id = id;
        this.message = message;
        this.order = order;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public IMessage getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
