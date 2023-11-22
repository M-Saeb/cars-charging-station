package exceptions;

public class ChargingSlotFullException extends Exception {
    public ChargingSlotFullException () {

    }

    public ChargingSlotFullException (String message) {
        super (message);
    }

    public ChargingSlotFullException (Throwable cause) {
        super (cause);
    }

    public ChargingSlotFullException (String message, Throwable cause) {
        super (message, cause);
    }
}