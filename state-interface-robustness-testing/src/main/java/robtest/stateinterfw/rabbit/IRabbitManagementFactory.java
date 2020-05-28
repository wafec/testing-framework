package robtest.stateinterfw.rabbit;

public interface IRabbitManagementFactory {
    IRabbitManagementApi build(IRabbitMessageDevice messageDevice);
}
