package robtest.stateinterfw.openStack.cli.models;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ImageResult {
    private String id;
    private String name;
    private String diskFormat;
    private String containerFormat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiskFormat() {
        return diskFormat;
    }

    @JsonSetter("disk_format")
    public void setDiskFormat(String diskFormat) {
        this.diskFormat = diskFormat;
    }

    public String getContainerFormat() {
        return containerFormat;
    }

    @JsonSetter("container_format")
    public void setContainerFormat(String containerFormat) {
        this.containerFormat = containerFormat;
    }

    @Override
    public String toString() {
        return String.format("<OSImage(id=%s, name=%s, diskFormat=%s, containerFormat=%s)>",
                id, name, diskFormat, containerFormat);
    }
}
