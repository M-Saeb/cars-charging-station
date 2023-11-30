package stations;

import java.time.LocalDateTime;
import car.Car;
import exceptions.ChargingSlotFullException;


public class ChargingSlot {
	private int id;
	private ChargingStation chargingStation;
	private Car currentCar = null;
	private LocalDateTime nextFreeTime;
	
	public ChargingSlot(ChargingStation chargingStation, int id) {
		if (chargingStation == null) {
			throw new IllegalArgumentException("Supplied charging station is null.");
		}
		this.chargingStation = chargingStation;
		this.id = id;
	}
	
	public void chargeCar(Car car) throws ChargingSlotFullException {
		// if there is a car already docked in this slot, raise an exception
		if (this.currentCar != null) {
			throw new ChargingSlotFullException("Slot " + this.id + " is full.");
		}
		this.currentCar = car;
		
		// calculate when the charging finishes and the slot is available
		this.nextFreeTime = this.calculateNextFreeTime();
	}
	
	public int getId(){
		return this.id;
	}
	
	public Car getCurrentCar() {
		return this.currentCar;
	}
	
	public LocalDateTime getNextFreeTime() {
		return this.nextFreeTime;
	}
	
	private LocalDateTime calculateNextFreeTime() {
		long chargingTime =  (long) this.currentCar.getChargingTime(this.chargingStation);
		return LocalDateTime.now().plusSeconds(chargingTime);
	}
	
	public String toString() {
		return String.format("Charging Slot %s", this.id);
	} 
}
	
