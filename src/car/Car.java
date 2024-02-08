package car;

import api.GPSValues;
import api.LocationAPI;
import annotations.Readonly;

import java.util.logging.Logger;
import java.time.Duration;
import java.time.LocalDateTime;

import annotations.APIMethod;
import annotations.Mutable;
import exceptions.ChargingStationNotFoundException;
import exceptions.InvalidGPSValueException;
import stations.ChargingSlot;
import stations.ChargingStation;

public abstract class Car implements Runnable{

	protected String carNumber;
	private float currentCapacity;
	private float tankCapacity;
	private float maximumWaitingDuration; // the maximum accepted waiting duration for the car
	private LocalDateTime enterStationTime; // time when the car entered the queue and is set to wait.
	protected LocationAPI api;
	protected GPSValues currentGPS;
	private ChargingStation chargingStationWaitingQueue;
	private ChargingSlot chargingSlot;
	private CarState currentState;
	private boolean priorityFlag;
	private Logger logger;

	public Car(String carNumber, float currentCapacity, float tankCapacity, float waitDuration, LocationAPI api,
			GPSValues currentGPS, boolean priorityFlag) {
		this.carNumber = carNumber;
		this.logger = Logger.getLogger(this.toString());
		this.currentCapacity = currentCapacity;
		this.tankCapacity = tankCapacity;
		this.maximumWaitingDuration = waitDuration;
		this.api = api;
		this.currentGPS = currentGPS;
		if (this.getMissingAmountOfFuel() > 0.0) {
			this.setCurrentState(CarState.looking);
		} else {
			this.setCurrentState(CarState.charged);
		}
		this.priorityFlag = priorityFlag;
		this.logger = Logger.getLogger(this.toString());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + this.getCarNumber();
	}
	
	public void setEnterStationTime()
	{
		this.enterStationTime = LocalDateTime.now();
	}
	
	public LocalDateTime getEnterStationTime()
	{
		return this.enterStationTime;
	}

	@Readonly
	public ChargingSlot getChargingSlot(){
		return this.chargingSlot;
	}

	@Readonly
	public float getCurrentCapacity() {
		return this.currentCapacity;
	}

	@Readonly
	public ChargingStation getChargingStationWaitingQueue(){
		return this.chargingStationWaitingQueue;
	}

	@Mutable
	public void setChargingStationWaitingQueue(ChargingStation station) {
		station.addCarToWaitingQueue(this);
		this.chargingStationWaitingQueue = station;
	}

	@Mutable
	public void leaveChargingStationWaitingQueue() {
		this.chargingStationWaitingQueue.leaveStationWaitingQueue(this);
		this.chargingStationWaitingQueue = null;
	}

	@Mutable
	public void setCharginSlot(ChargingSlot slot){
		this.chargingSlot = slot;
		try{
			slot.setCarToSlot(this);
		} catch (Exception e){
			this.logger.severe("Please handle the error 111");
		}
	}

	@Mutable
	public void disconnectFromSlot(){
		ChargingSlot slot = this.getChargingSlot(); 
		slot.disconnectCar();
		this.chargingSlot = null;
	}

	@Readonly
	abstract public float getChargingTime(ChargingStation station);

	@Readonly
	public String getCarNumber() {
		return carNumber;
	}

	@Readonly
	public float getTankCapacity() {
		return tankCapacity;
	}

	@Readonly
	public float getMaximumWaitingDuration() {
		return maximumWaitingDuration;
	}

	@Mutable
	public void setMaximumWaitingDuration(float waitDuration) {
		this.maximumWaitingDuration = waitDuration;
	}

	@Readonly
	public LocationAPI getApi() {
		return api;
	}

	@Mutable
	public void setApi(LocationAPI api) {
		this.api = api;
	}

	@Mutable
	public void setCurrentState(CarState newState) {
		this.logger.info("Updating state to " + newState);
		this.currentState = newState;
	}
	
	public CarState getCurrentState() {
		return this.currentState;
	}

	public boolean isPriority() {
		return priorityFlag;
	}

