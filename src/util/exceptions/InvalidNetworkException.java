package util.exceptions;

/**
 * This exception is thrown if there is no network relation to be found with the provided identifier
 */
public class InvalidNetworkException extends Exception  {
    public InvalidNetworkException() {
        super("Invalid Network");
    }

    public InvalidNetworkException(String network) {
        super("Konnte kein Netzwerk mit der Bezeichnung '" + network + "' finden!");
    }

    public InvalidNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNetworkException(Throwable cause) {
        super(cause);
    }

    protected InvalidNetworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
