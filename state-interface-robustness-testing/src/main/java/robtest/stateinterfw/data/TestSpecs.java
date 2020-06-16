package robtest.stateinterfw.data;

import robtest.stateinterfw.IEnvironment;
import robtest.stateinterfw.IMessageDevice;
import robtest.stateinterfw.ITestSpecs;

import java.util.Iterator;
import java.util.Set;

public class TestSpecs implements ITestSpecs {
    private int id;
    private TestPlan testPlan;

    public TestSpecs() {

    }

    public TestSpecs(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    @Override
    public IEnvironment getEnvironment(int index) {
        return testPlan.getEnvironment(index);
    }

    @Override
    public int getEnvironmentCount() {
        return testPlan.getEnvironmentCount();
    }

    @Override
    public IMessageDevice getMessageDevice(int index) {
        return testPlan.getMessageDevice(index);
    }

    @Override
    public int getMessageDeviceCount() {
        return testPlan.getMessageDeviceCount();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public TestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlan testPlan) {
        this.testPlan = testPlan;
    }
}
