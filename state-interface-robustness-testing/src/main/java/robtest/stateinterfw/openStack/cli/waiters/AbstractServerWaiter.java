package robtest.stateinterfw.openStack.cli.waiters;

import com.mysql.cj.util.StringUtils;
import robtest.core.ConditionalWaiter;
import robtest.core.ConditionalWaiterResult;
import robtest.stateinterfw.openStack.cli.FlavorClient;
import robtest.stateinterfw.openStack.cli.ImageClient;
import robtest.stateinterfw.openStack.cli.NetworkClient;
import robtest.stateinterfw.openStack.cli.ServerClient;
import robtest.stateinterfw.openStack.cli.models.FlavorResult;
import robtest.stateinterfw.openStack.cli.models.ImageResult;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;
import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.concurrent.TimeUnit;

public abstract class AbstractServerWaiter implements IOSWaiter {
    protected int testId;
    protected String serverName;
    private long timeout;
    private long interval;

    public AbstractServerWaiter(int testId, String serverName) {
        this(testId, serverName, TimeUnit.MILLISECONDS.convert(2, TimeUnit.MINUTES));
    }

    public AbstractServerWaiter(int testId, String serverName, long timeout) {
        this(testId, serverName, timeout, 100);
    }

    public AbstractServerWaiter(int testId, String serverName, long timeout, long interval) {
        this.testId = testId;
        this.serverName = serverName;
        this.timeout = timeout;
        this.interval = interval;
    }

    protected abstract boolean evaluate(ServerResult server, FlavorResult flavor,
                               ImageResult image, NetworkResult network);

    @Override
    public ConditionalWaiterResult waitCondition() {
        ServerClient serverClient = new ServerClient();
        FlavorClient flavorClient = new FlavorClient();
        NetworkClient networkClient = new NetworkClient();
        ImageClient imageClient = new ImageClient();
        if (StringUtils.isNullOrEmpty(serverName))
            throw new IllegalArgumentException("Server name cannot be null");
        return ConditionalWaiter.waitFor(() -> {
            final var server = serverClient.serverDetails(testId, serverName);
            if (server == null)
                throw new IllegalArgumentException("Server not found");
            final var image = imageClient.imageDetails(testId, null, server.getImage());
            final var flavor = flavorClient.flavorDetails(testId, null, server.getFlavor());
            final var network = networkClient.networkDetails(testId, null, server.getNetwork());
            System.out.println(server.toString());
            return evaluate(server, flavor, image, network);
        }, timeout, interval);
    }
}
