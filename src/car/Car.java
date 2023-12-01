package car;

import api.GPSValues;
import api.LocationAPI;
import exceptions.ChargingStationNotFoundException;
import stations.ChargingStation;

public abstract class Car {
	
	protected String carNumber;
	private float currentCapacity;
	private float tankCapacity;
	private float waitDuration;
	protected LocationAPI api;
	protected GPSValues currentGPS;
	private CarState currState;
	
	public Car(String carNumber, float currentCapacity, float tankCapacity, float waitDuration, LocationAPI api,
			GPSValues currentGPS, CarState currState) {
		this.carNumber = carNumber;
		this.currentCapacity = currentCapacity;
		this.tankCapacity = tankCapacity;
		this.waitDuration = waitDuration;
		this.api = api;
		this.currentGPS = currentGPS;
		this.currState = currState;
	}

	public float getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(float currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	abstract public float getChargingTime(ChargingStation station);

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public float getTankCapacity() {
		return tankCapacity;
	}

	public void setTankCapacity(float tankCapacity) {
		this.tankCapacity = tankCapacity;
	}

	public float getWaitDuration() {
		return waitDuration;
	}

	public void setWaitDuration(float waitDuration) {
		this.waitDuration = waitDuration;
	}

	public LocationAPI getApi() {
		return api;
	}

	public void setApi(LocationAPI api) {
		this.api = api;
	}
	
	public void setCurrState(CarState currState) {
		this.currState = currState;
	}
	
	/*
	 * This method should return the nearest charging station based on the following
	 * criteria and order: - Location of the station (nearest is better) - Waiting
	 * time (station's waiting time should be lower than car's waiting time) -
	 * Station's capacity (station should have enough fuel left for this car)
	 */
	public ChargingStation getNearestFreeChargingStation() throws ChargingStationNotFoundException {
		// Getting the nearest station from the LocationAPI
		ChargingStation[] nearestStations = LocationAPI.calculateNearestStation(currentGPS, api.getChargingStation(), this);

		// Checking if it returned any stations. Throwing exception when not
		if (nearestStations.length == 0) {
			throw new ChargingStationNotFoundException(
					"Car: " + carNumber + "; LocationAPI returned no close stations.");
		}

		// Iterating over the found stations and checking for empty slots and if the
		// type is matching
		for (int i = 0; i < nearestStations.length; i++) {
			if (nearestStations[i].getTotalWaitingTime() >= waitDuration) {
				continue;
			}
			
			if(this instanceof ElectricCar && nearestStations[i].getTotalLeftoverElectricity() < getMissingAmountOfFuel())
			{
				continue;
			}
			else if(this instanceof GasCar && nearestStations[i].getTotalLeftoverGas() < getMissingAmountOfFuel())
			{
				continue;
			}
				
			return nearestStations[i];
		}

		throw new ChargingStationNotFoundException("Car: " + carNumber + " could not find a free station.");
	}

	/*
	 * This method will add the car to the station's queue. Also it should set car
	 * state to in queue.
	 */
	public void joinStationQueue(ChargingStation station)
	{
		station.addCarToQueue(this);
		currState = CarState.charging;
	}

	/*
	 * A car has 4 states (ENUM implementation): - looking - in queue -
	 * charging - charged The following methods return boolean values corrresponding
	 * to its state.
	 */
	public boolean isLooking()
	{
		if(currState == CarState.looking)
		{
			return true;
		}
		return false;
	}

	public boolean isInQueue()
	{
		if(currState == CarState.inQueue)
		{
			return true;
		}
		return false;
	}

	public boolean isCharging()
	{
		if(currState == CarState.charging)
		{
			return true;
		}
		return false;
	}

	public boolean isCharged()
	{
		if(currState == CarState.charged)
		{
			return true;
		}
		return false;
	}

	/*
	 * Checks the current station the car is in to make sure the waiting time is
	 * still feasible. This is required since priority cars could jump in line or
	 * station could run out of fuel till the car's turn is up for whatever reason.
	 */
	abstract public boolean checkCurrentStation();

	/*
	 * Return the station the car joined to.
	 */
	abstract public ChargingStation getCurrentStation();

	/*
	 * Leave station since the current station isn't suitable anymore. Set car state
	 * to looking.
	 */
	abstract public void leaveStationQueue();

	/*
	 * Leave station since the car is charged. Set car state to charged.
	 */
	abstract public void leaveStation();

	/*
	 * Car leaves map as no suitable station is available. Set state to charged!
	 * We'll change this later and handle it better. But for now, it should suffice.
	 */
	abstract public void leaveMap();

	/*
	 * Add the amount of fuel to the car's current capacity.
	 */
	public void addFuel(double amount)
	{
		currentCapacity += amount;
	}
	
	/*
	 * Returns the amount of fuel that is missing until the tank is full
	 */
	public float getMissingAmountOfFuel()
	{
		return tankCapacity - currentCapacity;
	}
}
