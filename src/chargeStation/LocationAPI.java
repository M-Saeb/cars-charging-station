import Stations.ChargingStation;

public class LocationAPI
{
    ChargingStation class_ChargingStation;
    private float GPSLatitud_f;
    private float GPSLongitud_f;
    private int availableStationID_int;
    private int availableSlotID_int;

    public float getGPSLatitud_f()
    {
        if(this.GPSLatitud_f == 0)
        {
            throw new IllegalArgumentException("GPS Location is Invalid...");
        }
        else
        {
            return GPSLatitud_f;
        }
    }

    public float getGPSLongitud_f()
    {
        if(this.GPSLongitud_f == 0)
        {
            throw new IllegalArgumentException("GPS Location is Invalid...");
        }
        else
        {
            return GPSLongitud_f;
        }
    }

    public int getAvailableStationID_int()
    {
        /*
        If the number of available slots is different from 0.
        Then we will return that station #ID is available.
         */
        if(class_ChargingStation.getAvailableSlots_int() != 0)
        {
            availableStationID_int = class_ChargingStation.getChargingStationID_int();
        }
        else
        {
            System.out.println("No Available Slots in station: " + class_ChargingStation.getChargingStationID_int());
            availableStationID_int = 0;
        }

        return availableStationID_int;
    }

    public int getAvailableSlotID_int()
    {
        if(getAvailableStationID_int() != 0)
        {
            availableSlotID_int = class_ChargingStation.getAvailableSlots_int();
        }
        else
        {
            System.out.println("No Available Slots in station: " + class_ChargingStation.getChargingStationID_int());
            availableSlotID_int = 0;
        }
        return availableSlotID_int;
    }
}
