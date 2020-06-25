package robtest.stateinterfw.openStack.cli;

import com.mysql.cj.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import robtest.stateinterfw.openStack.cli.models.ImageResult;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ImageClient extends BaseClient {
    public ImageClient() {
        super("/servers_api/images");
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

    public ImageResult imageDetails(int testId, String imageName) {
        return imageDetails(testId, imageName, null);
    }

    public ImageResult imageDetails(int testId, String imageName, String id) {
        if (StringUtils.isNullOrEmpty(imageName) && StringUtils.isNullOrEmpty(id))
            return null;
        var opt = this.request(ImageResult.class, String.format("/details?test_id=%d&image_name=%s&image_id=%s", testId, Optional.ofNullable(imageName).orElse(""), Optional.ofNullable(id).orElse("")), null, "get");
        return opt.orElse(null);
    }
}
