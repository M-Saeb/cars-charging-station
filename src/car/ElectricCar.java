package car;

import annotations.Readonly;
import api.GPSValues;
import api.LocationAPI;
import stations.ChargingStation;

public class ElectricCar extends Car {

	public ElectricCar(String carNumber, float currentCapacity, float tankCapacity, float waitDuration, LocationAPI api,
			GPSValues currentGPS, boolean priorityFlag) {
		super(carNumber, currentCapacity, tankCapacity, waitDuration, api, currentGPS, priorityFlag);
	}

	@Override
	@Readonly
	public boolean isStationWaitingTimeWithinRange(ChargingStation station) {
		float waitingDuration = station.getExpectedWaitingTimeForElectricCars();
		return waitingDuration < this.getMaximumWaitingDuration();
	}

	@Override
	@Readonly
	public float getChargingTime(ChargingStation station) {
		return getMissingAmountOfFuel() / station.getElectricityOutputPerSecond();
	}
}
