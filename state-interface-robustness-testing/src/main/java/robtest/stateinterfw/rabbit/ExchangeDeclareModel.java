package robtest.stateinterfw.rabbit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ExchangeDeclareModel {
    private String name;
    private String exchangeType;

    public ExchangeDeclareModel() {

    }

    public ExchangeDeclareModel(String name, String exchangeType) {
        this.exchangeType = exchangeType;
        this.name = name;
    }

    @JsonSetter("exchange_type")
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
}
