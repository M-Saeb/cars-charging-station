package Stations;

public class ElectricStation extends ChargingStation
{
    /*
    Amount of battery that the station has to charge vehicles
     */
    private float batteryAmountStorage_f;
    /*
    Amount of charge that the station can distribute to each vehicle
     */
    private float batteryDistributionCapacity_f;
    /*
    Simple counter of how many vehicles have charge in this station
     */
    private int vehiclesChargeCounter_int;

    public float getBatteryAmountStorage_f() {
        return batteryAmountStorage_f;
    }

    public void setBatteryAmountStorage_f(float batteryAmountStorage_f) {
        this.batteryAmountStorage_f = batteryAmountStorage_f;
    }

    public float getBatteryDistributionCapacity_f() {
        return batteryDistributionCapacity_f;
    }

    public void setBatteryDistributionCapacity_f(float batteryDistributionCapacity_f) {
        this.batteryDistributionCapacity_f = batteryDistributionCapacity_f;
    }

    public int getVehiclesChargeCounter_int() {
        return vehiclesChargeCounter_int;
    }

    public void setVehiclesChargeCounter_int(int vehiclesChargeCounter_int) {
        this.vehiclesChargeCounter_int = vehiclesChargeCounter_int;
    }
}
