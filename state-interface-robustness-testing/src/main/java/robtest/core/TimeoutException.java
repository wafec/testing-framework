package robtest.core;

public class TimeoutException extends RuntimeException {
    public TimeoutException() {

    }

    public TimeoutException(String message) {
        super(message);
    }
}
