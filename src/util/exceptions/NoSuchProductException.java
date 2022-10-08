package util.exceptions;

/**
 * This exception is thrown if there is no {@link model.Product} object that fits the identifier
 */
public class NoSuchProductException extends Exception {
    public NoSuchProductException() {
        super("Dieses Produkt existiert nicht!");
    }

    public NoSuchProductException(String name) {
        super("Konnte kein Produkt mit dem Namen '" + name + "' finden!");
    }

    public NoSuchProductException(int id) {
        super("Konnte kein Produkt mit der id " + id + " finden!");
    }

    public NoSuchProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchProductException(Throwable cause) {
        super(cause);
    }
}
