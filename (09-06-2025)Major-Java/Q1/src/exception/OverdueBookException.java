package exception;

public class OverdueBookException extends RuntimeException {
    public OverdueBookException(String message) {
        super(message);
    }
}
