package robtest.stateinterfw.rabbit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class QueueDeclareModel {
    private String name;
    private boolean autoDelete;
    private boolean durable;

    public QueueDeclareModel() {
        this(null);
    }

    public QueueDeclareModel(String name) {
        this(name, false);
    }

    public QueueDeclareModel(String name, boolean autoDelete) {
        this(name, autoDelete, false);
    }

    public QueueDeclareModel(String name, boolean autoDelete, boolean durable) {
        this.name = name;
        this.autoDelete = autoDelete;
        this.durable = durable;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    @JsonGetter("auto_delete")
    public boolean getAutoDelete() {
        return this.autoDelete;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean getDurable() {
        return durable;
    }
}
