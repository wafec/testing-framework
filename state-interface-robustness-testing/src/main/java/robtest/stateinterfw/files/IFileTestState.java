package robtest.stateinterfw.files;

public interface IFileTestState extends IFileObject {
    int getOrder();
    String getName();
    int getTimeout();
    boolean isRequired();
}
