package robtest.stateinterfw.virtualbox;

public interface IVirtualBoxManageClient {
    void powerOff(IVirtualBoxEnvironment virtualBoxEnvironment);
    void snapshot(IVirtualBoxEnvironment virtualBoxEnvironment);
    void powerOn(IVirtualBoxEnvironment virtualBoxEnvironment);
}
