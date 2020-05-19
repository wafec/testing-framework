package robtest.stateinterfw;

public interface ITestCaseFault extends ITestCase {
    ITestCase getOriginalTestCase();
}
