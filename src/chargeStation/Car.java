package chargeStation;

public abstract class Car
{
	private String carNumber;
	private float tankCapacity;
	private float waitDuration;
	private LocationAPI api;
	private ChargingStation chargingStation;
	
	public Car(String carNumber, float tankCapacity, float waitDuration, LocationAPI api)
	{
		this.carNumber = carNumber;
		this.tankCapacity = tankCapacity;
		this.waitDuration = waitDuration;
		this.api = api;
	}
	
	public float getChargingTime()
	{
		return tankCapacity / chargingStation.getChargePerSecond();
	}

	public abstract String getType();
	
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

	public ChargingStation getChargingStation()
	{
		return chargingStation;
	}

	public void setChargingStation(ChargingStation chargingStation)
	{
		this.chargingStation = chargingStation;
	}
}
