package robtest.stateinterfw.os.cli;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import robtest.stateinterfw.os.cli.models.ImageResult;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImageClient extends BaseClient {
    public ImageClient() {
        super("http://localhost:5000/images");
    }

    public List<ImageResult> listImages(Integer testId) {
        var opt = this.request(ImageResult[].class, String.format("?test_id=%d", testId), null, "get");
        return opt.map(Arrays::asList).orElse(null);
    }

    public ImageResult imageCreate(Integer testId, String name, String diskFormat, String containerFormat, File data) {
        try {
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(data));
            var opt = this.request(ImageResult.class, String.format("?test_id=%d", testId),
                    Map.of("name", name, "disk_format", diskFormat, "container_format", containerFormat,
                    "data", new String(encoded, StandardCharsets.US_ASCII)), "post");
            return opt.orElse(null);
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public void imageDelete(Integer testId, String name) {
        this.request(null, String.format("?test_id=%d&image_name=%s", testId, name), null, "delete");
    }
}
