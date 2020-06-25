package robtest.stateinterfw.openStack.cli;

import com.mysql.cj.util.StringUtils;
import robtest.stateinterfw.openStack.cli.models.NetworkResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NetworkClient extends BaseClient {
    public NetworkClient() {
        super("/servers_api/networks");
    }

    public List<NetworkResult> listNetworks(int testId) {
        var opt = this.request(NetworkResult[].class, String.format("?test_id=%d", testId), null, "get");
        return opt.map(Arrays::asList).orElse(null);
    }

    public NetworkResult networkDetails(int testId, String networkName) {
        return networkDetails(testId, networkName, null);
    }

    public NetworkResult networkDetails(int testId, String networkName, String id) {
        if (StringUtils.isNullOrEmpty(networkName) && StringUtils.isNullOrEmpty(id))
            return null;
        var opt = this.request(NetworkResult.class, String.format("/details?test_id=%d&network_name=%s&network_id=%s", testId, Optional.ofNullable(networkName).orElse(""), Optional.ofNullable(id).orElse("")), null, "get");
        return opt.orElse(null);
    }
}
