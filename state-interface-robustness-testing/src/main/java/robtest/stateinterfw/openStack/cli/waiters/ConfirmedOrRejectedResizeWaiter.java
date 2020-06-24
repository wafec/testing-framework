package robtest.stateinterfw.openStack.cli.waiters;

import robtest.core.ConditionalWaiter;
import robtest.core.ConditionalWaiterResult;
import robtest.stateinterfw.openStack.cli.FlavorClient;
import robtest.stateinterfw.openStack.cli.ServerClient;
import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ConfirmedOrRejectedResizeWaiter extends AbstractServerWaiter {
    private String flavorName;

    public ConfirmedOrRejectedResizeWaiter(int testId, String serverName, String flavorName) {
        super(testId, serverName);
        this.flavorName = flavorName;
    }

    @Override
    protected boolean evaluate(ServerResult server, FlavorResult flavorOld, ImageResult image, NetworkResult network) {
        FlavorClient flavorClient = new FlavorClient();
        var flavor = flavorClient.flavorDetails(testId, flavorName);
        return Optional.ofNullable(server.getStatus()).map(s -> s.toLowerCase().equals("active")).orElse(false) &&
                server.getFlavor().equals(flavor.getName());
    }
}
