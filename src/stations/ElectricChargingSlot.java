package stations;

import annotations.Mutable;
import car.ElectricCar;
import exceptions.ChargingSlotFullException;

public class ElectricChargingSlot extends ChargingSlot {
    public ElectricChargingSlot(ChargingStation chargingStation, int id) {
        super(chargingStation, id);
    }

    @Mutable
    public synchronized void connectCar(ElectricCar car) throws ChargingSlotFullException {
        super.connectCar(car);
    }

    @Mutable
    public void chargeCar() {
        this.logger.finer("Charging vehicle in slot...");
        float LevelOfElectricityStorage = this.chargingStation.getLevelOfElectricityStorage();
        float electricityOutputPerSecond = this.chargingStation.getElectricityOutputPerSecond();
        float missingFuel = this.currentCar.getMissingAmountOfFuel();
        float fuelToBeTransferred;

        // If car needs more than station's output, add as much as the output
        // Else, add what the car needs.
        if (missingFuel >= electricityOutputPerSecond){
            fuelToBeTransferred = electricityOutputPerSecond;
        } else {
            fuelToBeTransferred = missingFuel;
        }

        if (LevelOfElectricityStorage < fuelToBeTransferred) {
            this.logger.info("electricity has run out.");
            return;
        }

        this.chargingStation.takeElectricityFromReserve(fuelToBeTransferred);
        this.currentCar.addFuel(fuelToBeTransferred);
        logger.fine(String.format(
                "Charged %s with %f values. Car still needs: %f",
                this.currentCar.toString(),
                fuelToBeTransferred,
                this.currentCar.getMissingAmountOfFuel()));
    }
}
