package stations;

import car.GasCar;
import exceptions.ChargingSlotFullException;

public class GasChargingSlot extends ChargingSlot {
    public GasChargingSlot(ChargingStation chargingStation, int id) {
        super(chargingStation, id);
    }

    public void chargeCar(GasCar car) throws ChargingSlotFullException {
        super.connectCar(car);
    }

    public void chargeCar() {
        this.currentCar.addFuel(this.chargingStation.getGasOutputPerSecond());
    }

}
