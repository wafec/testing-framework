package robtest.stateinterfw.virtualbox.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.virtualbox.IVirtualBoxEnvironmentManager;
import robtest.stateinterfw.virtualbox.IVirtualBoxManageClient;
import robtest.stateinterfw.virtualbox.VirtualBoxEnvironmentManager;
import robtest.stateinterfw.virtualbox.VirtualBoxManageClient;

public class VirtualBoxModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IVirtualBoxEnvironmentManager.class).to(VirtualBoxEnvironmentManager.class);
        bind(IVirtualBoxManageClient.class).to(VirtualBoxManageClient.class);
    }
}
