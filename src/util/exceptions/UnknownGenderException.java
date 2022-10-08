package util.exceptions;

/**
 * This exception is thrown if there is no {@link util.Gender} with the fitting identifier
 */
public class UnknownGenderException extends Exception {
    public UnknownGenderException() {
        super("Unbekanntes Geschlecht!");
    }

    public UnknownGenderException(String message) {
        super(message);
    }

    public UnknownGenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownGenderException(Throwable cause) {
        super(cause);
    }
}
