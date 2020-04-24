package robtest.stateinterfw.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.virtualbox.IVirtualBoxManageClient;
import robtest.stateinterfw.virtualbox.VirtualBoxManageClient;

public class VirtualBoxModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IVirtualBoxManageClient.class).to(VirtualBoxManageClient.class);
    }
}
