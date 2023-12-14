package stations;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

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
	
	private SlotAssigment[] slotAssigment;
	private Semaphore semaphore;
	
	public ChargingSlot(int numSlots) 
	{		
		this.totalSlots = numSlots;
		/* Initialize Semaphore */
		this.semaphore = new Semaphore(numSlots, true);	
		this.slotAssigment = new SlotAssigment[numSlots];
		
		for(int i = 0; i < numSlots; i++)
		{
			slotAssigment[i] = new SlotAssigment();
			Thread slotThread = new Thread(new ChargingSlotRun(numSlots));
			slotThread.start();
		}
	}
	
	/* Whole purpose of this class is to just implement a way of tracking which car goes into which slot */
	private static class SlotAssigment{
		private Car car;
		public Car getCar()
		{
			return car;
		}
		public void setCar(Car car)
		{
			this.car = car;
		}
		public int getSemaphoreID()
		{
			return System.identityHashCode(this);
		}
	}

	@Mutable
	public boolean getSlot(Car car) throws ChargingSlotFullException {
		boolean aquireSemaphore = false;
		try {
			this.currentCar = car;
			if(car instanceof ElectricCar)
			{
				//this.logger.fine("Connecting to ElectricCar slot.");
				System.out.println("Connecting to ElectricCar slot.");
				System.out.println("Car ID... " + car.getCarNumber());
			}
			else {
				//this.logger.fine("Connecting to GasCar slot.");
				System.out.println("Connecting to GasCar slot.");
				System.out.println("Car ID... " + car.getCarNumber());
			}
			aquireSemaphore = semaphore.tryAcquire();
			if(aquireSemaphore)
			{
				setCarToSlot(car);
			}
			/* Car will try to obtain the charging slot */
			return aquireSemaphore;
		} catch (Exception e) {
			this.logger.info("Could not connect to slot.");
			return false;
		}
	}
	
	@Mutable
	private void setCarToSlot(Car car)
	{
		for(int i = 0; i < slotAssigment.length; i++)
		{
			if(slotAssigment[i].getCar() == null)
			{
				slotAssigment[i].setCar(car);
				continue;
			}
		}
	}
	@Mutable
	private SlotAssigment getCartoSlot(Car car)
	{
		for(SlotAssigment tempSlotAssigment : slotAssigment)
		{
			if((tempSlotAssigment.getCar() != null) && (tempSlotAssigment.getCar().equals(car)))
			{
				return tempSlotAssigment;
			}
		}
		return null;
	}

	@Mutable
	public void leaveSlot(Car car){
		System.out.println("Disconnecting " + this.currentCar.toString());
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
	
	private class ChargingSlotRun implements Runnable{
		
		private int slotID;
		
		public ChargingSlotRun(int SlotID)
		{
			this.slotID = SlotID;
		}
		@Override
		public void run() {
			while(true)
			{
				try {
					int randWait = (int) (Math.random() * 1000);
					Thread.sleep(randWait);
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					break;
				}
				SlotAssigment assignment = slotAssigment[this.slotID];
				if(assignment.getCar() != null)
				{
					System.out.println("Car... " + assignment.getCar() + " is in slot... " + slotID);
				}
			}
		}
	}
}	
