package robtest.stateinterfw;

public interface ITestSuite {
    ITestCase get(int index);
    int size();
}
