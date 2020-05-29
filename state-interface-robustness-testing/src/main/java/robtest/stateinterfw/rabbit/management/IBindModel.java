package robtest.stateinterfw.rabbit.management;

public interface IBindModel {
    String getSource();
    String getSourceType();
    String getDestination();
    String getDestinationType();
    String getRoutingKey();
    String getVirtualHost();
}
