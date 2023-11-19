package chargeStation;

import java.time.LocalDateTime;
import java.lang.IllegalArgumentException;

public class ChargingSlot {
	private int id;
	private ChargingStation chargingStation;
	private Car currentCar = null;
	private LocalDateTime nextFreeTime;
	
	public ChargingSlot(chargingStation chargingStation, int id) {
		if (chargingStation == null) {
			throw new IllegalArgumentException("Supplied charging station is null.");
		}
		this.chargingStation = ChargingStation;
		this.id = id;
	}
	
	public void chargeCar(Car car) throws  ChargingSlotFullException{
		// if there is a car already docked in this slot, raise an exception
		if (this.currentCar != null) {
			throw new ChargingSlotFullException("Slot +" this.id + " is full.");
		}
		this.currentCar = car;
		
		// calculate when the charging finishes and the slot is available
		// LocalDateTime finishedChargingTime = ;
		// this.nextFreeTime = finishedChargingTime;
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
	}
	
	
}
