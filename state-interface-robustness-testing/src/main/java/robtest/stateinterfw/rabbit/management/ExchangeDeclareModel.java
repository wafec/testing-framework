package robtest.stateinterfw.rabbit.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ExchangeDeclareModel {
    private String name;
    private String exchangeType;
    private boolean autoDelete;
    private boolean durable;
    private boolean internal;

    public ExchangeDeclareModel() {

    }

    public ExchangeDeclareModel(String name, String exchangeType) {
        this(name, exchangeType, false, false, false);
    }

    public ExchangeDeclareModel(String name, String exchangeType, boolean autoDelete, boolean durable, boolean internal) {
        this.exchangeType = exchangeType;
        this.name = name;
        this.autoDelete = autoDelete;
        this.durable = durable;
        this.internal = internal;
    }

    @JsonSetter("type")
    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JsonSetter("auto_delete")
    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public boolean getAutoDelete() {
        return this.autoDelete;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean getDurable() {
        return durable;
    }

    public boolean getInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }
}
