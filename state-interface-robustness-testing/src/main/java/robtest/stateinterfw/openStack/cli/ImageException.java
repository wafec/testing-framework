package robtest.stateinterfw.openStack.cli;

public class ImageException extends ClientException {
    public ImageException(int code, String message, String action) {
        super(code, message, action);
    }
}
