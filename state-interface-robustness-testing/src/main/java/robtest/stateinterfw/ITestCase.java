package robtest.stateinterfw;

import robtest.stateinterfw.data.IEntity;

public interface ITestCase extends IEntity, Iterable<ITestInput> {
    ITestInput get(int index);
    int size();
    ITestCase pureClone();
    String getUniqueIdentifier();
}
