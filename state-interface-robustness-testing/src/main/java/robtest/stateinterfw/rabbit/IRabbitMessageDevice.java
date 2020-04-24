package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.IMessageDevice;

public interface IRabbitMessageDevice extends IMessageDevice {
    String getUrl();
    String getUser();
    String getPassword();
}
