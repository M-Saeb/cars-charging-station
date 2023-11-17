package Stations;

public class ChargingStation
{
    /*
    GPS location from every charging station.
    This value can be sent to the other types of station from this super class
     */
    private float GPSLatitud_f;
    private float GPSLongitud_f;
    /*
    Amount of available slot per charging station
     */
    private int availableSlots_int;

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
}
