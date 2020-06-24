package robtest.stateinterfw.openStack.cli;

public class NetworkingException extends ClientException {
    public NetworkingException(int code, String message, String action) {
        super(code, message, action);
    }
}
