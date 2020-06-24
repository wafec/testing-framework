package robtest.stateinterfw.openStack.cli;

public class ClientException extends RuntimeException {
    private int code;
    private String message;
    private String action;

    public ClientException(int code) {
        this(code, (String) null);
    }

    public ClientException(int code, String message) {
        this(code, message, (String) null, null);
    }

    public ClientException(int code, String message, String action, Exception innerException) {
        super(message);
        this.code = code;
        this.message = message;
        this.action = action;
    }

    public ClientException(int code, String message, Exception innerException) {
        this(code, message, null, innerException);
    }

    public ClientException(int code, String message, String action) {
        this(code, message, action, null);
    }
}
