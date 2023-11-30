package car;
import api.GPSValues;
import api.LocationAPI;
import exceptions.ChargingStationNotFoundException;
import stations.ChargingStation;

public class GasCar extends Car
{

	public GasCar(String carNumber, float currentCapacity, float tankCapacity, float waitDuration, LocationAPI api, GPSValues currentGPS, CarState currState)
	{
		super(carNumber, currentCapacity, tankCapacity, waitDuration, api, currentGPS, currState);
		// TODO Auto-generated constructor stub
	}
}
