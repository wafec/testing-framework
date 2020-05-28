package robtest.stateinterfw.rabbit;

public class RabbitManagementFactory implements IRabbitManagementFactory {
    @Override
    public IRabbitManagementApi build(IRabbitMessageDevice messageDevice) {
        return new RabbitManagementApi(messageDevice.getUrl(), 15672, messageDevice.getUser(), messageDevice.getPassword());
    }
}
