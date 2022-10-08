package util.exceptions;

/**
 * This exception is thrown if there is no {@link model.Company} object that fits the identifier
 */
public class NoSuchCompanyException extends Exception {
    public NoSuchCompanyException() {
        super("Diese Firma existiert nicht!");
    }

    public NoSuchCompanyException(String name) {
        super("Konnte keine Firma mit dem Namen '" + name + "' finden!");
    }

    public NoSuchCompanyException(int id) {
        super("Konnte keine Firma mit der id " + id + " finden!");
    }

    public NoSuchCompanyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCompanyException(Throwable cause) {
        super(cause);
    }
}
