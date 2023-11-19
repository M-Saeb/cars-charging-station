package chargeStation;

import java.time.LocalDateTime;
import java.util.logging.Logger;


public class ChargingSlot {
	private int id;
	private ChargingStation chargingStation;
	private Car currentCar = null;
	private LocalDateTime nextFreeTime;
	private float throughput;
	private Logger logger;
	
	public ChargingSlot(chargingStation chargingStation, int id, float throughput) {
		if (chargingStation == null) {
			throw new IllegalArgumentException("Supplied charging station is null.");
		}
		this.chargingStation = ChargingStation;
		this.id = id;
		this.logger = Logger.getLogger(this.getClass().getSimpleName() + " " + this.id);
		this.logger.fine("Initiated Charging Slot " + this.id);
	}
	
	public void chargeCar(Car car) throws  ChargingSlotFullException{
		// if there is a car already docked in this slot, raise an exception
		if (this.currentCar != null) {
			throw new ChargingSlotFullException("Slot " + this.id + " is full.");
		}
		this.currentCar = car;
		
		// calculate when the charging finishes and the slot is available
		this.nextFreeTime = this.calculateNextFreeTime();
		this.logger.info("Plugged in " + this.currentCar.toString());
		this.logger.fine("Slot will be free at " + this.nextFreeTime.toString());
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
	
