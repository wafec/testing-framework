package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Optional;

public class SuspendWaiter extends AbstractServerWaiter {
    public SuspendWaiter(int testId, String serverName) {
        super(testId, serverName);
    }

    @Override
    protected boolean evaluate(ServerResult server, FlavorResult flavor, ImageResult image, NetworkResult network) {
        return Optional.ofNullable(server.getStatus()).map(s -> s.toLowerCase().equals("suspended")).orElse(false);
    }
}
