package Stations;

public class ChargingStation
{
    /*
    GPS location from every charging station.
    This value can be sent to the other types of station from this super class
     */
    private float GPSLatitude;
    private float GPSLongitude;
    /* TODO: Implement queue logic, where variable availableSlots_int is the number of slot available to push into the queue */
    /*
    Amount of available slot per charging station
     */
    private int availableSlots;
    private int chargingStationID;
    private float outputPerSecond;

    public float getGPSLatitud_f() {
        return GPSLatitude;
    }

    public void setGPSLatitud_f(float GPSLatitud_f) {
        this.GPSLatitude = GPSLatitud_f;
    }

    public float getGPSLongitud_f() {
        return GPSLongitude;
    }

    public void setGPSLongitud_f(float GPSLongitud_f) {
        this.GPSLongitude = GPSLongitud_f;
    }

    public int getAvailableSlots_int() {
        return availableSlots;
    }

    public void setAvailableSlots_int(int availableSlots_int) {
        this.availableSlots = availableSlots_int;
    }

    public void setChargingStationID_int(int chargingStationID_int) {
        this.chargingStationID = chargingStationID_int;
    }

    public int getChargingStationID_int() {
        return chargingStationID;
    }

	public float getOutputPerSecond()
	{
		return outputPerSecond;
	}

	public void setOutputPerSecond(float outputPerSecond)
	{
		this.outputPerSecond = outputPerSecond;
	}
}
