package chargeStation;

import Stations.ChargingStation;
import java.lang.Math;
import java.util.Arrays;
import java.util.Comparator;

public class LocationAPI
{
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
    Function: sortNearestStation
    Description: Calculate the nearest station regarding the auto current location that is given to the function
    Input:
    -varLatitud -> Current coordinates of the car
    -varLongitud -> Current coordinates of the car
    Return: Nearest Charging Station ID
     */
    public int[] sortNearestStation(float varLatitud, float varLongitud, ChargingStation[] varChargingStation)
    {
        float LattitudDiff = 0;
        float LongitudDiff = 0;
        int[][] totalDistance = new int[this.class_ChargingStation.length][2];
        int[] sortedArray = new int[totalDistance.length];
        /* Method where the function 'setChargingStations' was called */
        if(varChargingStation == null)
        {
            if(getAvailableStationID_int() != 0)
            {
                for(int i = 0; i < this.class_ChargingStation.length; i++)
                {
            /*
            Calculate the distance between the 2 points
            Where x2 is the station latitud and x1 is the car location
            sqrt[ (x2 - x1)^2 + (y2 - y1)^2 ]
            */
                    LattitudDiff = (float) Math.pow((this.class_ChargingStation[i].getGPSLatitude() - varLatitud), 2);
                    LongitudDiff = (float) Math.pow((this.class_ChargingStation[i].getGPSLongitude() - varLongitud), 2);
                    totalDistance[i][0] = (int) this.class_ChargingStation[i].getChargingStationID();
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
        }
        /* Method where the function 'setChargingStations' was not called */
        else
        {
            if(getAvailableStationID_int() != 0)
            {
                for(int i = 0; i < varChargingStation.length; i++)
                {
            /*
            Calculate the distance between the 2 points
            Where x2 is the station latitud and x1 is the car location
            sqrt[ (x2 - x1)^2 + (y2 - y1)^2 ]
            */
                    LattitudDiff = (float) Math.pow((varChargingStation[i].getGPSLatitude() - varLatitud), 2);
                    LongitudDiff = (float) Math.pow((varChargingStation[i].getGPSLongitude() - varLongitud), 2);
                    totalDistance[i][0] = (int) varChargingStation[i].getChargingStationID();
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
        }
        /* Return the station ID that it is closest to the station */
        return sortedArray;
    }

    /*
    Function: calculateNearestStation
    Description: Calculate the nearest station regarding the auto current location that is given to the function
    Input:
    -varSortedArray[] -> sorted array, so we know the order of the stations
    -varArrStations[] -> array or list of all the stations in the area
    Return: Nearest Charging Station ID
     */
    public ChargingStation[] calculateNearestStation(int[] varSortedArray, ChargingStation[] varArrStations)
    {
        ChargingStation[] sortedStations = new ChargingStation[varArrStations.length];

        for(int i = 0; i < varSortedArray.length; i++)
        {
            int currentID = varSortedArray[i];
            for(int j = 0; j < varArrStations.length; j++)
            {
                ChargingStation tempChargingStation = varArrStations[j];
                if(tempChargingStation.getChargingStationID() == currentID)
                {
                    sortedStations[i] = tempChargingStation;
                    /* Exit current cycle as we already found a match and updated the list */
                    break;
                }
                else
                {
                    continue;
                }
            }
        }

        return sortedStations;
    }
	public static void checkGPSValues(float latitude, float longitude) throws InvalidGPSLatitude, InvalidGPSLongitude {
		if (latitude < -90.0 || latitude > 90) {
			throw new InvalidGPSLatitude("Invalid latitude: " + latitude);
		}
		if (longitude < -180.0 || longitude> 180) {
			throw new InvalidGPSLongitude("Invalid longtitude: " + longitude);
		}
		
	}

}
