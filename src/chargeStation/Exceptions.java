package chargeStation;

class ChargingSlotFullException extends Throwable{
	public ChargingSlotFullException(String s) {
		super(s);
	}
}