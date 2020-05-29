package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.Param;

public class RabbitTestBindBuilder implements IRabbitTestBindBuilder {
    private IRepository repository;

    @Inject
    public RabbitTestBindBuilder(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public IRabbitTestBind create(IRabbitBind bind) {
        RabbitTestBind testBind = new RabbitTestBind();
        testBind.setOldBind((RabbitBind)bind);
        RabbitBind sourceBind = new RabbitBind();
        String queueInName = String.format("testin_%s", bind.getDestination());
        RabbitQueue queueIn = repository.querySingleResult("from RabbitQueue where name = :name", RabbitQueue.class,
                Param.list("name", queueInName).all());
        if (queueIn == null)
            queueIn = new RabbitQueue(queueInName, bind.getVirtualHost(), (RabbitMessageDevice) bind.getMessageDevice());
        RabbitExchange exchange = (RabbitExchange) bind.getSource();
        sourceBind.setSource(exchange);
        sourceBind.setDestination(queueIn);
        sourceBind.setRoutingKey(bind.getRoutingKey());
        sourceBind.setVirtualHost(bind.getVirtualHost());
        testBind.setSourceBind(sourceBind);
        String routingKeyIn = String.format("testin_%s", bind.getRoutingKey());
        RabbitBind destinationBind = new RabbitBind();
        destinationBind.setVirtualHost(bind.getVirtualHost());
        destinationBind.setSource((RabbitExchange) bind.getSource());
        destinationBind.setRoutingKey(routingKeyIn);
        destinationBind.setDestination((RabbitQueue) bind.getDestination());
        testBind.setDestinationBind(destinationBind);
        repository.save(sourceBind);
        repository.save(destinationBind);
        repository.save(testBind);
        return testBind;
    }
}
