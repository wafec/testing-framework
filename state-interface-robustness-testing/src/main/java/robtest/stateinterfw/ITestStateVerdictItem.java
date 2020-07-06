package robtest.stateinterfw;

public interface ITestStateVerdictItem {
    ITestState getTestState();
    long getElapsedTime();
    boolean hasError();
    String getErrorMessage();
    String getErrorType();
}
