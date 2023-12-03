package car;

import api.GPSValues;
import api.LocationAPI;
import exceptions.ChargingStationNotFoundException;
import stations.ChargingStation;

public class ElectricCar extends Car {

	public ElectricCar(String carNumber, float currentCapacity, float tankCapacity, float waitDuration, LocationAPI api,
			GPSValues currentGPS) {
		super(carNumber, currentCapacity, tankCapacity, waitDuration, api, currentGPS);
	}

	@Override
	public float getChargingTime(ChargingStation station) {
		return getTankCapacity() / station.getElectricityOutputPerSecond();
	}
}
