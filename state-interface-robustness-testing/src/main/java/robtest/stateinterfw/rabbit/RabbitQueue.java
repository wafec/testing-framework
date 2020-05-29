package robtest.stateinterfw.rabbit;

public class RabbitQueue implements IRabbitQueue {
    private int id;
    private String name;
    private String virtualHost;
    private RabbitMessageDevice messageDevice;

    public RabbitQueue() {

    }

    public RabbitQueue(String name, String virtualHost, RabbitMessageDevice messageDevice) {
        this.name = name;
        this.virtualHost = virtualHost;
        this.messageDevice = messageDevice;
    }

    @Override
    public String getName() {
        return name;
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }
}
