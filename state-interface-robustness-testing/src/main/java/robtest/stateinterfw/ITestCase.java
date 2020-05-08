package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;

public interface ITestCase extends IEntity {
    ITestInput get(int index);
    int size();
    ITestCase pureClone();
    String getUniqueIdentifier();
}
