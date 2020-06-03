package robtest.stateinterfw;

public interface ITestExecutionContext {
    int getId();
    ITestCase get();
    ITestInput getCurrent();
    int moveForward();
    int moveBackward();
    ITestSpecs getSpecs();
    Object getVolatileUserContent();
    void setVolatileUserContent(Object userContent);
}
