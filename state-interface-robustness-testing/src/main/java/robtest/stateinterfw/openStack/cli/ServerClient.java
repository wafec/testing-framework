package robtest.stateinterfw.openStack.cli;

import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ServerClient extends BaseClient {
    public ServerClient() {
        super("/servers_api/servers");
    }

    public List<ServerResult> listServers(int testId) {
        var opt = this.request(ServerResult[].class, String.format("?test_id=%d", testId), null, "get");
        return opt.map(Arrays::asList).orElse(null);
    }

    public ServerResult createServer(int testId, String name, String image, String flavor, String network) {
        var opt = this.request(ServerResult.class, String.format("?test_id=%d", testId),
                Map.of("image", image, "flavor", flavor, "network", network, "name", name),
                "post");
        return opt.orElse(null);
    }

    public ServerResult serverDetails(int testId, String serverName) {
        var opt = this.request(ServerResult.class, String.format("/details?test_id=%d&server_name=%s", testId, serverName), null, "get");
        return opt.orElse(null);
    }

    public void serverDelete(int testId, String serverName) {
        this.request(null, String.format("?test_id=%d&server_name=%s", testId, serverName), null, "delete");
    }

    public void serverResize(int testId, String serverName, String targetFlavorName) {
        this.request(null, String.format("/resize?test_id=%d", testId),
                Map.of("server", serverName, "flavor", targetFlavorName), "post");
    }

    public void serverConfirmOrRejectResize(int testId, String serverName, boolean confirm) {
        this.request(null, String.format("/resize?test_id=%d", testId),
                Map.of("server", serverName, "confirm", confirm), "put");
    }

    public void serverSuspend(int testId, String serverName) {
        this.request(null, String.format("/suspend?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverResume(int testId, String serverName) {
        this.request(null, String.format("/resume?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverPause(int testId, String serverName) {
        this.request(null, String.format("/pause?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverUnpause(int testId, String serverName) {
        this.request(null, String.format("/unpause?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverShelve(int testId, String serverName) {
        this.request(null, String.format("/shelve?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverShelveOffload(int testId, String serverName) {
        this.request(null, String.format("/shelve-offload?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverUnshelve(int testId, String serverName) {
        this.request(null, String.format("/unshelve?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverStop(int testId, String serverName) {
        this.request(null, String.format("/stop?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverStart(int testId, String serverName) {
        this.request(null, String.format("/start?test_id=%d", testId), Map.of("server", serverName), "post");
    }

    public void serverRebuild(int testId, String serverName, String imageName) {
        this.request(null, String.format("/rebuild?test_id=%d", testId),
                Map.of("server", serverName, "image", imageName), "post");
    }

    public void serverMigrate(int testId, String serverName) {
        this.request(null, String.format("/migrate?test_id=%d", testId),
                Map.of("server", serverName), "post");
    }

    public void serverLiveMigrate(int testId, String serverName) {
        this.request(null, String.format("/live-migrate?test_id=%d", testId),
                Map.of("server", serverName), "post");
    }
}
