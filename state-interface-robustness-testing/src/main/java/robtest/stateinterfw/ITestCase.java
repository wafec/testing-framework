package robtest.stateinterfw;

public interface ITestCase {
    int getId();
    ITestInput get(int index);
    int size();
    ITestCase pureClone();
}
