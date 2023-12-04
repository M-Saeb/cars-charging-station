package byteStream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyStore.TrustedCertificateEntry;

import api.GPSValues;
import api.LocationAPI;
import stations.ChargingStation;

/**
 * 
 */
public class byteStreamInput 
{
	private int chargingStationID;
	private GPSValues gpsValues;
	private int numGasSlots;
	private int numElectricSlots;
	private float gasOutputPerSecondoutputPerSecond;
	private float electricityOutputPerSecond;
	
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

	public float getGasOutputPerSecondoutputPerSecond() {
		return gasOutputPerSecondoutputPerSecond;
	}

	private void setGasOutputPerSecondoutputPerSecond(float gasOutputPerSecondoutputPerSecond) {
		this.gasOutputPerSecondoutputPerSecond = gasOutputPerSecondoutputPerSecond;
	}

	public float getElectricityOutputPerSecond() {
		return electricityOutputPerSecond;
	}

	private void setElectricityOutputPerSecond(float electricityOutputPerSecond) {
		this.electricityOutputPerSecond = electricityOutputPerSecond;
	}
	/*
	 * 
	 */
	public void chargingStationsInputByteStream(String filePath) throws IOException
	{
		int indexArray = 0;
		try {
			FileInputStream reader = new FileInputStream(filePath);
			int stationsCounter = (int) reader.getChannel().size();
			/* Reader needs a reset, so we can go to the beginning of the file again */
			reader.close();
			ChargingStation[] stations = new ChargingStation[stationsCounter];
			/* If object was created correctly, move to obtain the information */
			try {
				/* Reset object reader to the beginning of the file */
				reader.getChannel().position(0);
				String recorveredParameterString;
				StringBuilder recoverText = new StringBuilder();
				int byteData = 0;
				while((byteData = reader.read()) != -1)
				{	
					char recorveredParameterChar = (char)byteData;
					if(recorveredParameterChar == '\n')
					{
						recorveredParameterString = recoverText.toString();
						String[] recorveredParameterStrings = recorveredParameterString.split(" ");
						
						setChargingStationID(Integer.parseInt(recorveredParameterStrings[0]));
						setGpsValues(new GPSValues(Float.parseFloat(recorveredParameterStrings[1]), Float.parseFloat(recorveredParameterStrings[2])));
						setNumGasSlots(Integer.parseInt(recorveredParameterStrings[3]));
						setNumElectricSlots(Integer.parseInt(recorveredParameterStrings[4]));
						setGasOutputPerSecondoutputPerSecond(Integer.parseInt(recorveredParameterStrings[5]));
						setElectricityOutputPerSecond(Integer.parseInt(recorveredParameterStrings[6]));
						
					}
					else {
						recoverText.append(recorveredParameterChar);
					}
					
					ChargingStation tempStation = new ChargingStation(getChargingStationID(), getGpsValues(), getNumGasSlots(), getNumElectricSlots(), getGasOutputPerSecondoutputPerSecond(), getElectricityOutputPerSecond());
					
					stations[indexArray] = tempStation;
					/* Update index until the value matches the created items */
					indexArray = (indexArray < stationsCounter)? (indexArray++): (indexArray);
					setListStations(stations);
				}
			} catch (Exception e) {
				System.out.println("An error occurred: " + e.getMessage());
				throw new IOException("Failed to open the file...");
			}
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			throw new IOException("Failed to open the file...");
		}
	}	
	public static ChargingStation[] getChargingStations(String filePath)
	{
		byteStreamInput objectByteStreamInput = new byteStreamInput();
		
		try {
			objectByteStreamInput.chargingStationsInputByteStream(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objectByteStreamInput.getListStations();
	}
	
	public void printChargingStations()
	{
		byteStreamInput objectByteStreamInput = new byteStreamInput();
		ChargingStation[] tempChargingStations = objectByteStreamInput.getListStations();
		LocationAPI.printArray(tempChargingStations);
		
        System.out.println("Charging Station ID: " + objectByteStreamInput.getChargingStationID());
        System.out.println("Latitude: " + objectByteStreamInput.getGpsValues().getLatitude());
        System.out.println("Longitude: " + objectByteStreamInput.getGpsValues().getLongitude());
        System.out.println("Gas Slots: " + objectByteStreamInput.getNumGasSlots());
        System.out.println("Electric Slots: " + objectByteStreamInput.getNumElectricSlots());
        System.out.println("Gas Output: " + objectByteStreamInput.getGasOutputPerSecondoutputPerSecond());
        System.out.println("Electric Output: " + objectByteStreamInput.getElectricityOutputPerSecond());
        System.out.println();
	}
}
