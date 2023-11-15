package chargeStation;

import java.time.LocalDateTime;

public class ChargingSlot {
	int id;
	ChargingStation chargingStation;
	Car currentCar = NULL;
	LocalDateTime nextFreeTime;
	
	public ChargingSlot(CharingStation charingStation, int id) {
		this.chargingStation = ChargingStation;
		this.id = id;
	}
	
	public void chargeCar(Car car) {
		// if there is a car already docked in this slot, raise an exception
		if (this.currentCar != NULL) {
			throw new CharingSlotFullException("Charging Slot +" this.id + " is full.");
		}
		this.currentCar = car;
		
		// calculate when the charging finishes and the slot is available
		// LocalDateTime finishedChargingTime = ;
		// this.nextFreeTime = finishedChargingTime;
	}; 
}
