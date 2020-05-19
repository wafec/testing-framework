package robtest.stateinterfw.virtualbox;

import robtest.stateinterfw.IEnvironment;

public interface IVirtualBoxEnvironment extends IEnvironment {
    String getSnapshot();
    int getPriority();
}
