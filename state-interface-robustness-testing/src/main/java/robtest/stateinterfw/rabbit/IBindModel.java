package robtest.stateinterfw.rabbit;

public interface IBindModel {
    String getSource();
    String getSourceType();
    String getDestination();
    String getDestinationType();
    String getRoutingKey();
    String getVirtualHost();
}
