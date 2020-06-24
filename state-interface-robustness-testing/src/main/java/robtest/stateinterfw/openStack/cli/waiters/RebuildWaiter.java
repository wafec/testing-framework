package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Optional;

public class RebuildWaiter extends AbstractServerWaiter {
    private boolean rebuildStatus = false;

    public RebuildWaiter(int testId, String serverName) {
        super(testId, serverName);
    }

    @Override
    protected boolean evaluate(ServerResult server, FlavorResult flavor, ImageResult image, NetworkResult network) {
        if (!rebuildStatus) {
            rebuildStatus = Optional.ofNullable(server.getStatus()).map(s -> s.toLowerCase().equals("rebuild")).orElse(false);
            return false;
        } else {
            return Optional.ofNullable(server.getStatus()).map(s -> s.toLowerCase().equals("active")).orElse(false);
        }
    }
}
