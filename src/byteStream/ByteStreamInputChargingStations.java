package byteStream;

import java.io.FileInputStream;
import java.io.IOException;

import api.GPSValues;
import api.LocationAPI;
import stations.ChargingStation;

/**
 * 
 */
public class ByteStreamInputChargingStations 
{
	private int chargingStationID;
	private GPSValues gpsValues;
	private int numGasSlots;
	private int numElectricSlots;
	private float gasOutputPerSecond;
	private float electricityOutputPerSecond;
	private float levelOfGasStorage;
	private float levelOfElectricityStorage;
	
	ChargingStation[] listStations;
	
	public ChargingStation[] getListStations() {
		return listStations;
	}

	private void setListStations(ChargingStation[] listStations) {
		this.listStations = listStations;
	}

	public int getChargingStationID() {
		return chargingStationID;
	}

	private void setChargingStationID(int chargingStationID) {
		this.chargingStationID = chargingStationID;
	}

	public GPSValues getGpsValues() {
		return gpsValues;
	}

	private void setGpsValues(GPSValues gpsValues) {
		this.gpsValues = gpsValues;
	}

	public int getNumGasSlots() {
		return numGasSlots;
	}

	private void setNumGasSlots(int numGasSlots) {
		this.numGasSlots = numGasSlots;
	}

	public int getNumElectricSlots() {
		return numElectricSlots;
	}

	private void setNumElectricSlots(int numElectricSlots) {
		this.numElectricSlots = numElectricSlots;
	}

	public float getGasOutputPerSecond() {
		return gasOutputPerSecond;
	}

	private void setGasOutputPerSecond(float gasOutputPerSecond) {
		this.gasOutputPerSecond = gasOutputPerSecond;
	}

	public float getElectricityOutputPerSecond() {
		return electricityOutputPerSecond;
	}

	private void setElectricityOutputPerSecond(float electricityOutputPerSecond) {
		this.electricityOutputPerSecond = electricityOutputPerSecond;
	}

	private void setLevelOfElectricityStorage(float LevelOfElectricityStorage) {
		this.levelOfElectricityStorage = LevelOfElectricityStorage;
	}

	private void setLevelOfGasStorage(float LevelOfGasStorage) {
		this.levelOfGasStorage = LevelOfGasStorage;
	}

	private float getLevelOfElectricityStorage() {
		return this.levelOfElectricityStorage;
	}

	private float getLevelOfGasStorage() {
		return this.levelOfGasStorage;
	}


