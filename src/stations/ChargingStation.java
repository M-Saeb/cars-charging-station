package stations;
import java.util.logging.Logger;

import api.GPSValues;
import api.LocationAPI;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;
import exceptions.InvalidGPSValueException;

public class ChargingStation
{
    /*
    GPS location from every charging station.
    This value can be sent to the other types of station from this super class
     */
    private GPSValues gpsValues;
    /* TODO: Implement queue logic, where variable availableSlots_int is the number of slot available to push into the queue */
    /*
    Amount of available slot per charging station
     */
    private int availableSlots;
    
    private int chargingStationID;
    private float outputPerSecond;
    private Logger logger;
    private ChargingSlot[] slots;
    
    public ChargingStation(int chargingStationID, GPSValues gpsValues, int availableSlots, float outputPerSecond)
        throws InvalidGPSLatitudeException, InvalidGPSLongitudeException, InvalidGPSValueException {
    	this.logger = Logger.getLogger(this.toString());
    	this.chargingStationID = chargingStationID;
    	try {
    		LocationAPI.checkGPSValues(gpsValues);
    	} catch (InvalidGPSLatitudeException | InvalidGPSLongitudeException e) {
    		this.logger.severe(e.getStackTrace().toString());
    		throw e;
    	} catch (Exception e) {
    		this.logger.severe(e.getStackTrace().toString());
    		throw e;
    	}
    	this.gpsValues = gpsValues;

    	this.availableSlots = availableSlots;
        ChargingSlot[] mySlots = new ChargingSlot[availableSlots];
    	for (int i=0;i < availableSlots; i++) {
    		mySlots[i] = new ChargingSlot(this, i);
    	}
        this.slots = mySlots;

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
        return this.gpsValues.getLatitude();
    }

    public float getGPSLongitude() {
        return this.gpsValues.getLongitude();
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
