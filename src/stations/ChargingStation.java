package stations;

import java.util.ArrayList;
import java.util.logging.Logger;

import api.GPSValues;
import api.LocationAPI;
import car.Car;
import car.ElectricCar;
import car.GasCar;
import exceptions.ChargingSlotFullException;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;
import exceptions.InvalidGPSValueException;

public class ChargingStation
{
	/*
	 * GPS location from every charging station. This value can be sent to the other
	 * types of station from this super class
	 */
	private GPSValues gpsValues;

	/* Amount of available slot per charging station */
	private int numberOfAvailableSlots;

	private int chargingStationID;
	private float gasOutputPerSecond;
	private float electricityOutputPerSecond;
	private Logger logger;
	private GasChargingSlot[] gasSlots;
	private ElectricChargingSlot[] electricSlots;
	private float LevelOfElectricityStorage;
	private float LevelOfGasStorage;
	private ArrayList<Car> queue = new ArrayList<Car>();
	private float waitTime = 0;

	public ChargingStation(int chargingStationID, GPSValues gpsValues, int numGasSlots, int numElectricSlots,
			float gasOutputPerSecondoutputPerSecond, float electricityOutputPerSecond)
			throws InvalidGPSLatitudeException, InvalidGPSLongitudeException, InvalidGPSValueException
	{
		this.logger = Logger.getLogger(this.toString());
		this.chargingStationID = chargingStationID;
		try
		{
			LocationAPI.checkGPSValues(gpsValues);
		} catch (InvalidGPSLatitudeException | InvalidGPSLongitudeException e)
		{
			this.logger.severe(e.getStackTrace().toString());
			throw e;
		} catch (Exception e)
		{
			this.logger.severe(e.getStackTrace().toString());
			throw e;
		}
		this.gpsValues = gpsValues;

		this.gasSlots = new GasChargingSlot[numGasSlots];
		this.electricSlots = new ElectricChargingSlot[numElectricSlots];

		this.setNumberOfAvailableSlots(numGasSlots + numElectricSlots);
		if((numGasSlots == 0) && (numElectricSlots == 0))
		{
			throw new IllegalArgumentException("Station can't have 0 slots");
		}
		else if(numGasSlots < 0)
		{
			throw new IllegalArgumentException("Station can't have fewer than 0 gas slots.");
		}
		else if(numElectricSlots < 0)
		{
			throw new IllegalArgumentException("Station can't have fewer than 0 electirc slots.");
		}

		int slotIDs = 0;
		if(numGasSlots > 0)
		{
			GasChargingSlot[] myGasSlots = new GasChargingSlot[numGasSlots];
			for (int i = 0; i < numGasSlots; i++)
			{
				myGasSlots[i] = new GasChargingSlot(this, slotIDs++);
			}
			this.gasSlots = myGasSlots;
		}
		if(numElectricSlots > 0)
		{
			ElectricChargingSlot[] myElectricSlots = new ElectricChargingSlot[numElectricSlots];
			for (int i = 0; i < numElectricSlots; i++)
			{
				myElectricSlots[i] = new ElectricChargingSlot(this, slotIDs++);
			}
			this.electricSlots = myElectricSlots;
		}

		if(gasOutputPerSecondoutputPerSecond < 0 || electricityOutputPerSecond < 0)
		{
			throw new IllegalArgumentException("Charging station output can't be fewer than 0.");
		}
		if(numGasSlots == 0 && gasOutputPerSecondoutputPerSecond > 0)
		{
			throw new IllegalArgumentException("Station can't have 0 gas slots and still have gas output potential.");
		}
		else if(numElectricSlots == 0 && electricityOutputPerSecond > 0)
		{
			throw new IllegalArgumentException(
					"Station can't have 0 electricty slots and still have electrity output potential.");
		}
		this.gasOutputPerSecond = gasOutputPerSecondoutputPerSecond;
		this.electricityOutputPerSecond = electricityOutputPerSecond;

		this.logger.fine("Initiated " + this.toString());
	}

