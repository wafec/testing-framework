package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.data.MessageDevice;

public class RabbitMessageDevice extends MessageDevice implements IRabbitMessageDevice {
    private int messageDeviceId;
    private String url;
    private String user;
    private String password;

    public RabbitMessageDevice() {

    }

    public RabbitMessageDevice(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void setMessageDeviceId(int messageDeviceId) {
        this.messageDeviceId = messageDeviceId;
    }

    public int getMessageDeviceId() {
        return messageDeviceId;
    }

    @Override
    public int getId() {
        return messageDeviceId;
    }

    public void setId(int id) {
        this.messageDeviceId = id;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
