import api.GPSValues;
import api.LocationAPI;
import car.*;
import exceptions.InvalidGPSLatitudeException;
import exceptions.InvalidGPSLongitudeException;
import exceptions.InvalidGPSValueException;
import stations.ChargingStation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

	static {
		try {
			Files.createDirectories(Paths.get("logs"));
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Couldn't great logs folder.");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		final InputStream inputStream = Main.class.getResourceAsStream("/logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}

	}

	public static void main(String[] args) {
		// initiate logger
		Logger logger = Logger.getLogger("Main");
		ChargingStation[] stations = new ChargingStation[4];
		ChargingStation[] sortedStations = new ChargingStation[4];

		// create pool of charging stations
		try {
			stations[0] = new ChargingStation(1, new GPSValues(44, 96), 3, 10);
			stations[1] = new ChargingStation(2, new GPSValues(10, 50), 2, 5);
			stations[2] = new ChargingStation(3, new GPSValues(-50, 150), 4, 15);
			stations[3] = new ChargingStation(4, new GPSValues(-70, 44), 1, 5);
		} catch (InvalidGPSLatitudeException | InvalidGPSLongitudeException | InvalidGPSValueException e) {
			logger.severe(e.getStackTrace().toString());
			e.printStackTrace();
			return;
		}
		logger.info("Created pool of charging stations.");

		// create pool of cars
		Car[] cars = {
				new GasCar("Ford Mustang", (float) 60.9, (float) 120.0, new LocationAPI(stations),
						new GPSValues(40, 20)),
				new ElectricCar("Toyota Prius", (float) 13.0, (float) 30.0, new LocationAPI(stations),
						new GPSValues(-50, 50)),
				new GasCar("Peugeot 206", (float) 49.0, (float) 30.0, new LocationAPI(stations),
						new GPSValues(24, 140)),
				new ElectricCar("BW i5", (float) 83.9, (float) 70.0, new LocationAPI(stations), new GPSValues(80, 95)),
				new GasCar("Audi A3", (float) 48.0, (float) 90.0, new LocationAPI(stations), new GPSValues(40, 10))
		};
		logger.info("Created pool of cars.");

		// create pool of threads

		// send cars to charging stations
		boolean allCharged = false;
		while (!allCharged){
			for (Car car: cars){
				if (!car.isCharged()){
					// find a station that:
					// - provides a matching fuel type
					// - provides suitable waiting time
					
					
					// if charging is done, make car leave
				}
			}
		}

		for (ChargingStation station: stations){
			// process cars in queues, slots, ...
		}

	}
}
