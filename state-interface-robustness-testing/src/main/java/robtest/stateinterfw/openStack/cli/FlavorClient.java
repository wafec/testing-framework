package robtest.stateinterfw.openStack.cli;

import robtest.stateinterfw.openStack.cli.models.FlavorResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FlavorClient extends BaseClient {
    public FlavorClient() {
        super("http://localhost:5000/flavors");
    }

    public List<FlavorResult> listFlavors(Integer testId) {
        var opt = this.request(FlavorResult[].class, String.format("?test_id=%d", testId), null, "get");
        return opt.map(Arrays::asList).orElse(null);
    }

    public FlavorResult createFlavor(Integer testId, String name, int ram, int vcpus, int disk) {
        var opt = this.request(FlavorResult.class, String.format("?test_id=%d", testId),
                Map.of("name", name, "ram", ram, "vcpus", vcpus, "disk", disk), "post");
        return opt.orElse(null);
    }

    public void deleteFlavor(Integer testId, String name) {
        this.request(null, String.format("?test_id=%d&flavor_name=%s", testId, name), null, "delete");
    }
}
