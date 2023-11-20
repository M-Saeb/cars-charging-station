package Stations;
import java.util.logging.Logger;

import API.LocationAPI;

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
    private ChargingSlot[] slots;
    private int chargingStationID;
    private float outputPerSecond;
    private Logger logger;
    
    public ChargingStation(int chargingStationID, float GPSLatitude, float GPSLongitude, int availableSlots, float outputPerSecond) {
    	this.logger = Logger.getLogger(this.toString());
    	this.chargingStationID = chargingStationID;
    	try {
    		LocationAPI.checkGPSValues(GPSLatitude, GPSLongitude);
    		this.GPSLatitude = GPSLatitude;
    		this.GPSLongitude = GPSLongitude;
    	} catch (InvalidGPSLatitude | InvalidGPSLongitude e) {
    		this.logger.severe(e.getStackTrace().toString());
    		throw e;
    	} catch (Exception e) {
    		this.logger.severe(e.getStackTrace().toString());
    		throw e;
    	}

    	this.availableSlots = availableSlots;
    	for (int i=0;i < availableSlots; i++) {
    		this.slots[i] = new ChargingSlot(this, i);
    	}

    	if (outputPerSecond < 0) {
    		throw new IllegalArgumentException("Charging station output can't be fewer than 0.");
    	}
    	this.outputPerSecond = outputPerSecond;
    	
    	
    	this.logger.fine("Initiated " + this.toString());
    }
    
    public String toString() {
    	return String.format("Charging Station %d", this.chargingStationID);
    }
    
    public float getGPSLatitude() {
        return GPSLatitude;
    }

    public void setGPSLatitude(float GPSLatitude) {
        this.GPSLatitude = GPSLatitude;
    }

    public float getGPSLongitude() {
        return GPSLongitude;
    }

    public void setGPSLongitude(float GPSLongitude) {
        this.GPSLongitude = GPSLongitude;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public void setChargingStationID(int chargingStationID) {
        this.chargingStationID = chargingStationID;
    }

    public int getChargingStationID() {
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
