package stations;

import annotations.Mutable;
import car.GasCar;
import exceptions.ChargingSlotFullException;

public class GasChargingSlot extends ChargingSlot {
    public GasChargingSlot(ChargingStation chargingStation, int id) {
        super(chargingStation, id);
    }

    @Mutable
    public synchronized void chargeCar(GasCar car) throws ChargingSlotFullException {
        super.connectCar(car);
    }

    @Mutable
    public void chargeCar() {
        this.logger.finer("Charging vehicle in slot...");
        float LevelOfGasStorage = this.chargingStation.getLevelOfGasStorage();
        float gasOutputPerSecond = this.chargingStation.getGasOutputPerSecond();
        float missingFuel = this.currentCar.getMissingAmountOfFuel();
        float fuelToBeTransferred;

        // If car needs more than station's output, add as much as the output
        // Else, add what the car needs.
        if (missingFuel >= gasOutputPerSecond){
            fuelToBeTransferred = gasOutputPerSecond;
        } else {
            fuelToBeTransferred = missingFuel;
        }

        if (LevelOfGasStorage < fuelToBeTransferred) {
            this.logger.info("gas has run out.");
            return;
        }

        this.chargingStation.takeGasFromReserve(fuelToBeTransferred);
        this.currentCar.addFuel(fuelToBeTransferred);
        logger.fine(String.format(
                "Charged %s with %f values. Car still needs: %f",
                this.currentCar.toString(),
                fuelToBeTransferred,
                this.currentCar.getMissingAmountOfFuel()));
    }

}
