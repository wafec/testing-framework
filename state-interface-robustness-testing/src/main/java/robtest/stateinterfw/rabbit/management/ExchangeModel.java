package robtest.stateinterfw.rabbit.management;

import com.fasterxml.jackson.annotation.JsonSetter;
import robtest.stateinterfw.rabbit.management.IExchangeModel;

public class ExchangeModel implements IExchangeModel {
    private String name;
    private String exchangeType;
    private boolean durable;
    private boolean autoDelete;
    private boolean internal;

    public ExchangeModel() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExchangeType() {
        return exchangeType;
    }

    @Override
    public boolean getDurable() {
        return durable;
    }

    @Override
    public boolean getAutoDelete() {
        return autoDelete;
    }

    @Override
    public boolean getInternal() {
        return internal;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonSetter("type")
    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }
}
