package robtest.stateinterfw;

public interface IEnvironmentManager {
    void initialize(ITestExecutionContext testExecutionContext);
    void clear();
}
