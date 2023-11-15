package chargeStation;

import java.time.LocalDateTime;
import java.lang.IllegalArgumentException;

public class ChargingSlot {
	int id;
	ChargingStation chargingStation;
	Car currentCar = null;
	LocalDateTime nextFreeTime;
	
	public ChargingSlot(CharingStation charingStation, int id) {
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
	}; 
}
