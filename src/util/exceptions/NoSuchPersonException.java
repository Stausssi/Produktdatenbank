package util.exceptions;

/**
 * This exception is thrown if there is no {@link model.Person} object that fits the identifier
 */
public class NoSuchPersonException extends Exception {
    public NoSuchPersonException() {
        super("Dieses Produkt existiert nicht");
    }

    public NoSuchPersonException(String name) {
        super("Konnte keine Person mit dem Namen '" + name + "' finden!");
    }

    public NoSuchPersonException(int id) {
        super("Konnte keine Person mit der id " + id + " finden!");
    }

    public NoSuchPersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPersonException(Throwable cause) {
        super(cause);
    }
}
