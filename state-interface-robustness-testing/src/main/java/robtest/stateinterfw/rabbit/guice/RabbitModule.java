package robtest.stateinterfw.rabbit.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.rabbit.IRabbitMessageManager;
import robtest.stateinterfw.rabbit.RabbitMessageManager;

public class RabbitModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IRabbitMessageManager.class).to(RabbitMessageManager.class);
    }
}