	/*
	 * 
	 */
	public void chargingStationsInputByteStream(String filePath) throws IOException
	{
		int indexArray = 0;
		int byteData = 0;
		int stationsCounter = 0;
		char recoveredParameterCharTemp;
		try (FileInputStream reader = new FileInputStream(filePath)) {
			while((byteData = reader.read()) != -1)
			{
				recoveredParameterCharTemp = (char)byteData;
				if(recoveredParameterCharTemp == '\n')
				{
					stationsCounter++;
				}
			}
			// stationsCounter++;
			/* Reader needs a reset, so we can go to the beginning of the file again */
			reader.close();
			ChargingStation[] stations = new ChargingStation[stationsCounter++];
			/* If object was created correctly, move to obtain the information */
			StringBuilder recoverText = new StringBuilder();
			String recoveredParameterString;
			try {
				try ( /* Reset object reader to the beginning of the file */
				FileInputStream readerTemp = new FileInputStream(filePath)) {
					byteData = 0;
					while((byteData = readerTemp.read()) != -1)
					{	
						char recoveredParameterChar = (char)byteData;
						if(recoveredParameterChar == '\n')
						{
							recoveredParameterString = recoverText.toString();
							String[] recoveredParameterStrings = recoveredParameterString.split(" ");
							
							setChargingStationID(Integer.parseInt(recoveredParameterStrings[0]));
							setGpsValues(new GPSValues(Float.parseFloat(recoveredParameterStrings[1]), Float.parseFloat(recoveredParameterStrings[2])));
							setNumGasSlots(Integer.parseInt(recoveredParameterStrings[3]));
							setNumElectricSlots(Integer.parseInt(recoveredParameterStrings[4]));
							setGasOutputPerSecond(Float.parseFloat(recoveredParameterStrings[5]));
							setElectricityOutputPerSecond(Float.parseFloat(recoveredParameterStrings[6]));
							setLevelOfElectricityStorage(Float.parseFloat(recoveredParameterStrings[7]));
							setLevelOfGasStorage(Float.parseFloat(recoveredParameterStrings[8]));
							ChargingStation tempStation = new ChargingStation(getChargingStationID(), getGpsValues(), getNumGasSlots(), getNumElectricSlots(), getGasOutputPerSecond(), getElectricityOutputPerSecond(), getLevelOfElectricityStorage(), getLevelOfGasStorage());
							
							stations[indexArray] = tempStation;
							recoverText = new StringBuilder(); 
							/* Update index until the value matches the created items */
							indexArray++;
						}
						else {
							recoverText.append(recoveredParameterChar);
						}
					}
				}
				if(recoverText.length() > 0)
				{
					recoveredParameterString = recoverText.toString();
					String[] recoveredParameterStrings = recoveredParameterString.split(" ");
					
					setChargingStationID(Integer.parseInt(recoveredParameterStrings[0]));
					setGpsValues(new GPSValues(Float.parseFloat(recoveredParameterStrings[1]), Float.parseFloat(recoveredParameterStrings[2])));
					setNumGasSlots(Integer.parseInt(recoveredParameterStrings[3]));
					setNumElectricSlots(Integer.parseInt(recoveredParameterStrings[4]));
					setGasOutputPerSecond(Float.parseFloat(recoveredParameterStrings[5]));
					setElectricityOutputPerSecond(Float.parseFloat(recoveredParameterStrings[6]));
					ChargingStation tempStation = new ChargingStation(getChargingStationID(), getGpsValues(), getNumGasSlots(), getNumElectricSlots(), getGasOutputPerSecond(), getElectricityOutputPerSecond(), getLevelOfElectricityStorage(), getLevelOfGasStorage());
					
					stations[indexArray] = tempStation;
					recoverText = new StringBuilder(); 
				}
				setListStations(stations);

			} catch (Exception e) {
				System.out.println("End of File reached " + e.getMessage());
				throw new Exception("Invalid data type...");
			}
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			throw new IOException("Failed to open the file...");
		}
	}	
	public static ChargingStation[] getChargingStations(String filePath)
	{
		ByteStreamInputChargingStations objectByteStreamInput = new ByteStreamInputChargingStations();
		
		try {
			objectByteStreamInput.chargingStationsInputByteStream(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return objectByteStreamInput.getListStations();
	}
	
	public void printChargingStations()
	{
		ByteStreamInputChargingStations objectByteStreamInput = new ByteStreamInputChargingStations();
		ChargingStation[] tempChargingStations = objectByteStreamInput.getListStations();
		LocationAPI.printArray(tempChargingStations);
		
        System.out.println("Charging Station ID: " + objectByteStreamInput.getChargingStationID());
        System.out.println("Latitude: " + objectByteStreamInput.getGpsValues().getLatitude());
        System.out.println("Longitude: " + objectByteStreamInput.getGpsValues().getLongitude());
        System.out.println("Gas Slots: " + objectByteStreamInput.getNumGasSlots());
        System.out.println("Electric Slots: " + objectByteStreamInput.getNumElectricSlots());
        System.out.println("Gas Output: " + objectByteStreamInput.getGasOutputPerSecond());
        System.out.println("Electric Output: " + objectByteStreamInput.getElectricityOutputPerSecond());
        System.out.println("Electric Storage: " + objectByteStreamInput.getLevelOfElectricityStorage());
		System.out.println("Gas Storage: " + objectByteStreamInput.getLevelOfGasStorage());
		System.out.println();
	}
}
