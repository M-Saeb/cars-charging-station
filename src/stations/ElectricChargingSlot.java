package stations;

import car.ElectricCar;
import exceptions.ChargingSlotFullException;

public class ElectricChargingSlot extends ChargingSlot {
    public ElectricChargingSlot(ChargingStation chargingStation, int id) {
        super(chargingStation, id);
    }

    public void connectCar(ElectricCar car) throws ChargingSlotFullException {
        super.connectCar(car);
    }

    public void chargeCar() {
        this.currentCar.addFuel(this.chargingStation.getElectricityOutputPerSecond());
    }
}
