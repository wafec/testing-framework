package robtest.stateinterfw.files;

public interface IFileTestState {
    int getOrder();
    String getName();
    int getTimeout();
    boolean isRequired();
}
