package robtest.stateinterfw;

public interface ITestSpecs {
    int getId();
    IEnvironment getEnvironment(int index);
    int getEnvironmentCount();
    IMessageDevice getMessageDevice(int index);
    int getMessageDeviceCount();
}
