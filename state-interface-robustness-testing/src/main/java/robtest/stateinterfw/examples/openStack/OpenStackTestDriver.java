package robtest.stateinterfw.examples.openStack;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;
import robtest.stateinterfw.ITestDriver;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.TestDriver;

public class OpenStackTestDriver extends TestDriver implements IOpenStackTestDriver {
    private ITestExecutionContext _testExecutionContext;

    @Override
    public void initialize(ITestExecutionContext testExecutionContext) {
        _testExecutionContext = testExecutionContext;
    }

    @Override
    public void executeNext() {
        this._testExecutionContext.getCurrent().getAction();
        OSClient.OSClientV3 os = OSFactory.builderV3()
                .endpoint("")
                .credentials("user id", "password")
                .authenticate();
        var serverCreate = os.compute().servers().serverBuilder().flavor("").image("").build();

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    protected ITestExecutionContext getTestExecutionContext() {
        return _testExecutionContext;
    }
}
