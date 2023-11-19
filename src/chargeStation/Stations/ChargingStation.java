package Stations;

public class ChargingStation
{
    /*
    GPS location from every charging station.
    This value can be sent to the other types of station from this super class
     */
    private float GPSLatitud_f;
    private float GPSLongitud_f;
    /* TODO: Implement queue logic, where variable availableSlots_int is the number of slot available to push into the queue */
    /*
    Amount of available slot per charging station
     */
    private int availableSlots_int;
    private int chargingStationID_int;
    private float outputPerSecond;

    public float getGPSLatitud_f() {
        return GPSLatitud_f;
    }

    public void setGPSLatitud_f(float GPSLatitud_f) {
        this.GPSLatitud_f = GPSLatitud_f;
    }

    public float getGPSLongitud_f() {
        return GPSLongitud_f;
    }

    public void setGPSLongitud_f(float GPSLongitud_f) {
        this.GPSLongitud_f = GPSLongitud_f;
    }

    public int getAvailableSlots_int() {
        return availableSlots_int;
    }

    public void setAvailableSlots_int(int availableSlots_int) {
        this.availableSlots_int = availableSlots_int;
    }

    public void setChargingStationID_int(int chargingStationID_int) {
        this.chargingStationID_int = chargingStationID_int;
    }

    public int getChargingStationID_int() {
        return chargingStationID_int;
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
