package stations;
import java.util.ArrayList;
import java.util.logging.Logger;

import annotations.APIMethod;
import annotations.Mutable;
import annotations.Readonly;
import api.GPSValues;
import api.LocationAPI;
import car.Car;
import car.ElectricCar;
import car.GasCar;
import exceptions.ChargingSlotFullException;
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

	/*Amount of available slot per charging station*/
    private int numberOfAvailableSlots;
    
    private int chargingStationID;
    private float gasOutputPerSecond;
    private float electricityOutputPerSecond;
    private Logger logger;
    private GasChargingSlot[] gasSlots;
    private ElectricChargingSlot[] electricSlots;
    private float LevelOfElectricityStorage;
	private float LevelOfGasStorage;
    private ArrayList<Car> queue = new ArrayList<Car>();
    private float waitTime = 0;

	@APIMethod
    public ChargingStation(int chargingStationID, GPSValues gpsValues, int numGasSlots, int numElectricSlots, float gasOutputPerSecondoutputPerSecond, float electricityOutputPerSecond)
	throws InvalidGPSLatitudeException, InvalidGPSLongitudeException, InvalidGPSValueException {
		/* Testing doc line 001 */
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

    	this.gasSlots = new GasChargingSlot[numGasSlots];
    	this.electricSlots = new ElectricChargingSlot[numElectricSlots];
    	
    	this.setNumberOfAvailableSlots(numGasSlots + numElectricSlots);
        if ((numGasSlots == 0) && (numElectricSlots == 0)){
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
    
	@Mutable
	public void addCar(Car car){
        // TODO: add this car to queue

        // TODO: calcualte new wait time
    }

	@Readonly
    public String toString() {
    	return String.format("Charging Station %d", this.chargingStationID);
    }

	@Readonly
    public float getWaitTime() {
        return waitTime;
    }

	@Readonly
    public float getGPSLatitude() {
        return this.gpsValues.getLatitude();
    }

	@Readonly
    public float getGPSLongitude() {
        return this.gpsValues.getLongitude();
    }

	@Readonly
    public int getNumberOfAvailableSlots() {
        return numberOfAvailableSlots;
    }
    
	@Readonly
    public int getAvailableGasSlots() {
    	return gasSlots.length;
    }

	@Readonly
    public int getAvailableElectricSlots() {
        return electricSlots.length;
    }

	@Mutable
    public void setNumberOfAvailableSlots(int availableSlots) {
        this.numberOfAvailableSlots = availableSlots;
    }

	@Mutable
    public void setChargingStationID(int chargingStationID) {
        this.chargingStationID = chargingStationID;
    }

	@Readonly
    public int getChargingStationID() {
        return chargingStationID;
    }

	@Readonly
    public float getGasOutputPerSecond() {
        return gasOutputPerSecond;
    }

	@Readonly
    public float getElectricityOutputPerSecond() {
        return electricityOutputPerSecond;
    }

	@Readonly
    public float getLevelOfElectricityStorage() {
		return LevelOfElectricityStorage;
	}

	@Mutable
	public void setLevelOfElectricityStorage(float levelOfElectricityStorage) {
		LevelOfElectricityStorage = levelOfElectricityStorage;
	}

	@Readonly
	public float getLevelOfGasStorage() {
		return LevelOfGasStorage;
	}

	@Mutable
	public void setLevelOfGasStorage(float levelOfGasStorage) {
		LevelOfGasStorage = levelOfGasStorage;
	}

    /*
    This should get the waiting time for a specific car in qeueue
    It should consider the waiting time + the charging times of the cars
    in front of that car.
     */
	@Readonly
    public double getCarWaitingTime(Car car){ return 0.0; };

    /*
    This should get the total waiting time of the station.
    It should consider the waiting time + the charging times of the cars
    in the queue.
     */
	@Readonly
    public double getTotalWaitingTimeElectric()
    {
    	double totalWaitingTime = 0;
    	for(Car car : queue)
    	{
    		if(car instanceof ElectricCar)
    		{
    			totalWaitingTime += car.getChargingTime(this);
    		}
    	}
    	
    	for(ElectricChargingSlot slot : electricSlots)
    	{
    		if(slot.getCurrentCar() == null)
    		{
    			continue;
    		}
    		
    		totalWaitingTime += slot.getCurrentCar().getChargingTime(this);
    	}
    	
    	return totalWaitingTime;
    }

	@Readonly
    public double getTotalWaitingTimeGas()
    {
    	double totalWaitingTime = 0;
    	for(Car car : queue)
    	{
    		if(car instanceof GasCar)
    		{
    			totalWaitingTime += car.getChargingTime(this);
    		}
    	}
    	
    	for(GasChargingSlot slot : gasSlots)
    	{
    		if(slot.getCurrentCar() == null)
    		{
    			continue;
    		}
    		
    		totalWaitingTime += slot.getCurrentCar().getChargingTime(this);
    	}
    	
    	return totalWaitingTime;
    }

	@Mutable
    public void addCarToQueue(Car car)
    {
    	queue.add(car);
    }

    /*
     * Remove car from station queue.
     */
	@Mutable
    public void leaveStationQueue(Car car)
    {
    	queue.remove(car);
    }

	/*
     * Disonnect car from slot.
     */
	@Mutable
    public void leaveStation(Car car){
		ChargingSlot stationSlots[];
		if (car instanceof ElectricCar){
			stationSlots = this.electricSlots;
		} else { // GasCar
			stationSlots = this.gasSlots;
		}
		for (ChargingSlot slot: stationSlots){
			if (slot.currentCar == car){
				slot.disconnectCar();
			}
			return;
		}
		logger.severe(
			"Something went wrong: you order car numbered  " + car.getCarNumber() + 
			" out of the station, but the car is not in the station"
		);
	}

    /*
     * Send cars in queue to free slots and set their state to charging.
     */
	@Mutable
    public void sendCarsToFreeSlots(){
		/* get the currently free slots */
		ArrayList<ChargingSlot> freeSlots = new ArrayList<ChargingSlot>();
		for (ElectricChargingSlot slot: electricSlots){
			if (slot.currentCar == null){
				freeSlots.add(slot);
			}
		}
		for (GasChargingSlot slot: gasSlots){
			if (slot.currentCar == null){
				freeSlots.add(slot);
			}
		}

		/* updating each free slot */
		for (ChargingSlot slot: freeSlots){

			/* getting the type (class) of the slot */
			Class<?> acceptedCarType;
			if (slot instanceof ElectricChargingSlot){
				acceptedCarType = ElectricCar.class;
			} else { // GasCharingSlot
				acceptedCarType = GasCar.class;
			}

			/* 
			checking which is the first car in the qeueue to have
			the same fuel as the slot 
			*/
			for (Car car: queue){
				if(car.getClass() == acceptedCarType){
					try{
						slot.connectCar(car);
						queue.remove(car);
						break;
					} catch (ChargingSlotFullException e) {
						break;
					}
				}
			}
		}
	}

    /*
     * Add output per second of station to the car's tank.
     * Make sure the car's tank only gets full and not more than that.
     * If a car's tank is already full, do nothing.
     */
	@Mutable
    public void chargeCarsInSlots()
    {
    	for(ElectricChargingSlot chargingSlot : electricSlots)
    	{
    		if(chargingSlot.getCurrentCar() == null)
    		{
    			continue;
    		}
    		
    		if(LevelOfElectricityStorage < electricityOutputPerSecond)
    		{
    			break;
    		}
    		
    		float missingFuel = chargingSlot.getCurrentCar().getMissingAmountOfFuel();
    		if(missingFuel >= electricityOutputPerSecond)
    		{
        		chargingSlot.getCurrentCar().addFuel(electricityOutputPerSecond);
        		LevelOfElectricityStorage -= electricityOutputPerSecond;
    		}
    		else
    		{
    			chargingSlot.getCurrentCar().addFuel(missingFuel);
        		LevelOfElectricityStorage -= missingFuel;
    		}
    	}
    	
    	for(GasChargingSlot chargingSlot : gasSlots)
    	{	
    		if(LevelOfGasStorage < gasOutputPerSecond)
    		{
    			break;
    		}
    		
    		if(chargingSlot.getCurrentCar() == null)
    		{
    			continue;
    		}
    		
    		float missingFuel = chargingSlot.getCurrentCar().getMissingAmountOfFuel();
    		if(missingFuel >= gasOutputPerSecond)
    		{
        		chargingSlot.getCurrentCar().addFuel(gasOutputPerSecond);
        		LevelOfGasStorage -= gasOutputPerSecond;
    		}
    		else
    		{
    			chargingSlot.getCurrentCar().addFuel(missingFuel);
        		LevelOfGasStorage -= missingFuel;
    		}
    	}
    }

	@Readonly
    public float getTotalLeftoverElectricity()
    {
		int pendingUsedElectricity = 0;
    	
		for(int i = 0; i<electricSlots.length; i++)
    	{
			Car currentCar = electricSlots[i].getCurrentCar();
			if (currentCar == null){
				continue;
			}
    		pendingUsedElectricity += currentCar.getMissingAmountOfFuel();
    	}
    	
		for(int i = 0; i<queue.size(); i++)
		{
			if(queue.get(i) instanceof ElectricCar)
			{
				pendingUsedElectricity += queue.get(i).getMissingAmountOfFuel();
			}
		}
    	
		return LevelOfElectricityStorage - pendingUsedElectricity;
    }

	@Readonly
    public float getTotalLeftoverGas()
    {
		int pendingUsedGas = 0;
    	
		for(int i = 0; i<gasSlots.length; i++)
    	{
			Car currentCar = gasSlots[i].getCurrentCar();
			if (currentCar != null){
				pendingUsedGas += gasSlots[i].getCurrentCar().getMissingAmountOfFuel();
			}
    	}
    	
		for(int i = 0; i<queue.size(); i++)
		{
			if(queue.get(i) instanceof GasCar)
			{
				pendingUsedGas += queue.get(i).getMissingAmountOfFuel();
			}
		}
    	
		return LevelOfGasStorage - pendingUsedGas;
    }
}
