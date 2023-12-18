package exceptions;

public class IllegalFuelAmountException extends RuntimeException {
    public IllegalFuelAmountException () {

    }

    public IllegalFuelAmountException (String message) {
        super (message);
    }

    public IllegalFuelAmountException (Throwable cause) {
        super (cause);
    }

    public IllegalFuelAmountException (String message, Throwable cause) {
        super (message, cause);
    }
}