package stations;

import api.GPSValues;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;

public class ElectricStation extends ChargingStation
{
    public ElectricStation(int chargingStationID, GPSValues gpsValues, int availableSlots,
            float outputPerSecond) throws InvalidGPSLatitudeException, InvalidGPSLongitudeException {
        super(chargingStationID, gpsValues, availableSlots, outputPerSecond);
        //TODO Auto-generated constructor stub
    }

    /*
    Amount of battery that the station has to charge vehicles
     */
    private float batteryCapacityStorage_f;
    /*
    Amount of charge that the station can distribute to each vehicle
     */
    private float batteryDistributionCapacity_f;
    /*
    Simple counter of how many vehicles have charge in this station
     */
    private int vehiclesChargeCounter_int;

    public float getBatteryCapacityStorage_f()
    {
        if(this.batteryCapacityStorage_f < 0)
        {
            throw new IllegalArgumentException("Charging in this station is not possible...");
        }
        else
        {
            return batteryCapacityStorage_f;
        }
    }

    public void setBatteryCapacityStorage_f(float batteryCapacityStorage_f)
    {
        if(batteryCapacityStorage_f < 0)
        {
            throw new IllegalArgumentException("Battery died...");
        }
        else
        {
            this.batteryCapacityStorage_f = batteryCapacityStorage_f;
        }
    }

    public float getBatteryDistributionCapacity_f() {
        return batteryDistributionCapacity_f;
    }

    /* TODO: Discuss with Leander regarding this subject */
    public void setBatteryDistributionCapacity_f(float batteryDistributionCapacity_f) {
        this.batteryDistributionCapacity_f = batteryDistributionCapacity_f;
    }

    public int getVehiclesChargeCounter_int() {
        return vehiclesChargeCounter_int;
    }

    public void setVehiclesChargeCounter_int(int vehiclesChargeCounter_int)
    {
        if(vehiclesChargeCounter_int < 0)
        {
            vehiclesChargeCounter_int = 0;
            throw new IllegalArgumentException("Impossible to have less than 0 vehicles...");
        }
        else
        {
            this.vehiclesChargeCounter_int = vehiclesChargeCounter_int;
        }
    }
}
