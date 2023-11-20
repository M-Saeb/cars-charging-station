package Stations;

class InvalidGPSValue extends Throwable{
	public InvalidGPSValue(String s) {
		super(s);
	}
}

public class InvalidGPSLatitude extends Throwable{
	public InvalidGPSLatitude(String s) {
		super(s);
	}
}

