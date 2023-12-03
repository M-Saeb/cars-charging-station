package stations;

import annotations.Mutable;
import car.GasCar;
import exceptions.ChargingSlotFullException;

public class GasChargingSlot extends ChargingSlot {
    public GasChargingSlot(ChargingStation chargingStation, int id) {
        super(chargingStation, id);
    }

    @Mutable
    public void chargeCar(GasCar car) throws ChargingSlotFullException {
        super.connectCar(car);
    }
    
    @Mutable
    public void chargeCar() {
        this.currentCar.addFuel(this.chargingStation.getGasOutputPerSecond());
    }

}
