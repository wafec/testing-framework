package robtest.stateinterfw.virtualbox;

public interface IVirtualBoxManageClient {
    void powerOff(VirtualBoxEnvironment virtualBoxEnvironment);
    void snapshot(VirtualBoxEnvironment virtualBoxEnvironment);
    void powerOn(VirtualBoxEnvironment virtualBoxEnvironment);
}
