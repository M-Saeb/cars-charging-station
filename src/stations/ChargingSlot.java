package stations;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import java.util.concurrent.Semaphore;

import annotations.Mutable;
import annotations.Readonly;
import car.Car;
import car.ElectricCar;
import exceptions.ChargingSlotFullException;


public class ChargingSlot {
	private int id;
	private int totalSlots;
	protected ChargingStation chargingStation;
	protected Car currentCar = null;
	protected Logger logger;
	
	private Semaphore semaphore;
	
	public ChargingSlot(int numSlots) 
	{		
		this.totalSlots = numSlots;
		/* Initialize Semaphore */
		this.semaphore = new Semaphore(numSlots, true);	
	}

	@Mutable
	public boolean getSlot(Car car) throws ChargingSlotFullException {
		
		try {
			this.currentCar = car;
			if(car instanceof ElectricCar)
			{
				//this.logger.fine("Connecting to ElectricCar slot.");
				System.out.println("Connecting to ElectricCar slot.");
			}
			else {
				//this.logger.fine("Connecting to GasCar slot.");
				System.out.println("Connecting to GasCar slot.");
			}
			/* Car will try to obtain the charging slot */
			return semaphore.tryAcquire();
		} catch (Exception e) {
			this.logger.info("Could not connect to slot.");
			return false;
		}
	}

	@Mutable
	public void leaveSlot(Car car){
		System.out.println("Disconnecting " + this.currentCar.toString());
		this.currentCar = null;
		semaphore.release();
	}

	@Readonly
	public Car getCurrentCar() {
		return this.currentCar;
	}
	
	@Readonly
	public int getTotalSlots()
	{
		return this.totalSlots;
	}

	@Readonly
	private LocalDateTime calculateNextFreeTime() {
		long chargingTime =  (long) this.currentCar.getChargingTime(this.chargingStation);
		return LocalDateTime.now().plusSeconds(chargingTime);
	}

	@Readonly
	public String toString() {
		return String.format("Charging Slot %s", this.id);
	} 
}
	
