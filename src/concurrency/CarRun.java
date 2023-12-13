package concurrency;

import car.Car;
import stations.ChargingStation;

public class CarRun implements Runnable
{
	private ChargingStation chargingStation;
	private Car car;
	
	public CarRun(ChargingStation varChargingStation, Car varCar)
	{
		this.chargingStation = varChargingStation;
		this.car = varCar;
	}
	
	@Override
	public void run() {
		chargingStation.sendCarsToFreeSlots(car);
	}
}
