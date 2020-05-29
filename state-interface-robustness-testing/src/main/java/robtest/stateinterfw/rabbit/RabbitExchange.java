package robtest.stateinterfw.rabbit;

public class RabbitExchange implements IRabbitExchange {
    private int id;
    private String virtualHost;
    private String exchangeType;
    private String name;
    private RabbitMessageDevice messageDevice;

    public RabbitExchange() {

    }

    public RabbitExchange(String name, String exchangeType, String virtualHost, RabbitMessageDevice messageDevice) {
        this.virtualHost = virtualHost;
        this.exchangeType = exchangeType;
        this.name = name;
        this.messageDevice = messageDevice;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
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
