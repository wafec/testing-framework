package robtest.stateinterfw.openStack.cli;

import robtest.stateinterfw.openStack.cli.models.VolumeResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VolumeClient extends BaseClient {
    public VolumeClient() {
        super("/storage_api/volumes");
    }

    public List<VolumeResult> volumeList(int testId) {
        return this.request(VolumeResult[].class, String.format("/%d", testId), null, "get")
                .map(Arrays::asList).orElse(null);
    }

    public VolumeResult volumeCreate(int testId, String name, String availabilityZone, int sizeGb) {
        return this.request(VolumeResult.class, String.format("/%d", testId),
                Map.of("name", name, "zone", availabilityZone, "size", sizeGb), "post")
                .orElse(null);
    }

    public VolumeResult volumeDetails(int testId, String name) {
        return this.request(VolumeResult.class, String.format("/%d/volume?volume_name=%s", testId, name),
                null, "get").orElse(null);
    }

    public void volumeDelete(int testId, String name) {
        this.request(null, String.format("/%d/volume?volume_name=%s", testId, name), null, "delete");
    }

    public void volumeExtend(int testId, String name, int sizeGb) {
        this.request(null, String.format("/%d/volume/extend", testId),
                Map.of("name", name, "size", sizeGb), "post");
    }

    public void volumeAttach(int testId, String name, String serverName, String mountpoint) {
        this.request(null, String.format("/%d/volume/attach", testId),
                Map.of("name", name, "server", serverName, "mountpoint", mountpoint), "post");
    }

    public void volumeDetach(int testId, String name) {
        this.request(null, String.format("/%d/volume/detach", testId),
                Map.of("name", name), "post");
    }
        
}
