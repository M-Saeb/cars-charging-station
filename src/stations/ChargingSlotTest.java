package stations;

import api.GPSValues;
import car.GasCar;
import exceptions.ChargingSlotFullException;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;
import exceptions.InvalidGPSValueException;
import junit.framework.TestCase;


public class ChargingSlotTest extends TestCase {

	ChargingSlot slot;
	ChargingStation station;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			this.station = new ChargingStation(
				0,
				new GPSValues(50, 50),
				2,
				2,
				10,
				10,
				100,
				100
			);
		} catch (InvalidGPSLatitudeException | InvalidGPSLongitudeException | InvalidGPSValueException e) {
			e.printStackTrace();
			fail("Couldn't initialize ChargingStation");
		} 
		this.slot = new ChargingSlot(0, station);
	}

	/**
	 * Test method for {@link stations.ChargingSlot#ChargingSlot(int, stations.ChargingStation)}.
	 */
	public void testChargingSlot() {
		assertEquals(0, slot.getId());
		assertSame(station, slot.getChargingStation());
	}

	/**
	 * Test method for {@link stations.ChargingSlot#setCarToSlot(car.Car)}.
	 */
	public void testSetCarToSlot() {
		GasCar car = new GasCar("test", 0, 0, 0, null, null, false);
		try {
			slot.setCarToSlot(car);
		} catch (ChargingSlotFullException e) {
			e.printStackTrace();
			fail("Couldn't set car to slot");
		}
		assertSame(slot.getCurrentCar(), car);
	}

	/**
	 * Test method for {@link stations.ChargingSlot#disconnectCar()}.
	 */
	public void testDisconnectCar() {
		GasCar car = new GasCar("test", 0, 0, 0, null, null, false);
		try {
			slot.setCarToSlot(car);
		} catch (ChargingSlotFullException e) {
			e.printStackTrace();
			fail("Couldn't set car to slot");
		}
		slot.disconnectCar();
		assertNull(slot.currentCar);
	}

	/**
	 * Test method for {@link stations.ChargingSlot#addFuelToCar(float)}.
	 */
	public void testAddFuelToCar() {
		float fuelToBeAdded = 10;
		GasCar car = new GasCar("test", 0, 100, 0, null, null, false);
		try {
			slot.setCarToSlot(car);
		} catch (ChargingSlotFullException e) {
			e.printStackTrace();
			fail("Couldn't set car to slot");
		}
		slot.addFuelToCar(fuelToBeAdded);
		assertEquals(fuelToBeAdded, car.getCurrentCapacity());
	}

	/**
	 * Test method for {@link stations.ChargingSlot#fetchElectricityFromStation(float)}.
	 */
	public void testFetchElectricityFromStation() {
		float fuelToFetch = 10;
		float fetchedFuel = slot.fetchElectricityFromStation(fuelToFetch);
		assertEquals(fetchedFuel, fuelToFetch);
	}

	/**
	 * Test method for {@link stations.ChargingSlot#fetchGasFromStation(float)}.
	 */
	public void testFetchGasFromStation() {
		float fuelToFetch = 10;
		float fetchedFuel = slot.fetchGasFromStation(fuelToFetch);
		assertEquals(fetchedFuel, fuelToFetch);
	}

}
