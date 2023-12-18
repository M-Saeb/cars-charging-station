package car;
import annotations.Readonly;
import api.GPSValues;
import api.LocationAPI;
import stations.ChargingStation;

public class GasCar extends Car
{

	public GasCar(String carNumber, float currentCapacity, float tankCapacity, float waitDuration, LocationAPI api, GPSValues currentGPS)
	{
		super(carNumber, currentCapacity, tankCapacity, waitDuration, api, currentGPS);
	}

	@Override
	@Readonly
	public boolean isStationWaitingTimeWithinRange(ChargingStation station) {
		float waitingDuration = station.getExpectedWaitingTimeForGasCars();
		return waitingDuration < this.getMaximumWaitingDuration();
	}

	@Override
	@Readonly
	public float getChargingTime(ChargingStation station) {
		return getMissingAmountOfFuel() / station.getGasOutputPerSecond();
	}

}
