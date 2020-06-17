package robtest.os.cli;

import robtest.os.cli.models.FlavorResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlavorClient extends BaseClient {
    public FlavorClient() {
        super("http://localhost:5000/flavors");
    }

    public List<FlavorResult> listFlavors(Integer testId) {
        var opt = this.request(FlavorResult[].class, String.format("/test_id=%d", testId), null, "get");
        return opt.map(Arrays::asList).orElse(null);
    }
}
