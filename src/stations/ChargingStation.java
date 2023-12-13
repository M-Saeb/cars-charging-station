package stations;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import annotations.APIMethod;
import annotations.Mutable;
import annotations.Readonly;
import api.GPSValues;
import api.LocationAPI;
import car.Car;
import car.ElectricCar;

import exceptions.ChargingSlotFullException;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;
import exceptions.InvalidGPSValueException;

import java.util.concurrent.locks.*;

public class ChargingStation extends Thread {	
	private Logger logger;
	private Lock stationLock = new ReentrantLock();
	/* Charging Station Info */
	private int chargingStationID;
	
	private int numberOfAvailableSlots;
	private GPSValues gpsValues;
	private float gasOutputPerSecond;
	private float electricityOutputPerSecond;
	private float LevelOfElectricityStorage;
	private float LevelOfGasStorage;
	
	
	private ArrayList<Car> waitingQueue = new ArrayList<Car>();
	private ChargingSlot electricSlots;
	private ChargingSlot gasSlots;
	private float waitTime = 0;

	@APIMethod
	public ChargingStation(
			int chargingStationID,
			GPSValues gpsValues,
			int numGasSlots,
			int numElectricSlots,
			float gasOutputPerSecond,
			float electricityOutputPerSecond,
			float LevelOfElectricityStorage,
			float LevelOfGasStorage)
			throws InvalidGPSLatitudeException, InvalidGPSLongitudeException, InvalidGPSValueException {
		
		{
			this.chargingStationID = chargingStationID;
			this.logger = Logger.getLogger(this.toString());
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
			
			if ((numGasSlots == 0) && (numElectricSlots == 0)) {
				throw new IllegalArgumentException("Station can't have 0 slots");
			} else if (numGasSlots < 0) {
				throw new IllegalArgumentException("Station can't have fewer than 0 gas slots.");
			} else if (numElectricSlots < 0) {
				throw new IllegalArgumentException("Station can't have fewer than 0 electirc slots.");
			}
		}
		
		{
			if (numGasSlots > 0) {
				this.electricSlots = new ChargingSlot(numElectricSlots);
			}
			if (numElectricSlots > 0) {
				this.gasSlots = new ChargingSlot(numGasSlots);
			}

			if (gasOutputPerSecond < 0 || electricityOutputPerSecond < 0) {
				throw new IllegalArgumentException("Charging station output can't be fewer than 0.");
			}
			if (numGasSlots == 0 && gasOutputPerSecond > 0) {
				throw new IllegalArgumentException("Station can't have 0 gas slots and still have gas output potential.");
			} else if (numElectricSlots == 0 && electricityOutputPerSecond > 0) {
				throw new IllegalArgumentException(
						"Station can't have 0 electricity slots and still have electricity output potential.");
			}
			this.gasOutputPerSecond = gasOutputPerSecond;
			this.electricityOutputPerSecond = electricityOutputPerSecond;

			if (LevelOfElectricityStorage < 0 || LevelOfGasStorage < 0) {
				throw new IllegalArgumentException("Charging station storage can't be fewer than 0.");
			} else if (LevelOfElectricityStorage == 0 && LevelOfGasStorage == 0){
				throw new IllegalArgumentException("Station can't have 0 storage of any kind");
			}
			if (numGasSlots == 0 && LevelOfGasStorage > 0) {
				throw new IllegalArgumentException("Station can't have 0 gas slots and still have gas output potential.");
			} else if (numElectricSlots == 0 && LevelOfElectricityStorage > 0) {
				throw new IllegalArgumentException(
						"Station can't have 0 electricity slots and still have electricity output potential.");
			}
			this.LevelOfElectricityStorage = LevelOfElectricityStorage;
			this.LevelOfGasStorage = LevelOfGasStorage;
		}

		this.logger.fine("Initiated " + this.toString());
	}

	@Readonly
	public String toString() {
		return String.format("Charging Station %d", this.chargingStationID);
	}

	@Readonly
	public float getGPSLatitude() throws InvalidGPSValueException {
		if (this.gpsValues.getLatitude() == 0) {
			try {
				throw new InvalidGPSLatitudeException("Invalid Latitud value...");
			} catch (Exception e) {
				System.out.println("Invalid Latitud value...");
				e.printStackTrace();
			}
		} else {
			/*
			 * Do nothing
			 */
		}
		return this.gpsValues.getLatitude();
	}

