package car;

import api.GPSValues;
import api.LocationAPI;
import stations.ChargingStation;
import exceptions.ChargingStationNotFoundException;

public abstract class Car
{
	protected String carNumber;
	private float currentCapacity;
	private float tankCapacity;
	private float waitDuration;
	protected LocationAPI api;
    protected GPSValues currentGPS;
	
	public Car(String carNumber, float currentCapacity, float tankCapacity, float waitDuration, LocationAPI api, GPSValues currentGPS)
	{
		this.carNumber = carNumber;
		this.currentCapacity = currentCapacity;
		this.tankCapacity = tankCapacity;
		this.waitDuration = waitDuration;
		this.api = api;
		this.currentGPS = currentGPS;
	}
	
	public float getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(float currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	abstract public float getChargingTime(ChargingStation station);
	
	public String getCarNumber()
	{
		return carNumber;
	}

	public void setCarNumber(String carNumber)
	{
		this.carNumber = carNumber;
	}

	public float getTankCapacity()
	{
		return tankCapacity;
	}

	public void setTankCapacity(float tankCapacity)
	{
		this.tankCapacity = tankCapacity;
	}

	public float getWaitDuration()
	{
		return waitDuration;
	}

	public void setWaitDuration(float waitDuration)
	{
		this.waitDuration = waitDuration;
	}

	public LocationAPI getApi()
	{
		return api;
	}

	public void setApi(LocationAPI api)
	{
		this.api = api;
	}

	/*
	This method should return the nearest charging station based on the following criteria and order:
	- Location of the station (nearest is better)
	- Waiting time (station's waiting time should be lower than car's waiting time)
	- Station's capacity (station should have enough fuel left for this car)
	 */
	public abstract ChargingStation getNearestFreeChargingStation() throws ChargingStationNotFoundException;

	/*
	This method will add the car to the station's queue.
	Also it should set car state to in queue.
	 */
	public abstract void joinStationQueue(ChargingStation station);


	/*
	A car has 4 states (probably an ENUM implementation?):
	- looking
	- in queue
	- charging
	- charged
	The following methods return boolean values corrresponding to its state.
	 */

	abstract public boolean isLooking();
	abstract public boolean isInQueue();
	abstract public boolean isCharging();
	abstract public boolean isCharged();

	/*
	Checks the current station the car is in to make sure the
	waiting time is still feasible. This is required since priority cars
	could jump in line or station could run out of fuel till the car's turn
	is up for whatever reason. 
	 */
	abstract public boolean checkCurrentStation();

	/*
	Return the station the car joined to.
	 */
	abstract public ChargingStation getCurrentStation();

	/*
	 * Leave station since the current station isn't suitable anymore. Set car state to looking.
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
	abstract public void addFuel(double amount);
}