	public void addCar(Car car)
	{
		// add this car to queue

		// calcualte new wait time
	}

	public String toString()
	{
		return String.format("Charging Station %d", this.chargingStationID);
	}

	public float getWaitTime()
	{
		return waitTime;
	}

	public float getGPSLatitude() throws InvalidGPSValueException
	{
		if(this.gpsValues.getLatitude() == 0)
		{
			try
			{
				throw new InvalidGPSLatitudeException("Invalid Latitud value...");
			} catch (Exception e)
			{
				System.out.println("Invalid Latitud value...");
				e.printStackTrace();
			}
		}
		else
		{
			/*
			 * Do nothing
			 */
		}
		return this.gpsValues.getLatitude();
	}

	public float getGPSLongitude() throws InvalidGPSValueException
	{
		if(this.gpsValues.getLongitude() == 0)
		{
			try
			{
				throw new InvalidGPSLongitudeException("Invalid Latitud value...");
			} catch (Exception e)
			{
				System.out.println("Invalid Latitud value...");
				e.printStackTrace();
			}
		}
		else
		{
			/*
			 * Do nothing
			 */
		}
		return this.gpsValues.getLongitude();
	}

	public int getNumberOfAvailableSlots()
	{
		return numberOfAvailableSlots;
	}

	public int getAvailableGasSlots()
	{
		return gasSlots.length;
	}

	public int getAvailableElectricSlots()
	{
		return electricSlots.length;
	}

	public void setNumberOfAvailableSlots(int availableSlots)
	{
		this.numberOfAvailableSlots = availableSlots;
	}

	public void setChargingStationID(int chargingStationID)
	{
		this.chargingStationID = chargingStationID;
	}

	public int getChargingStationID()
	{
		return chargingStationID;
	}

	public float getGasOutputPerSecond()
	{
		return gasOutputPerSecond;
	}

	public float getElectricityOutputPerSecond()
	{
		return electricityOutputPerSecond;
	}

	public float getLevelOfElectricityStorage()
	{
		return LevelOfElectricityStorage;
	}

	public void setLevelOfElectricityStorage(float levelOfElectricityStorage)
	{
		LevelOfElectricityStorage = levelOfElectricityStorage;
	}

	public float getLevelOfGasStorage()
	{
		return LevelOfGasStorage;
	}

	public void setLevelOfGasStorage(float levelOfGasStorage)
	{
		LevelOfGasStorage = levelOfGasStorage;
	}

	/*
	 * Private function for getting the waiting time for the next free electric slot
	 */
	private double getWaitingTimeForNextSlotElectric()
	{
		double waitingTime = 0;

		for (ElectricChargingSlot slot : electricSlots)
		{
			if(slot.getCurrentCar() == null)
			{
				continue;
			}

			double waitingTimeOfCurrCar = slot.getCurrentCar().getChargingTime(this);

			if(waitingTime == 0)
			{
				waitingTime = waitingTimeOfCurrCar;
				continue;
			}

			if(waitingTimeOfCurrCar < waitingTime)
			{
				waitingTime = waitingTimeOfCurrCar;
			}
		}

		return waitingTime;
	}

	/*
	 * Private function for getting the waiting time for the next free gas slot
	 */
	private double getWaitingTimeForNextSlotGas()
	{
		double waitingTime = 0;

		for (GasChargingSlot slot : gasSlots)
		{
			if(slot.getCurrentCar() == null)
			{
				continue;
			}

			double waitingTimeOfCurrCar = slot.getCurrentCar().getChargingTime(this);

			if(waitingTime == 0)
			{
				waitingTime = waitingTimeOfCurrCar;
				continue;
			}

			if(waitingTimeOfCurrCar < waitingTime)
			{
				waitingTime = waitingTimeOfCurrCar;
			}
		}

		return waitingTime;
	}

