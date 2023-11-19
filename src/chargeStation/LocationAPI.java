import Stations.ChargingStation;

public class LocationAPI
{
    ChargingStation class_ChargingStation;
    private float[][][] ChargingStationGPS_f;
    private float[][] carCurrentGPS_f;
    private int availableStationID_int;
    private int availableSlotID_int;

    public LocationAPI(float[][][] GPSCoordinates_f) {
        this.ChargingStationGPS_f = GPSCoordinates_f;
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

    /* Local functions */
    /*
    Function: calculateNearestStation
    Description: Calculate the nearest station regarding the auto current location that is given to the function
    Return: Nearest Charging Station ID
     */
    protected int calculateNearestStation()
    {
        float nearestStationID = 0;
        float temp = 0;
        int numStations = this.ChargingStationGPS_f.length;
        for(int i = 0; i < numStations; i++)
        {
            for(int j = 0; j < numStations; j++)
            {
                nearestStationID = (this.carCurrentGPS_f[0][j] - this.ChargingStationGPS_f[i][0][j]) / (this.carCurrentGPS_f[j][0] - this.ChargingStationGPS_f[i][j][0]);
                /* TODO: Implement bubble sort or something to find shortest route */
            }
        }
        return 0;
    }

	public void setCarCurrentGPS_f(float[][] carCurrentGPS_f)
	{
		this.carCurrentGPS_f = carCurrentGPS_f;
	}
}
