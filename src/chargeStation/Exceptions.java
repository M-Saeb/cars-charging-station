package chargeStation;

class ChargingSlotFullException extends Throwable{
	public ChargingSlotFullException(String s) {
		super(s);
	}
}


class NoAvailableSlotException extends Throwable{
	public NoAvailableSlotException(String s) {
		super(s);
	}
}

class ChargingStationNotFoundException extends Throwable{
	public ChargingStationNotFoundException(String s) {
		super(s);
	}
}

class InvalidGPSValue extends Throwable{
	public InvalidGPSValue(String s) {
		super(s);
	}
}

class InvalidGPSLatitude extends Throwable{
	public InvalidGPSLatitude(String s) {
		super(s);
	}
}

class InvalidGPSLongitude extends Throwable{
	public InvalidGPSLongitude(String s) {
		super(s);
	}
}