package pp.ua.fame.exception;

public class TypeMismatchException extends TesterException{
    public TypeMismatchException() {
        super();
    }

    public TypeMismatchException(String message) {
        super(message);
    }

    public TypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeMismatchException(Throwable cause) {
        super(cause);
    }
}
