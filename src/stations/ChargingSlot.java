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
	private int id;
	protected ChargingStation chargingStation;
	protected Car currentCar = null;
	protected Logger logger;
	
	
	public ChargingSlot(int id, ChargingStation station) 
	{
		this.id = id;
		this.chargingStation = station;	
		this.logger = Logger.getLogger(this.toString());
		// Add logs of slots to station logs
		this.logger.addHandler(this.chargingStation.getFileHandler());
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
		return String.format("%s - %s %d", this.chargingStation.toString(), this.getClass().getSimpleName(), this.id);
	} 

	@Override
	public void run(){
		while (true){
			try{
				Thread.sleep(1000);
				Car car = this.getCurrentCar();
				if (car != null ){
					float energyAmount = car.getMissingAmountOfFuel();
					if (car instanceof ElectricCar){
						energyAmount = this.chargingStation.consumeElectricity(energyAmount);
					} else {
						energyAmount = this.chargingStation.consumeGas(energyAmount);
					}
					car.addFuel(energyAmount);
					this.logger.info("Adding " + energyAmount + " to car " + car.toString());
				}
			} catch (Exception e){
				e.printStackTrace();
			}			
		}
	}
}	
