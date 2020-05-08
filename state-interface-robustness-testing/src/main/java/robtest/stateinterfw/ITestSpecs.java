package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;

public interface ITestSpecs extends IEntity {
    IEnvironment getEnvironment(int index);
    int getEnvironmentCount();
    IMessageDevice getMessageDevice(int index);
    int getMessageDeviceCount();
}
