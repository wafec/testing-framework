package robtest.stateinterfw.openStack.cli;

import robtest.stateinterfw.openStack.cli.models.NetworkResult;

import java.util.Arrays;
import java.util.List;

public class NetworkClient extends BaseClient {
    public NetworkClient() {
        super("http://localhost:5000/networks");
    }

    public List<NetworkResult> listNetworks(int testId) {
        var opt = this.request(NetworkResult[].class, String.format("?test_id=%d", testId), null, "get");
        return opt.map(Arrays::asList).orElse(null);
    }
}
