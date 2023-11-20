package chargeStation;

import chargeStation.Stations.ChargingStation;

public abstract class Car
{
	private String carNumber;
	private float tankCapacity;
	private float waitDuration;
	private LocationAPI api;
    private float[][] currentGPS_f;
	
	public Car(String carNumber, float tankCapacity, float waitDuration, LocationAPI api, float[][] currentGPS_f)
	{
		this.carNumber = carNumber;
		this.tankCapacity = tankCapacity;
		this.waitDuration = waitDuration;
		this.api = api;
		this.currentGPS_f = currentGPS_f;
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
		// api.setCarCurrentGPS(currentGPS_f);
		
		//Getting the nearest station
		//int result = api.sortNearestStation(currentGPS_f[0], currentGPS_f[1], );
		int result = 1;
		
		//Checking the result
		if(result == 0)
		{
			throw new ChargingStationNotFoundException("Car: " + carNumber + " could not find a free station.");
		}
		return result;
	}
}
