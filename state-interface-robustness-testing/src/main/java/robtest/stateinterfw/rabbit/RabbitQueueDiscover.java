package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;
import robtest.stateinterfw.rabbit.management.IExchangeModel;

import java.util.ArrayList;
import java.util.List;

public class RabbitQueueDiscover implements IRabbitQueueDiscover {
    private IRepository repository;
    private IRabbitManagementFactory managementFactory;

    @Inject
    public RabbitQueueDiscover(IRepository repository, IRabbitManagementFactory managementFactory) {
        this.repository = repository;
        this.managementFactory = managementFactory;
    }

    @Override
    public List<IRabbitBind> listBindings(IRabbitMessageDevice rabbitMessageDevice) {
        var managementApi = managementFactory.build(rabbitMessageDevice);
        List<IRabbitBind> result = new ArrayList<>();
        for (var virtualHost : managementApi.listVirtualHosts()) {
            for (var binding : managementApi.listBindings(virtualHost.getName())) {
                // default exchange (source) cannot be bound
                // reply is a queue or an exchange is used by services to return values
                // avoid reply queues and exchanges in the interception initialization
                // trying intercept them can cause some crashes
                if (StringUtils.isEmpty(binding.getSource()) ||
                    binding.getSource().startsWith("reply") ||
                    binding.getDestination().startsWith("reply"))
                    continue;
                // fanout is very hard to handle and it is better in in this code to skip the handling of them
                // fanout feature is to broadcast messages
                // we can't know which are the receivers as they can appear in any time
                // consumer queues of fanout exchanges are often not durable and it did not require routing keys
                if (binding.getSourceType().equals("exchange")) {
                    var exchangeDetails = managementApi.detailExchange(binding.getSource(), binding.getVirtualHost());
                    if (StringUtils.isNotEmpty(exchangeDetails.getExchangeType()) && exchangeDetails.getExchangeType().equals("fanout")) {
                        System.out.println(String.format("Skipping '%s' due to invalid exchange type (fanout)!", exchangeDetails.getName()));
                        continue;
                    }
                }
                System.out.println(String.format("Source: %s, Destination: %s", binding.getSource(), binding.getDestination()));
                RabbitBind bind = repository.querySingleResult("from RabbitBind bind where bind.source.name = :source and bind.destination.name = :destination and bind.virtualHost = :virtualHost",
                        RabbitBind.class, Param.list("source", binding.getSource()).add("destination", binding.getDestination())
                                .add("virtualHost", binding.getVirtualHost()).all());
                IExchangeModel exchangeModel = null;
                if (bind == null) {
                    RabbitExchange exchange = repository.querySingleResult("from RabbitExchange where name = :name and virtualHost = :virtualHost",
                            RabbitExchange.class, Param.list("name", binding.getSource())
                                    .add("virtualHost", binding.getVirtualHost()).all());
                    RabbitQueue queue = repository.querySingleResult("from RabbitQueue where name = :name and virtualHost = :virtualHost",
                            RabbitQueue.class, Param.list("name", binding.getDestination())
                                    .add("virtualHost", binding.getVirtualHost()).all());
                    if (exchange == null) {
                        exchangeModel = managementApi.detailExchange(binding.getSource(), binding.getVirtualHost());
                        exchange = new RabbitExchange(exchangeModel.getName(), exchangeModel.getExchangeType(), binding.getVirtualHost(), (RabbitMessageDevice) rabbitMessageDevice);
                        repository.save(exchange);
                    }
                    if (queue == null) {
                        queue = new RabbitQueue(binding.getDestination(), binding.getVirtualHost(), (RabbitMessageDevice)rabbitMessageDevice);
                        repository.save(queue);
                    }
                    bind = new RabbitBind(exchange, queue, binding.getRoutingKey(), binding.getVirtualHost(), (RabbitMessageDevice) rabbitMessageDevice);
                    repository.save(bind);
                }
                if (exchangeModel == null) {
                    exchangeModel = managementApi.detailExchange(binding.getSource(), binding.getVirtualHost());
                    ((RabbitExchange) bind.getSource()).setExchangeType(exchangeModel.getExchangeType());
                    repository.update(bind.getSource());
                }
                result.add(bind);
            }
        }
        return result;
    }
}
