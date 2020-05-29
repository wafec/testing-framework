package robtest.stateinterfw.rabbit.management;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.rabbit.management.IBindModel;

public class BindModel implements IBindModel {
    private String source;
    private String sourceType;
    private String destination;
    private String destinationType;
    private String routingKey;
    private String virtualHost;

    public BindModel() {

    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getSourceType() {
        return StringUtils.isEmpty(sourceType) ? "exchange" : sourceType;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public String getDestinationType() {
        return destinationType;
    }

    @Override
    public String getRoutingKey() {
        return routingKey;
    }

    @Override
    public String getVirtualHost() {
        return virtualHost;
    }

    @JsonSetter("source_type")
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @JsonSetter("destination_type")
    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    @JsonSetter("routing_key")
    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @JsonSetter("vhost")
    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
