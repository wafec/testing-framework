package robtest.stateinterfw.rabbit.commandline;

import com.google.inject.Inject;
import robtest.stateinterfw.data.IRepository;

public class RabbitCommandLine implements IRabbitCommandLine {
    private IRepository repository;

    @Inject
    public RabbitCommandLine(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

    }
}
