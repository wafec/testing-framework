package robtest.stateinterfw.openStack.cli.waiters;

import robtest.core.ConditionalWaiter;
import robtest.core.ConditionalWaiterResult;
import robtest.stateinterfw.openStack.cli.ServerClient;
import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class StopWaiter extends AbstractServerWaiter {
    private int testId;
    private String serverName;

    public StopWaiter(int testId, String serverName) {
        super(testId, serverName);
    }

    @Override
    protected boolean evaluate(ServerResult serverDetails, FlavorResult flavor, ImageResult image, NetworkResult network) {
        return Optional.ofNullable(serverDetails.getStatus()).map(s -> s.toLowerCase().equals("shutoff")).orElse(false) &&
                Optional.ofNullable(serverDetails.getVmState()).map(s -> s.toLowerCase().equals("stopped")).orElse(false);
    }
}
