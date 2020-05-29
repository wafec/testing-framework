package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;

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
                if (StringUtils.isEmpty(binding.getSource()))
                    continue;
                RabbitBind bind = repository.querySingleResult("from RabbitBind bind where bind.source.name = :source and bind.destination.name = :destination",
                        RabbitBind.class, Param.list("source", binding.getSource()).add("destination", binding.getDestination()).all());
                if (bind == null) {
                    RabbitExchange exchange = repository.querySingleResult("from RabbitExchange where name = :name",
                            RabbitExchange.class, Param.list("name", binding.getSource()));
                    RabbitQueue queue = repository.querySingleResult("from RabbitQueue where name = :name",
                            RabbitQueue.class, Param.list("name", binding.getDestination()));
                    if (exchange == null) {
                        exchange = new RabbitExchange(binding.getSource(), binding.getSourceType(), binding.getVirtualHost(), (RabbitMessageDevice) rabbitMessageDevice);
                        repository.save(exchange);
                    }
                    if (queue == null) {
                        queue = new RabbitQueue(binding.getDestination(), binding.getVirtualHost(), (RabbitMessageDevice)rabbitMessageDevice);
                        repository.save(queue);
                    }
                    bind = new RabbitBind(exchange, queue, binding.getRoutingKey(), binding.getVirtualHost(), (RabbitMessageDevice) rabbitMessageDevice);
                    repository.save(bind);
                }
                result.add(bind);
            }
        }
        return result;
    }
}
