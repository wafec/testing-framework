package robtest.stateinterfw.rabbit;

public class Queue implements IQueue {
    private int id;
    private RabbitMessageDevice messageDevice;
    private String name;
    private String exchange;
    private String routingKey;
    private String exchangeType;

    public Queue() {

    }

    @Override
    public IRabbitMessageDevice getMessageDevice() {
        return messageDevice;
    }

    public void setMessageDevice(RabbitMessageDevice messageDevice) {
        this.messageDevice = messageDevice;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;

    }
}