	/*
	 * This should get the waiting time for a specific car in queue It should
	 * consider the waiting time + the charging times of the cars in front of that
	 * car.
	 */
	public double getCarWaitingTime(Car car)
	{
		double waitingTime = 0;

		// Getting the waiting time for a next slot
		if(car instanceof ElectricCar)
		{
			waitingTime += getWaitingTimeForNextSlotElectric();
		}
		else if(car instanceof GasCar)
		{
			waitingTime += getWaitingTimeForNextSlotGas();
		}

		// Adding the waiting time of the cars that are in the queue in front of the car
		for (Car queueCar : queue)
		{
			// Checking if the car that the time is calculated for is equal to the car at
			// the position of the queue
			if(queueCar.equals(car))
			{
				break;
			}

			// Checking if the objects are from the same type (same type of car)
			if(queueCar.getClass().equals(car.getClass()))
			{
				waitingTime += queueCar.getChargingTime(this);
			}
		}

		return waitingTime;
	}

	/*
	 * This should get the total waiting time of the station. It should consider the
	 * waiting time + the charging times of the cars in the queue.
	 */
	public double getTotalWaitingTimeElectric(Car car)
	{
		double totalWaitingTime = 0;

		totalWaitingTime += getWaitingTimeForNextSlotElectric();

		// Checking if there are cars in the queue, otherwise returning
		if(queue.size() == 0)
		{
			return totalWaitingTime;
		}

		// Branch if car is a prioritized car
		if(car.isPriority())
		{
			for (Car queueCar : queue)
			{
				// Break if current checked car is not prioritized (first not prioritized car)
				if(!queueCar.isPriority())
				{
					break;
				}

				if(queueCar instanceof ElectricCar)
				{
					totalWaitingTime += car.getChargingTime(this);
				}
			}

			return totalWaitingTime;
		}

		// Branch if car is not a prioritized car
		for (Car queueCar : queue)
		{
			if(queueCar instanceof ElectricCar)
			{
				totalWaitingTime += queueCar.getChargingTime(this);
			}
		}

		return totalWaitingTime;
	}

	public double getTotalWaitingTimeGas(Car car)
	{
		double totalWaitingTime = 0;

		totalWaitingTime += getWaitingTimeForNextSlotGas();

		// Checking if there are cars in the queue, otherwise returning
		if(queue.size() == 0)
		{
			return totalWaitingTime;
		}

		// Branch if car is a prioritized car
		if(car.isPriority())
		{
			for (Car queueCar : queue)
			{
				// Break if current checked car is not prioritized (first not prioritized car)
				if(!queueCar.isPriority())
				{
					break;
				}

				if(queueCar instanceof GasCar)
				{
					totalWaitingTime += car.getChargingTime(this);
				}
			}

			return totalWaitingTime;
		}

		// Branch if car is not a prioritized car
		for (Car queueCar : queue)
		{
			if(queueCar instanceof GasCar)
			{
				totalWaitingTime += queueCar.getChargingTime(this);
			}
		}

		return totalWaitingTime;
	}

	public void addCarToQueue(Car car)
	{
		queue.add(car);
	}

	/*
	 * Remove car from station queue.
	 */
	public void leaveStationQueue(Car car)
	{
		queue.remove(car);
	}

	/*
	 * Disonnect car from slot.
	 */
	public void leaveStation(Car car)
	{
		ChargingSlot stationSlots[];
		if(car instanceof ElectricCar)
		{
			stationSlots = this.electricSlots;
		}
		else
		{ // GasCar
			stationSlots = this.gasSlots;
		}
		for (ChargingSlot slot : stationSlots)
		{
			if(slot.currentCar == car)
			{
				slot.disconnectCar();
			}
			return;
		}
		logger.severe("Something went wrong: you order car numbered  " + car.getCarNumber()
				+ " out of the station, but the car is not in the station");
	}

