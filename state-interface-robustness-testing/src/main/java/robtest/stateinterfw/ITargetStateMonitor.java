package robtest.stateinterfw;

public interface ITargetStateMonitor {
    void notify(IState state);
    void notify(ITestStateOutput testStateOutput);
    void monitor(ITestExecutionContext testExecutionContext);
    void close();
}
