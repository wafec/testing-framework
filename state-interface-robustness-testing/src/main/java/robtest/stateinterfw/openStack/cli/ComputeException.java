package robtest.stateinterfw.openStack.cli;

public class ComputeException extends ClientException {
    public ComputeException(int code, String message, String action) {
        super(code, message, action);
    }
}
