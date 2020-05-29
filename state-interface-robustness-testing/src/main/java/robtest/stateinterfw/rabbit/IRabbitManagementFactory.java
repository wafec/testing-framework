package robtest.stateinterfw.rabbit;

import robtest.stateinterfw.rabbit.management.IRabbitManagementApi;

public interface IRabbitManagementFactory {
    IRabbitManagementApi build(IRabbitMessageDevice messageDevice);
}
