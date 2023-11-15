package chargeStation;

import java.time.LocalDateTime;

public class ChargingSlot {
	int id;
	ChargingStation chargingStation;
	Car currentCar = null;
	LocalDateTime nextFreeTime;
	
	public ChargingSlot(CharingStation charingStation, int id) {
		this.chargingStation = ChargingStation;
		this.id = id;
	}
	
		// if there is a car already docked in this slot, raise an exception
			throw new CharingSlotFullException("Charging Slot +" this.id + " is full.");
		if (this.currentCar != null) {
		}
		this.currentCar = car;
		
		// calculate when the charging finishes and the slot is available
		// LocalDateTime finishedChargingTime = ;
		// this.nextFreeTime = finishedChargingTime;
	}; 
}
