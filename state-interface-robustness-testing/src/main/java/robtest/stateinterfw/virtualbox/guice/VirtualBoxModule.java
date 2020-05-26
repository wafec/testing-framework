package robtest.stateinterfw.virtualbox.guice;

import com.google.inject.AbstractModule;
import robtest.stateinterfw.virtualbox.IVirtualBoxEnvironmentManager;
import robtest.stateinterfw.virtualbox.IVirtualBoxManageClient;
import robtest.stateinterfw.virtualbox.VirtualBoxEnvironmentManager;
import robtest.stateinterfw.virtualbox.VirtualBoxManageClient;
import robtest.stateinterfw.virtualbox.commandline.IVirtualBoxCommandLine;
import robtest.stateinterfw.virtualbox.commandline.VirtualBoxCommandLine;

public class VirtualBoxModule extends AbstractModule {
    @Override
    public void configure() {
        bind(IVirtualBoxEnvironmentManager.class).to(VirtualBoxEnvironmentManager.class);
        bind(IVirtualBoxManageClient.class).to(VirtualBoxManageClient.class);
        bind(IVirtualBoxCommandLine.class).to(VirtualBoxCommandLine.class);
    }
}
