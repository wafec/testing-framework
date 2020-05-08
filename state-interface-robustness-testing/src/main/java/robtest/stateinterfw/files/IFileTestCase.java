package robtest.stateinterfw.files;

import robtest.stateinterfw.ITestCase;

public interface IFileTestCase extends IFileObject {
    String getUniqueIdentifier();
    int getId();
    IFileTestInput get(int index);
    int size();
    ITestCase toTestCase();
}
