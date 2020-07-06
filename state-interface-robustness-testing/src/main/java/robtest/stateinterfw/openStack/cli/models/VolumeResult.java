package robtest.stateinterfw.openStack.cli.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class VolumeResult {
    private String id;
    private String name;
    private String status;
    private String availabilityZone;
    private int size;
    private AttachmentResult[] attachments;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonGetter("availability_zone")
    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public AttachmentResult[] getAttachments() {
        return attachments;
    }

    public void setAttachments(AttachmentResult[] attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return String.format("<OSVolume(id=%s, name=%s, status=%s, zone=%s, size=%d, attachments=\"%s\")>", id, name, status, availabilityZone, size,
                Optional.ofNullable(attachments).map(a -> Arrays.stream(a).map(AttachmentResult::getDevice).collect(Collectors.joining(", "))).orElse(""));
    }

    public static class AttachmentResult {
        private String id;
        private String attachmentId;
        private String volumeId;
        private String serverId;
        private String hostname;
        private String device;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAttachmentId() {
            return attachmentId;
        }

        @JsonSetter("attachment_id")
        public void setAttachmentId(String attachmentId) {
            this.attachmentId = attachmentId;
        }

        public String getVolumeId() {
            return volumeId;
        }

        @JsonSetter("volume_id")
        public void setVolumeId(String volumeId) {
            this.volumeId = volumeId;
        }

        public String getServerId() {
            return serverId;
        }

        @JsonSetter("server_id")
        public void setServerId(String serverId) {
            this.serverId = serverId;
        }

        public String getHostname() {
            return hostname;
        }

        @JsonSetter("host_name")
        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }
    }
}
