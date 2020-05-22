package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;

public interface ITestPlan extends IEntity {
    ITestCase getTestCase(int index);
    int getTestCaseCount();
    IEnvironment getEnvironment(int index);
    int getEnvironmentCount();
    IMessageDevice getMessageDevice(int index);
    int getMessageDeviceCount();
    String getName();
}
