package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.rabbit.management.IRabbitManagementApi;
import robtest.stateinterfw.rabbit.management.RabbitManagementApi;

public class RabbitManagementFactory implements IRabbitManagementFactory {
    @Override
    public IRabbitManagementApi build(IRabbitMessageDevice messageDevice) {
        return new RabbitManagementApi(messageDevice.getUrl(), 15672, messageDevice.getUser(), messageDevice.getPassword());
    }
}
