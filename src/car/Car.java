package car;

import api.GPSValues;
import api.LocationAPI;
import stations.ChargingStation;
import exceptions.ChargingStationNotFoundException;

public abstract class Car
{
	private String carNumber;
	private float tankCapacity;
	private float waitDuration;
	private LocationAPI api;
    private GPSValues currentGPS;
	
	public Car(String carNumber, float tankCapacity, float waitDuration, LocationAPI api, GPSValues currentGPS)
	{
		this.carNumber = carNumber;
		this.tankCapacity = tankCapacity;
		this.waitDuration = waitDuration;
		this.api = api;
		this.currentGPS = currentGPS;
	}
	
	public float getChargingTime(ChargingStation station)
	{
		return tankCapacity / station.getOutputPerSecond();
	}

	
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

	public int getNearestFreeChargingStation() throws ChargingStationNotFoundException
	{
		//Setting the current position
		// api.setCarCurrentGPS(currentGPS);
		
		//Getting the nearest station
		//int result = api.sortNearestStation(currentGPS[0], currentGPS[1], );
		int result = 1;
		
		//Checking the result
		if(result == 0)
		{
			throw new ChargingStationNotFoundException("Car: " + carNumber + " could not find a free station.");
		}
		return result;
	}
}
