package robtest.stateinterfw;

public interface ITestDriver {
    void initialize(ITestExecutionContext testExecutionContext);
    void executeNext();
    boolean hasNext();
    void close();
}