	/*
	 * Send cars in queue to free slots and set their state to charging.
	 */
	public void sendCarsToFreeSlots()
	{
		/* get the currently free slots */
		ArrayList<ChargingSlot> freeSlots = new ArrayList<ChargingSlot>();
		for (ElectricChargingSlot slot : electricSlots)
		{
			if(slot.currentCar == null)
			{
				freeSlots.add(slot);
			}
		}
		for (GasChargingSlot slot : gasSlots)
		{
			if(slot.currentCar == null)
			{
				freeSlots.add(slot);
			}
		}

		/* updating each free slot */
		for (ChargingSlot slot : freeSlots)
		{

			/* getting the type (class) of the slot */
			Class<?> acceptedCarType;
			if(slot instanceof ElectricChargingSlot)
			{
				acceptedCarType = ElectricCar.class;
			}
			else
			{ // GasCharingSlot
				acceptedCarType = GasCar.class;
			}

			/*
			 * checking which is the first car in the qeueue to have the same fuel as the
			 * slot
			 */
			for (Car car : queue)
			{
				if(car.getClass() == acceptedCarType)
				{
					try
					{
						slot.connectCar(car);
						queue.remove(car);
						break;
					} catch (ChargingSlotFullException e)
					{
						break;
					}
				}
			}
		}
	}

	/*
	 * Add output per second of station to the car's tank. Make sure the car's tank
	 * only gets full and not more than that. If a car's tank is already full, do
	 * nothing.
	 */
	public void chargeCarsInSlots()
	{
		for (ElectricChargingSlot chargingSlot : electricSlots)
		{
			if(chargingSlot.getCurrentCar() == null)
			{
				continue;
			}

			if(LevelOfElectricityStorage < electricityOutputPerSecond)
			{
				break;
			}

			float missingFuel = chargingSlot.getCurrentCar().getMissingAmountOfFuel();
			if(missingFuel >= electricityOutputPerSecond)
			{
				chargingSlot.getCurrentCar().addFuel(electricityOutputPerSecond);
				LevelOfElectricityStorage -= electricityOutputPerSecond;
			}
			else
			{
				chargingSlot.getCurrentCar().addFuel(missingFuel);
				LevelOfElectricityStorage -= missingFuel;
			}
		}

		for (GasChargingSlot chargingSlot : gasSlots)
		{
			if(LevelOfGasStorage < gasOutputPerSecond)
			{
				break;
			}

			if(chargingSlot.getCurrentCar() == null)
			{
				continue;
			}

			float missingFuel = chargingSlot.getCurrentCar().getMissingAmountOfFuel();
			if(missingFuel >= gasOutputPerSecond)
			{
				chargingSlot.getCurrentCar().addFuel(gasOutputPerSecond);
				LevelOfGasStorage -= gasOutputPerSecond;
			}
			else
			{
				chargingSlot.getCurrentCar().addFuel(missingFuel);
				LevelOfGasStorage -= missingFuel;
			}
		}
	}

	public float getTotalLeftoverElectricity()
	{
		int pendingUsedElectricity = 0;

		for (int i = 0; i < electricSlots.length; i++)
		{
			pendingUsedElectricity += electricSlots[i].getCurrentCar().getMissingAmountOfFuel();
		}

		for (int i = 0; i < queue.size(); i++)
		{
			if(queue.get(i) instanceof ElectricCar)
			{
				pendingUsedElectricity += queue.get(i).getMissingAmountOfFuel();
			}
		}

		return LevelOfElectricityStorage - pendingUsedElectricity;
	}

	public float getTotalLeftoverGas()
	{
		int pendingUsedGas = 0;

		for (int i = 0; i < gasSlots.length; i++)
		{
			pendingUsedGas += gasSlots[i].getCurrentCar().getMissingAmountOfFuel();
		}

		for (int i = 0; i < queue.size(); i++)
		{
			if(queue.get(i) instanceof GasCar)
			{
				pendingUsedGas += queue.get(i).getMissingAmountOfFuel();
			}
		}

		return LevelOfGasStorage - pendingUsedGas;
	}
}
