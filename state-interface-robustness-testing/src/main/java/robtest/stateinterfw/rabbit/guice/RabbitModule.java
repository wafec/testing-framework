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
        bind(IRabbitMessageDeviceService.class).to(RabbitMessageDeviceService.class);
        bind(IRabbitQueueDiscover.class).to(RabbitQueueDiscover.class);
    }
}
