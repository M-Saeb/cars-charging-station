import Stations.ChargingStation;
import java.lang.Math;
import java.util.Arrays;
import java.util.Comparator;

public class LocationAPI
{
    ChargingStation[] class_ChargingStation;
    int[] class_sortedChargingStation;
    private int totalAmountChargingStations;
    private int availableStationID_int;

    public int getAvailableStationID_int()
    {
        int amountOfChargingStations_int = this.class_ChargingStation.length;
        /*
        If the number of available slots is different from 0.
        Then we will return that station #ID is available.
         */
        if(amountOfChargingStations_int != 0)
        {
            /*
            TODO: Logic (possible function to return sorted list of the possible locations
             */
        }
        else
        {
            System.out.println("No Available Slots in station: ");
            availableStationID_int = 0;
        }

        return availableStationID_int;
    }

    /* Local functions */
    /*
    Function: setChargingStations
    Description: Assign the class ChargingStation to the API so all the functions can use this object.
    Input:
    -varChargingStation -> List of Charging stations in the area
    Return: Void
    Note:
    Important to use before 'calculateNearestStation'
     */
    public void setChargingStations(ChargingStation[] varChargingStation)
    {
        this.class_ChargingStation = varChargingStation;
    }
    /*
    Function: calculateNearestStation
    Description: Calculate the nearest station regarding the auto current location that is given to the function
    Input:
    -varLatitud -> Current coordinates of the car
    -varLongitud -> Current coordinates of the car
    Return: Nearest Charging Station ID
     */
    public int[] calculateNearestStation(float varLatitud, float varLongitud)
    {
        float LattitudDiff = 0;
        float LongitudDiff = 0;
        int[][] totalDistance = new int[this.class_ChargingStation.length][2];
        int[] sortedArray = new int[totalDistance.length];
        if(getAvailableStationID_int() != 0)
        {
            for(int i = 0; i < this.class_ChargingStation.length; i++)
            {
            /*
            Calculate the distance between the 2 points
            Where x2 is the station latitud and x1 is the car location
            sqrt[ (x2 - x1)^2 + (y2 - y1)^2 ]
            */
                LattitudDiff = (float) Math.pow((this.class_ChargingStation[i].getGPSLatitud_f() - varLatitud), 2);
                LongitudDiff = (float) Math.pow((this.class_ChargingStation[i].getGPSLongitud_f() - varLongitud), 2);
                totalDistance[i][0] = (int) this.class_ChargingStation[i].getChargingStationID_int();
                totalDistance[i][1] = (int) Math.sqrt(LattitudDiff + LongitudDiff);
            }
            /* Compare elements regarding the second position of the array and sort them from shortest to longest */
            Arrays.sort(totalDistance, Comparator.comparingInt(arr -> arr[1]));
            for(int i = 0; i<sortedArray.length; i++)
            {
                sortedArray[i] = totalDistance[i][0];
            }
            /* TODO: DEBUG print array */

        }
        else
        {
            System.out.println("No available stations nearby");
            /* Fill array with zeros, meaning no available station is nearby */
            Arrays.fill(sortedArray, 0);
        }
        /* Return the station ID that it is closest to the station */
        return sortedArray;
    }

	public void setCarCurrentGPS_f(float[][] carCurrentGPS_f)
	{
		this.carCurrentGPS_f = carCurrentGPS_f;
	}
}
