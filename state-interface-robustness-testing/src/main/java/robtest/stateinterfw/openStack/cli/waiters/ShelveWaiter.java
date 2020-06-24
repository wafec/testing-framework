package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Optional;

public class ShelveWaiter extends AbstractServerWaiter {
    public ShelveWaiter(int testId, String serverName) {
        super(testId, serverName);
    }

    @Override
    protected boolean evaluate(ServerResult server, FlavorResult flavor, ImageResult image, NetworkResult network) {
        return Optional.ofNullable(server.getStatus()).map(s -> s.toLowerCase().equals("shelved_offloaded")).orElse(false) &&
                Optional.ofNullable(server.getVmState()).map(s -> s.toLowerCase().equals("shelved_offloaded")).orElse(false);
    }
}
