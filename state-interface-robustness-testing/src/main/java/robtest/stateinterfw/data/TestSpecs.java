package robtest.stateinterfw.data;

import robtest.stateinterfw.IEnvironment;
import robtest.stateinterfw.IMessageDevice;
import robtest.stateinterfw.ITestSpecs;

import java.util.Iterator;
import java.util.Set;

public class TestSpecs implements ITestSpecs {
    private int id;
    private Set<Environment> environments;
    private Set<MessageDevice> messageDevices;

    public TestSpecs() {

    }

    public TestSpecs(Set<Environment> environments, Set<MessageDevice> messageDevices) {
        this.environments = environments;
        this.messageDevices = messageDevices;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public IEnvironment getEnvironment(int index) {
        IEnvironment environment = null;
        int i = 0;
        for (Iterator<Environment> it = this.environments.iterator(); it.hasNext() && i <= index; i++) {
            environment = it.next();
        }
        return environment;
    }

    @Override
    public int getEnvironmentCount() {
        return environments.size();
    }

    @Override
    public IMessageDevice getMessageDevice(int index) {
        IMessageDevice messageDevice = null;
        int i = 0;
        for (Iterator<MessageDevice> it = this.messageDevices.iterator(); it.hasNext() && i <= index; i++) {
            messageDevice = it.next();
        }
        return messageDevice;
    }

    @Override
    public int getMessageDeviceCount() {
        return messageDevices.size();
    }
}
