package robtest.stateinterfw.rabbit;

public class RabbitBind implements IRabbitBind {
    private int id;
    private RabbitExchange source;
    private RabbitQueue destination;
    private String routingKey;
    private String virtualHost;
    private RabbitMessageDevice messageDevice;

    public RabbitBind() {

    }

    public RabbitBind(RabbitExchange source, RabbitQueue destination, String routingKey, String virtualHost,
                      RabbitMessageDevice messageDevice) {
        this.source = source;
        this.destination = destination;
        this.routingKey = routingKey;
        this.virtualHost = virtualHost;
        this.messageDevice = messageDevice;
    }

    @Override
    public IRabbitExchange getSource() {
        return source;
    }

    public void setSource(RabbitExchange source) {
        this.source = source;
    }

    @Override
    public IRabbitQueue getDestination() {
        return destination;
    }

    public void setDestination(RabbitQueue destination) {
        this.destination = destination;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public String getVirtualHost() {
        return virtualHost;
    }

    @Override
    public IRabbitMessageDevice getMessageDevice() {
        return messageDevice;
    }

    public void setMessageDevice(RabbitMessageDevice messageDevice) {
        this.messageDevice = messageDevice;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
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
