package robtest.stateinterfw.data;

import robtest.stateinterfw.IMessageArgument;

public class MessageArgument implements IMessageArgument {
    private int id;
    private int order;
    private String name;
    private String dataType;
    private String dataValue;
    private Message message;

    public MessageArgument() {

    }

    public MessageArgument(int order, String name, String dataType, String dataValue) {
        this.order = order;
        this.name = name;
        this.dataType = dataType;
        this.dataValue = dataValue;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
