package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IRepository;

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
    public List<IQueue> listQueues(IRabbitMessageDevice rabbitMessageDevice) {
        var managementApi = managementFactory.build(rabbitMessageDevice);
        for (var virtualHost : managementApi.listVirtualHosts()) {
            String name = null;
            if (virtualHost != null && !virtualHost.getName().equals("/"))
                name = virtualHost.getName();
            for (var binding : managementApi.listBindings(name)) {
                Queue queue = new Queue();
                queue.setName(binding.getDestination());
                queue.setExchange(binding.getSource());
            }
        }
    }
}
