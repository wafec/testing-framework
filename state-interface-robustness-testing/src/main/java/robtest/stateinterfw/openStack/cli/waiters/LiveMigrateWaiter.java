package robtest.stateinterfw.openStack.cli.waiters;

import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Optional;

public class LiveMigrateWaiter extends AbstractServerWaiter {
    private boolean migrated = false;

    public LiveMigrateWaiter(int testId, String serverName) {
        super(testId, serverName);
    }

    @Override
    protected boolean evaluate(ServerResult server, FlavorResult flavor, ImageResult image, NetworkResult network) {
        if (!migrated) {
            migrated = Optional.ofNullable(server.getStatus()).map(String::toLowerCase).map(x -> x.equals("migrating")).orElse(false);
            return false;
        } else {
            return Optional.ofNullable(server.getStatus()).map(String::toLowerCase).map(x -> x.equals("active")).orElse(false);
        }
    }
}
