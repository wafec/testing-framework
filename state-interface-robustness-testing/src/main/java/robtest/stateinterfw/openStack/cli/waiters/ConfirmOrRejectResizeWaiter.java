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

public class ConfirmOrRejectResizeWaiter extends AbstractServerWaiter {
    private int testId;
    private String serverName;

    public ConfirmOrRejectResizeWaiter(int testId, String serverName) {
        super(testId, serverName, TimeUnit.MILLISECONDS.convert(4, TimeUnit.MINUTES));
    }

    @Override
    protected boolean evaluate(ServerResult server, FlavorResult flavor, ImageResult image, NetworkResult network) {
        return Integer.parseInt(server.getPowerState()) == 1 &&
                Optional.ofNullable(server.getStatus()).map(s -> s.toLowerCase().equals("verify_resize")).orElse(false) &&
                Optional.ofNullable(server.getVmState()).map(s -> s.toLowerCase().equals("resized")).orElse(false);
    }
}
