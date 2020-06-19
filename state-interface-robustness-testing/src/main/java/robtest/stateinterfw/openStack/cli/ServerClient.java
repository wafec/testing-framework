package robtest.stateinterfw.openStack.cli;

import robtest.stateinterfw.openStack.cli.models.ServerResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ServerClient extends BaseClient {
    public ServerClient() {
        super("http://localhost:5000/servers");
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
}
