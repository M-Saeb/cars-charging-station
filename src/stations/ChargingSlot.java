package stations;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import annotations.Mutable;
import annotations.Readonly;
import car.Car;
import car.CarState;
import car.ElectricCar;
import exceptions.ChargingSlotFullException;


public class ChargingSlot implements Runnable{
	private String name;
	protected ChargingStation chargingStation;
	protected Car currentCar = null;
	protected Logger logger;
	
	
	public ChargingSlot(String name, ChargingStation station) 
	{
		this.name = name;
		this.chargingStation = station;	
		this.logger = Logger.getLogger(this.toString());
	}
	
	@Mutable
	public void setCarToSlot(Car car) throws ChargingSlotFullException
	{
		if(this.getCurrentCar() == null){
			this.currentCar = car;
			this.currentCar.setCurrentState(CarState.charging);
			this.logger.info("Attached car " + this.currentCar.toString());
		} else{
			throw new ChargingSlotFullException("Charging slot already has a car set to it");
		}
	}

	@Mutable
	public void disconnectCar() {
		this.logger.info("Disconnecting " + this.currentCar.toString());
		this.currentCar = null;
	}

	@Readonly
	public Car getCurrentCar() {
		return this.currentCar;
	}

	@Readonly
	private LocalDateTime calculateNextFreeTime() {
		long chargingTime = (long) this.currentCar.getChargingTime(this.chargingStation);
		return LocalDateTime.now().plusSeconds(chargingTime);
	}

	@Readonly
	public String toString() {
		return this.name;
	} 

	@Override
	public void run(){
		while (true){
			try{
				Thread.sleep(1000);
				Car car = this.getCurrentCar();
				if (car != null ){
					float outputPerSecond;
					if (car instanceof ElectricCar){
						outputPerSecond = this.chargingStation.getElectricityOutputPerSecond();
					} else {
						outputPerSecond = this.chargingStation.getGasOutputPerSecond();
					}
					this.logger.info("Adding " + outputPerSecond + " to car " + car.toString());
					car.addFuel(outputPerSecond);
				}
			} catch (Exception e){
				e.printStackTrace();
			}			
		}
	}
}	
