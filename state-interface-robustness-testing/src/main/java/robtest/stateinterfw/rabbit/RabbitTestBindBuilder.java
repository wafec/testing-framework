package robtest.stateinterfw.rabbit;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.ITransactionRepository;
import robtest.stateinterfw.data.Param;

public class RabbitTestBindBuilder implements IRabbitTestBindBuilder {
    private final IRepository repository;
    private final ITransactionRepository transactionRepository;

    @Inject
    public RabbitTestBindBuilder(IRepository repository, ITransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public IRabbitTestBind create(IRabbitBind bind) {
        System.out.println(String.format("Source: %d, Destination: %d", bind.getId(), bind.getId()));
        RabbitTestBind sourceOrDestinationBind = repository.querySingleResult(
                "from RabbitTestBind where (sourceBind.id = :sourceid or destinationBind.id = :destinationid)",
                RabbitTestBind.class, Param.list("sourceid", bind.getId())
                    .add("destinationid", bind.getId()).all()
        );
        if (sourceOrDestinationBind != null) {
            return null;
        }
        RabbitTestBind oldTestBind = repository.querySingleResult("from RabbitTestBind where oldBind.id = :id", RabbitTestBind.class,
                Param.list("id", bind.getId()).all());
        if (oldTestBind != null) {
            return oldTestBind;
        }
        try (transactionRepository) {
            RabbitTestBind testBind = new RabbitTestBind();
            testBind.setOldBind((RabbitBind) bind);
            RabbitBind sourceBind = new RabbitBind();
            String queueInName = String.format("testin_q_%s", bind.getDestination().getName());
            RabbitQueue queueIn = transactionRepository.querySingleResult("from RabbitQueue where name = :name and virtualHost = :virtualHost", RabbitQueue.class,
                    Param.list("name", queueInName)
                            .add("virtualHost", bind.getVirtualHost()).all());
            if (queueIn == null) {
                queueIn = new RabbitQueue(queueInName, bind.getVirtualHost(), (RabbitMessageDevice) bind.getMessageDevice());
                transactionRepository.save(queueIn);
            }
            String exchangeInName = String.format("testin_e_%s", bind.getSource().getName());
            RabbitExchange exchangeIn = transactionRepository.querySingleResult("from RabbitExchange where name = :name and virtualHost = :virtualHost",
                    RabbitExchange.class, Param.list("name", exchangeInName)
                            .add("virtualHost", bind.getVirtualHost()).all());
            if (exchangeIn == null) {
                exchangeIn = new RabbitExchange(exchangeInName, "direct", bind.getVirtualHost(), (RabbitMessageDevice) bind.getMessageDevice());
                transactionRepository.save(exchangeIn);
            }
            RabbitExchange exchange = (RabbitExchange) bind.getSource();
            sourceBind.setSource(exchange);
            sourceBind.setDestination(queueIn);
            sourceBind.setRoutingKey(bind.getRoutingKey());
            sourceBind.setVirtualHost(bind.getVirtualHost());
            sourceBind.setMessageDevice((RabbitMessageDevice) bind.getMessageDevice());
            transactionRepository.save(sourceBind);
            testBind.setSourceBind(sourceBind);
            String routingKeyIn = String.format("testin_%s", bind.getRoutingKey());
            RabbitBind destinationBind = new RabbitBind();
            destinationBind.setVirtualHost(bind.getVirtualHost());
            destinationBind.setSource((RabbitExchange) exchangeIn);
            destinationBind.setRoutingKey(routingKeyIn);
            destinationBind.setDestination((RabbitQueue) bind.getDestination());
            destinationBind.setMessageDevice((RabbitMessageDevice) bind.getMessageDevice());
            testBind.setDestinationBind(destinationBind);
            testBind.setMessageDevice((RabbitMessageDevice) bind.getMessageDevice());
            transactionRepository.save(destinationBind);
            transactionRepository.save(testBind);
            System.out.println("Saved");
            return testBind;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
