package robtest.stateinterfw;

public interface ITestExecutionContext {
    int getId();
    ITestCase get();
    ITestInput getCurrent();
    ITestSpecs getSpecs();
    Object getVolatileUserContent();
    void setVolatileUserContent(Object userContent);
}
