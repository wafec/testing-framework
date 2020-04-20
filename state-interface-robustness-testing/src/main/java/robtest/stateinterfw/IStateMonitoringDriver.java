package robtest.stateinterfw;

public interface IStateMonitoringDriver {
    void notify(IState state);
    void monitor(ITestExecutionContext testExecutionContext);
    void close();
}
