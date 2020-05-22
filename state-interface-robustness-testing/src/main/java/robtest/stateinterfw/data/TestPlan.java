package robtest.stateinterfw.data;

import com.google.common.collect.Iterators;
import robtest.stateinterfw.IEnvironment;
import robtest.stateinterfw.IMessageDevice;
import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestPlan;

import java.util.Set;

public abstract class TestPlan implements ITestPlan {
    private int id;
    private Set<TestCase> testCases;
    private Set<Environment> environments;
    private Set<MessageDevice> messageDevices;
    private String name;

    public TestPlan() {

    }

    public void setTestCases(Set<TestCase> testCases) {
        this.testCases = testCases;
    }

    public void setEnvironments(Set<Environment> environments) {
        this.environments = environments;
    }

    public void setMessageDevices(Set<MessageDevice> messageDevices) {
        this.messageDevices = messageDevices;
    }

    public Set<TestCase> getTestCases() {
        return testCases;
    }

    public Set<Environment> getEnvironments() {
        return environments;
    }

    public Set<MessageDevice> getMessageDevices() {
        return messageDevices;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ITestCase getTestCase(int index) {
        return Iterators.get(this.testCases.iterator(), index);
    }

    @Override
    public int getTestCaseCount() {
        return this.testCases.size();
    }

    @Override
    public IEnvironment getEnvironment(int index) {
        return Iterators.get(this.environments.iterator(), index);
    }

    @Override
    public int getEnvironmentCount() {
        return this.environments.size();
    }

    @Override
    public IMessageDevice getMessageDevice(int index) {
        return Iterators.get(this.messageDevices.iterator(), index);
    }

    @Override
    public int getMessageDeviceCount() {
        return this.messageDevices.size();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
