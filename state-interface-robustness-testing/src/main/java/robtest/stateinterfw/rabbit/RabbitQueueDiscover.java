package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IRepository;

import java.util.List;

public class RabbitQueueDiscover implements IRabbitQueueDiscover {
    private IRepository repository;

    @Inject
    public RabbitQueueDiscover(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<IQueue> listQueues(IRabbitMessageDevice rabbitMessageDevice) {
        return null;
    }
}
