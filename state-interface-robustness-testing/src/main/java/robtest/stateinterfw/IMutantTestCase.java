package robtest.stateinterfw;

public interface IMutantTestCase extends ITestCase {
    ITestCase getOriginalTestCase();
}
