package robtest.stateinterfw.hbm;

import robtest.stateinterfw.ITestCase;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.ITestInput;

public class TestExecutionContext implements ITestExecutionContext {
    @Override
    public ITestCase get() {
        return null;
    }

    @Override
    public ITestInput getCurrent() {
        return null;
    }
}
