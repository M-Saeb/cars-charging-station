package car;

import api.GPSValues;
import junit.framework.TestCase;

public class TestCars extends TestCase
{
    GasCar createGasCar(){
        GPSValues gpsValue = new GPSValues((float)17.7749 ,(float)20.4194);
        GasCar car = new GasCar(
            "1", 100, 200, 30, null, gpsValue, false
        );
        return car;
    }
    
    ElectricCar createElectricCar(){
        GPSValues gpsValue = new GPSValues((float)17.7749 ,(float)20.4194);
        ElectricCar car = new ElectricCar(
            "1", 100, 200, 30, null, gpsValue, false
        );
        return car;
    }
	
    public void testAddingFuel(){
        GasCar testCar = createGasCar();
        float level_before_charging = testCar.getCurrentCapacity();
        testCar.addFuel(10);
        assertEquals(level_before_charging + 10, testCar.getCurrentCapacity(), 0.001);
    }
    
    public void testPriority(){
        ElectricCar testCar = createElectricCar();
        testCar.setPriorityFlag(true);
        assertEquals(testCar.isPriority(), true);
    }
    
    public void testState(){
        ElectricCar testCar = createElectricCar();
        testCar.setCurrentState(CarState.charging);
        assertEquals(testCar.getCurrentState(), CarState.charging);
    }
    
    public void testMaxWaitDuration()
    {
    	GasCar testCar = createGasCar();
        testCar.setMaximumWaitingDuration(100);
        assertEquals(testCar.getMaximumWaitingDuration(), 100, 0.001);
    }
}
