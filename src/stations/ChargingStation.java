package stations;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import api.GPSValues;
import api.LocationAPI;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;
import exceptions.InvalidGPSValueException;
import car.Car;

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
    private float gasOutputPerSecond;
    private float electricityOutputPerSecond;
    private Logger logger;
    private GasChargingSlot[] gasSlots;
    private ElectricChargingSlot[] electricSlots;
    private Queue<Car> queue = new LinkedList<Car>();
    private float waitTime = 0;
    
    public ChargingStation(int chargingStationID, GPSValues gpsValues, int numGasSlots, int numElectricSlots, float gasOutputPerSecondoutputPerSecond, float electricityOutputPerSecond)
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

    	this.availableSlots = numGasSlots + numElectricSlots;
        if (this.availableSlots == 0){
            throw new IllegalArgumentException("Station can't have 0 slots");
        } else if (numGasSlots < 0) {
            throw new IllegalArgumentException("Station can't have fewer than 0 gas slots.");
        } else if (numElectricSlots < 0){
            throw new IllegalArgumentException("Station can't have fewer than 0 electirc slots.");
        }

        int slotIDs = 0;
        if (numGasSlots > 0){
            GasChargingSlot[] myGasSlots = new GasChargingSlot[numGasSlots];
            for (int i=0;i < numGasSlots; i++) {
                myGasSlots[i] = new GasChargingSlot(this, slotIDs++);
            }
            this.gasSlots = myGasSlots;
        }
        if (numElectricSlots > 0){
            ElectricChargingSlot[] myElectricSlots = new ElectricChargingSlot[numElectricSlots];
            for (int i=0;i < numElectricSlots; i++) {
                myElectricSlots[i] = new ElectricChargingSlot(this, slotIDs++);
            }
            this.electricSlots = myElectricSlots;
        }


    	if (gasOutputPerSecondoutputPerSecond < 0 || electricityOutputPerSecond < 0) {
    		throw new IllegalArgumentException("Charging station output can't be fewer than 0.");
    	}
        if (numGasSlots == 0 && gasOutputPerSecondoutputPerSecond > 0){
            throw new IllegalArgumentException("Station can't have 0 gas slots and still have gas output potential.");
        } else if (numElectricSlots == 0 && electricityOutputPerSecond > 0){
            throw new IllegalArgumentException("Station can't have 0 electricty slots and still have electrity output potential.");
        }
    	this.gasOutputPerSecond = gasOutputPerSecondoutputPerSecond;
        this.electricityOutputPerSecond = electricityOutputPerSecond;
    	
    	
    	this.logger.fine("Initiated " + this.toString());
    }
    
    public void addCar(Car car){
        // add this car to queue

        // calcualte new wait time
    }

    public String toString() {
    	return String.format("Charging Station %d", this.chargingStationID);
    }

    public float getWaitTime() {
        return waitTime;
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

    public float getGasOutputPerSecond() {
        return gasOutputPerSecond;
    }

    public float getElectricityOutputPerSecond() {
        return electricityOutputPerSecond;
    }

    /*
    This should get the waiting time for a specific car in qeueue
    It should consider the waiting time + the charging times of the cars
    in front of that car.
     */
    public double getCarWaitingTime(Car car){ return 0.0; };

    /*
    This should get the total waiting time of the station.
    It should consider the waiting time + the charging times of the cars
    in the queue.
     */
    public double getTotalWaitingTime(){ return 0.0; };


    public void addCarToQueue(){}

    /*
     * Remove car from station queue.
     */
    public void leaveStationQueue(Car car){}

    /*
     * Disonnect car from slot.
     */
    public void leaveStation(Car car){}

    /*
     * Send cars in queue to free slots and set their state to charging.
     */
    public void sendCarsToFreeSlots(){}

    /*
     * Add output per second of station to the car's tank.
     * Make sure the car's tank only gets full and not more than that.
     * If a car's tank is already full, do nothing.
     */
    public void chargeCarsInSlots(){}
}
