package robtest.stateinterfw.openStack.cli.waiters;

import robtest.core.ConditionalWaiter;
import robtest.core.ConditionalWaiterResult;
import robtest.stateinterfw.openStack.cli.ServerClient;
import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.concurrent.TimeUnit;

public class PowerOnWaiter extends AbstractServerWaiter {
    private int testId;
    private String serverName;

    public PowerOnWaiter(int testId, String serverName) {
        super(testId, serverName);
    }

    @Override
    protected boolean evaluate(ServerResult server, FlavorResult flavor, ImageResult image, NetworkResult network) {
        return Integer.parseInt(server.getPowerState()) == 1;
    }
}
