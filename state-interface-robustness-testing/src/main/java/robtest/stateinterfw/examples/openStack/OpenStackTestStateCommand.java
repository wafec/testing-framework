package robtest.stateinterfw.examples.openStack;

import robtest.stateinterfw.*;
import robtest.stateinterfw.data.TestStateVerdictItem;
import robtest.stateinterfw.examples.openStack.content.OpenStackUserContent;
import robtest.stateinterfw.openStack.cli.waiters.PowerOnWaiter;

public class OpenStackTestStateCommand extends TestStateCommand implements IOpenStackTestStateCommand {
    private OpenStackTestExecutionContextWrapper contextWrapper;

    public OpenStackTestStateCommand() {
        this.configure();
    }

    @Override
    public ITestStateVerdict command(ITestExecutionContext testExecutionContext, ITestInput testInput) {
        this.contextWrapper = new OpenStackTestExecutionContextWrapper(testExecutionContext);
        return super.command(testExecutionContext, testInput);
    }

    OpenStackUserContent getUserContent() {
        return contextWrapper.getUserContent();
    }

    private void configure() {
        add("compute.flavor.created", this::flavorCreated);
        add("image.created", this::imageCreated);
        add("compute.server.created", this::serverCreated);
        add("compute.server.poweron", this::serverPowerOn);
    }

    protected ITestStateVerdictItem flavorCreated(ITestState state, ITestInputArgs args) {
        return TestStateVerdictItem.createPassOk(state);
    }

    protected ITestStateVerdictItem imageCreated(ITestState state, ITestInputArgs args) {
        return TestStateVerdictItem.createPassOk(state);
    }

    protected ITestStateVerdictItem serverCreated(ITestState state, ITestInputArgs args) {
        return TestStateVerdictItem.createPassOk(state);
    }

    protected ITestStateVerdictItem serverPowerOn(ITestState state, ITestInputArgs args) {
        var userContent = getUserContent();
        var name = args.get("name").getDataValue();
        var result = new PowerOnWaiter(userContent.getId(), name, state.getState().getTimeout()).waitCondition();
        if (result.get()) {
            return TestStateVerdictItem.createPassOk(state, result.getTimeSpent());
        } else {
            return TestStateVerdictItem.createInvalidStateError(state, result.getTimeSpent(), "Server could not power on");
        }
    }
}
