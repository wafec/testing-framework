package robtest.stateinterfw.rabbit.management;

import org.apache.commons.lang3.StringUtils;
import robtest.core.web.AbstractApi;
import robtest.stateinterfw.rabbit.management.*;

import java.util.*;

public class RabbitManagementApi extends AbstractApi implements IRabbitManagementApi {
    public RabbitManagementApi(String host, int port, String user, String password) {
        super(host, port, user, password);
    }

    private String parseVirtualHost(String virtualHost) {
        if (StringUtils.isEmpty(virtualHost))
            return "%2F";
        return virtualHost;
    }

    @Override
    public List<IQueueModel> listQueues(String virtualHost) {
        var result = request("/queues", "GET", null, null, QueueModel.class, true);
        return list(result, IQueueModel.class);
    }

    @Override
    public List<IVirtualHostModel> listVirtualHosts() {
        var result = request("/vhosts", "GET", null, null, VirtualHostModel.class, true);
        return list(result, IVirtualHostModel.class);
    }

    @Override
    public void queueDeclare(QueueDeclareModel declareModel, String virtualHost) {
        request(String.format("/queues/%s/%s", parseVirtualHost(virtualHost), declareModel.getName()),
                "PUT", declareModel, null, null);
    }

    @Override
    public void queueDelete(String name, String virtualHost) {
        request(String.format("/queues/%s/%s", parseVirtualHost(virtualHost), name), "DELETE", null, null);
    }

    @Override
    public List<IExchangeModel> listExchanges(String virtualHost) {
        var result = request(String.format("/exchanges/%s", parseVirtualHost(virtualHost)), "GET", null, null, ExchangeModel.class, true);
        return list(result, IExchangeModel.class);
    }

    @Override
    public void exchangeDeclare(ExchangeDeclareModel declareModel, String virtualHost) {
        request(String.format("/exchanges/%s/%s", parseVirtualHost(virtualHost), declareModel.getName()), "PUT", declareModel, null, null);
    }

    @Override
    public void exchangeDelete(String name, String virtualHost) {
        request(String.format("/exchanges/%s/%s", parseVirtualHost(virtualHost), name), "DELETE", null, null, null);
    }

    @Override
    public void queueBind(QueueBindModel bindModel, String virtualHost) {
        request(String.format("/bindings/%s/e/%s/q/%s", parseVirtualHost(virtualHost), bindModel.getQueue(), bindModel.getExchange()), "POST", bindModel, null, null);
    }

    @Override
    public List<IBindModel> listBindings(String virtualHost) {
        var result = request(String.format("/bindings/%s", parseVirtualHost(virtualHost)), "GET", null, null, BindModel.class, true);
        return list(result, IBindModel.class);
    }

    @Override
    public IExchangeModel detailExchange(String name, String virtualHost) {
        var result = request(String.format("/exchanges/%s/%s", parseVirtualHost(virtualHost), name), "GET", null, null, ExchangeModel.class);
        return (IExchangeModel) result;
    }
}
