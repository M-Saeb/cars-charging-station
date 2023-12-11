import api.GPSValues;
import api.LocationAPI;
import car.*;
import exceptions.ChargingStationNotFoundException;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;
import exceptions.InvalidGPSValueException;
import stations.ChargingStation;
import byteStream.ByteStreamHandler;
import byteStream.ByteStreamInputCars;
import byteStream.ByteStreamInputChargingStations;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

	static {
		Path logsPath = Paths.get("logs");
		// Create logs dir
		try {
			Files.createDirectories(logsPath);
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Couldn't create logs folder.");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}

		// Delete old logs
		try {
			for(File file: logsPath.toFile().listFiles()){ 
				if (!file.isDirectory()){
					file.delete();
				}
			}
		} catch (Exception e) {
			Logger.getAnonymousLogger().severe("Couldn't delete old logs.");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}

		// Import logging configurations
		try {
			final InputStream inputStream = Main.class.getResourceAsStream("/logging.properties");
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		Logger.getLogger("").addHandler(new ByteStreamHandler("logs/byteStreamLog.log"));
	}

	public static void main(String[] args) {
		// initiate logger
		Logger logger = Logger.getLogger("Main");
		ChargingStation[] sortedStations = new ChargingStation[4];
		
		ChargingStation[] stations = ByteStreamInputChargingStations.getChargingStations("objectLists/chargingStationsList.txt");
		logger.info("Created pool of charging stations.");
		// create pool of cars
		LocationAPI locationAPI = new LocationAPI(stations);
		
		Car[] cars = ByteStreamInputCars.getCars("objectLists/carsList.txt", locationAPI);
		
		logger.info("Created pool of cars.");

		// create pool of threads
		logger.info("Starting threads.");
		for (ChargingStation station : stations) {
			station.start();
		}
		// Wait for stations to start
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		for (Car car : cars) {
			car.start();
		}

		

		// wait for each car to finish
		for (Car car : cars) {
			try {
				car.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		logger.info("all car threads finished.");

		for (ChargingStation station : stations) {
			station.setDone(true);
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}

		// let's see how things went
		int numReallyCharged = 0;
		for (Car car: cars){
			if (car.getCurrentCapacity() != car.getTankCapacity()){
				logger.warning(car.toString() + " didn't get charged.");
			} else {
				++numReallyCharged;
			}
		}
		logger.info(String.format("Managed to charge %d/%d cars.", numReallyCharged, cars.length));
	}
}
