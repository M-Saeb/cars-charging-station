package stations;

import java.util.ArrayList;

import api.GPSValues;
import car.GasCar;
import car.Car;
import car.ElectricCar;
import junit.framework.TestCase;

public class TestChargingStation extends TestCase {

    ChargingStation createChargingStation(){
        ChargingStation station;
        GPSValues gpsValue = new GPSValues((float)17.7749 ,(float)20.4194);
        try{
            station = new ChargingStation(
                1, gpsValue, 3, 3, (float)2.5, (float)3.0, (float)1000, (float)1000
            );
        } catch (Exception e){
            assertTrue("Exception was not suppoed to be raised", false);
            return null;
        }
        return station;
    }

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

    void setGasCarToChargingStationg(ChargingStation station, GasCar car){
        station.addCarToWaitingQueue(car);
        station.sendCarsToEmptyGasSlots();
    }

    void setElectricCarToChargingStationg(ChargingStation station, ElectricCar car){
        station.addCarToWaitingQueue(car);
        station.sendCarsToEmptyElectricSlots();
    }

    public void testGetAvailableGasSlotsFunctionOnInit(){
        ChargingStation station = this.createChargingStation();
        int value =  station.getAvailableGasSlots();
        assertEquals(3, value);
    }

    public void testGetAvailableGasSlotsFunctionOnFilled(){
        ChargingStation station = this.createChargingStation();
        GasCar car1 = createGasCar();
        setGasCarToChargingStationg(station, car1);
        GasCar car2 = createGasCar();
        setGasCarToChargingStationg(station, car2);
        GasCar car3 = createGasCar();
        setGasCarToChargingStationg(station, car3);
        int value =  station.getAvailableGasSlots();
        assertEquals(0, value);
    }

    public void testGetAvailableElectricSlotsFunctionOnInit(){
        ChargingStation station = this.createChargingStation();
        int value =  station.getAvailableElectricSlots();
        assertEquals(3, value);
    }

    public void testGetAvailableElectricSlotsFunctionOnFilled(){
        ChargingStation station = this.createChargingStation();
        ElectricCar car1 = createElectricCar();
        setElectricCarToChargingStationg(station, car1);
        ElectricCar car2 = createElectricCar();
        setElectricCarToChargingStationg(station, car2);
        ElectricCar car3 = createElectricCar();
        setElectricCarToChargingStationg(station, car3);
        int value =  station.getAvailableElectricSlots();
        assertEquals(0, value);
    }
    
    public void testWaitingTimeElectricCar()
    {
        ChargingStation station = this.createChargingStation();
        ElectricCar car1 = createElectricCar();
        setElectricCarToChargingStationg(station, car1);
        
        station.leaveStationWaitingQueue(car1);
        ArrayList<Car> waitingQueueTest = station.getWaitingCarsQueue();
        assertEquals(waitingQueueTest.size(), 0);
    }
    public void testWaitingTimeGasCar()
    {
        ChargingStation station = this.createChargingStation();
        GasCar car1 = createGasCar();
        setGasCarToChargingStationg(station, car1);
        
        station.leaveStationWaitingQueue(car1);
        ArrayList<Car> waitingQueueTest = station.getWaitingCarsQueue();
        assertEquals(waitingQueueTest.size(), 0);
    }
}