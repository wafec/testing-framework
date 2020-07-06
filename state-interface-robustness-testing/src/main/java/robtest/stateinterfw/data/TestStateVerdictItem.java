package robtest.stateinterfw.data;

import robtest.stateinterfw.ITestState;
import robtest.stateinterfw.ITestStateVerdictItem;

public class TestStateVerdictItem implements ITestStateVerdictItem {
    public static final String INVALID_STATE = "INVALID";
    public static final String RUNTIME_ERROR = "RUNTIME_ERROR";
    public static final String EXCEPTION_ERROR = "EXCEPTION_ERROR";

    private TestState testState;
    private long elapsedTime;
    private boolean hasError;
    private String errorMessage;
    private String errorType;

    @Override
    public ITestState getTestState() {
        return testState;
    }

    @Override
    public long getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public boolean hasError() {
        return hasError;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getErrorType() {
        return errorType;
    }

    public void setTestState(TestState testState) {
        this.testState = testState;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    protected static TestStateVerdictItem create(ITestState testState, long elapsedTime, boolean hasError,
                                                 String errorMessage, String errorType) {
        TestStateVerdictItem item = new TestStateVerdictItem();
        item.setTestState((TestState) testState);
        item.setHasError(hasError);
        item.setElapsedTime(elapsedTime);
        item.setErrorMessage(errorMessage);
        item.setErrorType(errorType);
        return item;
    }

    public static TestStateVerdictItem createPassOk(ITestState testState) {
        return createPassOk(testState, -1);
    }

    public static TestStateVerdictItem createPassOk(ITestState testState, long elapsedTime) {
        return create(testState, elapsedTime, false, null, null);
    }

    public static TestStateVerdictItem createInvalidStateError(ITestState testState, long elapsedTime, String errorMessage) {
        return create(testState, elapsedTime, true, errorMessage, INVALID_STATE);
    }

    public static TestStateVerdictItem createRuntimeError(ITestState testState, String errorMessage) {
        return create(testState, -1, true, errorMessage, RUNTIME_ERROR);
    }

    public static TestStateVerdictItem createExceptionError(ITestState testState, Exception exception) {
        return create(testState, -1, true, exception.getMessage(), EXCEPTION_ERROR);
    }
}
