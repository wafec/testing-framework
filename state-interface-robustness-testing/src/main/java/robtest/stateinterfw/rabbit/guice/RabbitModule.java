package robtest.stateinterfw.rabbit.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.rabbit.*;
import robtest.stateinterfw.rabbit.commandline.IRabbitCommandLine;
import robtest.stateinterfw.rabbit.commandline.RabbitCommandLine;

public class RabbitModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IRabbitMessageManager.class).to(RabbitMessageManager.class);
        bind(IRabbitCommandLine.class).to(RabbitCommandLine.class);
        bind(IRabbitQueueDiscover.class).to(RabbitQueueDiscover.class);
        bind(IRabbitManagementFactory.class).to(RabbitManagementFactory.class);
        bind(IRabbitTestBindBuilder.class).to(RabbitTestBindBuilder.class);
    }
}
