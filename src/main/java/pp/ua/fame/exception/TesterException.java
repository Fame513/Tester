package pp.ua.fame.exception;

public class TesterException extends Exception {
    public TesterException() {
        super();
    }

    public TesterException(String message) {
        super(message);
    }

    public TesterException(String message, Throwable cause) {
        super(message, cause);
    }

    public TesterException(Throwable cause) {
        super(cause);
    }
}