	@Readonly
	public float getGPSLongitude() throws InvalidGPSValueException {
		if (this.gpsValues.getLongitude() == 0) {
			try {
				throw new InvalidGPSLongitudeException("Invalid Latitud value...");
			} catch (Exception e) {
				this.logger.severe("Invalid Latitud value...");
				this.logger.severe(e.getStackTrace().toString());
			}
		} else {
			/*
			 * Do nothing
			 */
		}
		return this.gpsValues.getLongitude();
	}


	@Readonly
	public int getAvailableGasSlots() {
		return gasSlots.getTotalSlots();
	}

	@Readonly
	public int getAvailableElectricSlots() {
		return electricSlots.getTotalSlots();
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

	@Mutable
	public void addCarToWaitingQueue(Car car) {
		this.logger.finer(String.format("Adding %s to waitingQueue.", car.toString()));
		// Adding it and returning, if the waitingQueue is empty
		if (waitingQueue.isEmpty()) {
			waitingQueue.add(car);
			this.logger.finer("waitingQueue was empty. Added car.");
			return;
		}

		// If car is prioritized, add it after the last prioritized car
		if (car.isPriority()) {
			this.logger.finer("Car is priority. Adding it to the top of the waitingQueue.");

			for (int i = 0; i < waitingQueue.size(); i++) {
				if (!waitingQueue.get(i).isPriority()) {
					waitingQueue.add(i, car);
					this.logger.finer("Added priority car at position " + i);
					return;
				}
			}
		}

		// Otherwise add normal
		waitingQueue.add(car);
		this.logger.fine(String.format("Added %s to waitingQueue.", car.toString()));
	}

	/**
	 * Remove car from station waitingQueue.
	 */
	@Mutable
	public void leaveStationwaitingQueue(Car car) {
		waitingQueue.remove(car);
		this.logger.fine(String.format("Removed %s from waitingQueue.", car));
	}

	/**
	 * Disonnect car from slot.
	 */
	@Mutable
	public void leaveStation(Car car) {
		this.logger.fine(String.format("%s is done charging. Removing it...", car.toString()));
		if (car instanceof ElectricCar) {
			this.electricSlots.disconnectCar(car);
			this.logger.fine(String.format("Removed %s from slot.", car.toString()));
		} else { // GasCar
			this.gasSlots.disconnectCar(car);
			this.logger.fine(String.format("Removed %s from slot.", car.toString()));
		}
		logger.severe("Something went wrong: you order car numbered  " + car.toString()
				+ " out of the station, but the car is not in the station");
	}

	/**
	 * Send cars in waitingQueue to free slots and set their state to charging.
	 */
	@Mutable
	public void sendCarsToFreeSlots(Car car) 
	{
		this.logger.finer("Sending cars to free slots...");
		
		if(!waitingQueue.isEmpty())
		{
			this.logger.finer("Queue is not empty...");
			if(car instanceof ElectricCar)
			{
				/* Resource is free, then take the semaphore resource and connect car */
				try 
				{
					/* Mutex */
					stationLock.lock();
					if(electricSlots.connectCar(car))
					{
						/* 
						 * Add fuel logic here 
						 */
						this.logger.info(String.format("Car %s was charged...", car.toString()));
						leaveStation(car);
					}
					else 
					{
						this.logger.info(String.format("Car %s, found no free slots...", car.toString()));
						car.setEnterStationTime(System.currentTimeMillis());
						waitingQueue.add(car);
					}
				} catch (ChargingSlotFullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					/* Mutex */
					stationLock.unlock();
				}
			}
			else {
				/* Resource is free, then take the semaphore resource and connect car */
				try 
				{
					if(gasSlots.connectCar(car))
					{
						/* 
						 * Add fuel logic here 
						 */
						this.logger.info(String.format("Car %s was charged...", car.toString()));
						leaveStation(car);
					}
					else 
					{
						this.logger.info(String.format("Car %s, found no free slots...", car.toString()));
						car.setEnterStationTime(System.currentTimeMillis());
						waitingQueue.add(car);
					}
				} catch (ChargingSlotFullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}

		}
		else 
		{
			this.logger.info(String.format("No more cars in queue...", car.toString()));
		}
	}
	
	@Mutable
	public void checkTimeQueue()
	{
		try {
			/* Mutex */
			stationLock.lock();
			for(Car tempCar: waitingQueue)
			{
				if(!waitingQueue.isEmpty())
				{
					tempCar = waitingQueue.remove(0);
					if((15*1000) >= (System.currentTimeMillis() - tempCar.getEnterStationTime()))
					{
						sendCarsToFreeSlots(tempCar);
					}
					else {
						this.logger.info(String.format("Waiting time was too much for car %s...", tempCar.toString()));
						waitingQueue.remove(0);
					}
				}
				else {
					break;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			/* Mutex */
			stationLock.unlock();
		}

	}
}
