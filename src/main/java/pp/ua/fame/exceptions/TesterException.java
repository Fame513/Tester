package pp.ua.fame.exceptions;

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
