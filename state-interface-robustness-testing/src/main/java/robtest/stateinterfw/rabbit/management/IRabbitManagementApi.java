package robtest.stateinterfw.rabbit.management;

import java.util.List;

public interface IRabbitManagementApi {
    List<IQueueModel> listQueues(String virtualHost);
    List<IVirtualHostModel> listVirtualHosts();
    void queueDeclare(QueueDeclareModel declareModel, String virtualHost);
    void queueDelete(String name, String virtualHost);
    List<IExchangeModel> listExchanges(String virtualHost);
    void exchangeDeclare(ExchangeDeclareModel declareModel, String virtualHost);
    void exchangeDelete(String name, String virtualHost);
    void queueBind(QueueBindModel bindModel, String virtualHost);
    List<IBindModel> listBindings(String virtualHost);
    IExchangeModel detailExchange(String name, String virtualHost);
}
