package robtest.stateinterfw.rabbit.management;

import com.fasterxml.jackson.annotation.JsonSetter;
import robtest.stateinterfw.rabbit.management.IQueueModel;

public class QueueModel implements IQueueModel {
    private String name;
    private String virtualHost;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter("vhost")
    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getVirtualHost() {
        return this.virtualHost;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean getDurable() {
        return this.durable;
    }

    @JsonSetter("auto_delete")
    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public boolean getAutoDelete() {
        return autoDelete;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public boolean getExclusive() {
        return exclusive;
    }
}
