package Stations;

public class GasStation extends ChargingStation
{
    /*
    Amount of battery that the station has to charge vehicles
     */
    private float fuelAmountStorage_f;
    /*
    Amount of charge that the station can distribute to each vehicle
     */
    private float fuelDistributionCapacity_f;
    /*
    Simple counter of how many vehicles have charge in this station
     */
    private int vehiclesChargeCounter_int;

    public float getFuelAmountStorage_f() {
        return fuelAmountStorage_f;
    }

    public void setFuelAmountStorage_f(float fuelAmountStorage_f) {
        this.fuelAmountStorage_f = fuelAmountStorage_f;
    }

    public float getFuelDistributionCapacity_f() {
        return fuelDistributionCapacity_f;
    }

    public void setFuelDistributionCapacity_f(float fuelDistributionCapacity_f) {
        this.fuelDistributionCapacity_f = fuelDistributionCapacity_f;
    }

    public int getVehiclesChargeCounter_int() {
        return vehiclesChargeCounter_int;
    }

    public void setVehiclesChargeCounter_int(int vehiclesChargeCounter_int) {
        this.vehiclesChargeCounter_int = vehiclesChargeCounter_int;
    }
}
