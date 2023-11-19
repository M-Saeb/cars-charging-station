package chargeStation;

class ChargingSlotFullException extends Throwable{
	public ChargingSlotFullException(String s) {
		super(s);
	}
}

class ChargingStationNotFoundException extends Throwable{
	public ChargingStationNotFoundException(String s) {
		super(s);
	}
}