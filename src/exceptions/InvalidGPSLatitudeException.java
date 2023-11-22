package exceptions;

public class InvalidGPSLatitudeException extends Exception {
    public InvalidGPSLatitudeException () {

    }

    public InvalidGPSLatitudeException (String message) {
        super (message);
    }

    public InvalidGPSLatitudeException (Throwable cause) {
        super (cause);
    }

    public InvalidGPSLatitudeException (String message, Throwable cause) {
        super (message, cause);
    }
}