	public void setPriorityFlag(boolean priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	/**
	 * This method should return the nearest charging station based on the following
	 * criteria and order: - Location of the station (nearest is better) - Waiting
	 * time (station's waiting time should be lower than car's waiting time) -
	 * Station's capacity (station should have enough fuel left for this car)
	 */
	@Readonly
	@APIMethod
	public ChargingStation getNearestFreeChargingStation() throws ChargingStationNotFoundException {
		// Getting the nearest station from the LocationAPI
		this.logger.finer("Finding nearest charging station...");
		ChargingStation[] nearestStations;
		try {
			nearestStations = LocationAPI.calculateNearestStation(currentGPS, api.getChargingStation(), this);
		} catch (InvalidGPSValueException e) {
			throw new ChargingStationNotFoundException(
					"Car: " + carNumber + "; LocationAPI returned no close stations.");
		}

		// Checking if it returned any stations. Throwing exception when not
		if (nearestStations.length == 0) {
			throw new ChargingStationNotFoundException(
					"Car: " + carNumber + "; LocationAPI returned no close stations.");
		}

		// Iterating over the found stations and checking for empty slots and if the
		// type is matching
		for (int i = 0; i < nearestStations.length; i++) {
			ChargingStation currentStation = nearestStations[i]; 
			// Check to see if station has enough fuel
			if (
				(this instanceof ElectricCar && currentStation.getLevelOfElectricityStorage() < this.getMissingAmountOfFuel())
				||
				(this instanceof GasCar && currentStation.getLevelOfGasStorage() < this.getMissingAmountOfFuel())
			) {
				this.logger.finest(String.format("The station %s doesn't have enough fuel", currentStation.toString()));
				continue;
			}

			// Check to see if waiting time is acceptable
			if (!this.isStationWaitingTimeWithinRange(currentStation)) {
				this.logger.finest(
					String.format("The station %s has a waiting time longer than 15 minutes", currentStation.toString())
				);
				continue;
			}
			this.logger.finest(nearestStations[i].toString() + " is a match.");
			return nearestStations[i];
		}

		throw new ChargingStationNotFoundException("Car: " + carNumber + " could not find a free station.");
	}

	@Mutable
	public void addFuel(float amount){
		this.currentCapacity += amount;
		if (this.currentCapacity > this.tankCapacity){
			this.currentCapacity = this.tankCapacity;
		}
		this.logger.finer(
			String.format(
				"Received: %f fuel. Current Capacity: %f - Tank capacity: %f",
				amount, this.currentCapacity, this.tankCapacity
			)
		);
	}

	public abstract boolean isStationWaitingTimeWithinRange(ChargingStation station);

	/**
	 * Returns the amount of fuel that is missing until the tank is full
	 */
	@Readonly
	public float getMissingAmountOfFuel() {
		return tankCapacity - currentCapacity;
	}

	@Override
	public void run() {
		while (true) {
			try{
				Thread.sleep(1000);
				logger.info("Current state: " + this.currentState);
				switch (this.currentState.toString()) {
					case "looking":
						try {
							ChargingStation suitableStation = this.getNearestFreeChargingStation();
							if (suitableStation == null){
								this.logger.info("No suitable station available. Leaving map...");
								this.setCurrentState(CarState.leaving);
							} else {
								this.setChargingStationWaitingQueue(suitableStation);
							}
							// logger.fine("Joined " + suitableStation.toString() + " Queue");
						} catch (ChargingStationNotFoundException e) {
							logger.severe("No charging station found.");
						}						
						break;
						
					case "inQueue":
						Duration timeDifference = Duration.between(this.getEnterStationTime(), LocalDateTime.now());
						if(timeDifference.toSeconds() > 15)
						{
							this.logger.info("Waited longer than 15 minutes, leave the station");
							this.leaveChargingStationWaitingQueue();
							this.setCurrentState(CarState.leaving);
						} 
						break;

					case "charging":
						float remaining = this.getMissingAmountOfFuel();
						if (remaining <= 0){
							this.setCurrentState(CarState.charged);
							this.disconnectFromSlot();
						} else if (
							(this instanceof ElectricCar && chargingStationWaitingQueue.getLevelOfElectricityStorage() == 0)
							||
							(this instanceof GasCar && chargingStationWaitingQueue.getLevelOfGasStorage() == 0)
						){
							this.setCurrentState(CarState.looking);
							this.disconnectFromSlot();
						}
						break;
						
					case "charged":
						this.logger.info("Car is fully charged. leaving the map");
						return;
						
					case "leaving":
						this.logger.info("Car couldn't find a suitable charging station and is leaving the map");
						return;
				
					default:
						this.logger.severe("This line shouldn't have been printed");
						break;
				}

			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
