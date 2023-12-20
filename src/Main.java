import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.AbstractMap.SimpleEntry;

import api.LocationAPI;
import byteStream.ByteStreamHandler;
import byteStream.ByteStreamInputCars;
import byteStream.ByteStreamInputChargingStations;
import car.Car;
import stations.ChargingStation;
import stations.EnergySource;

class StationsFilter implements Filter {
		public boolean isLoggable(LogRecord logRecord) {
			return logRecord.getLoggerName().startsWith(ChargingStation.class.getSimpleName());
		}
	}

class EnergySourceFilter implements Filter {
		public boolean isLoggable(LogRecord logRecord) {
			return logRecord.getLoggerName().contains(EnergySource.class.getSimpleName());
		}
	}


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
		/*
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
		*/

		// Import logging configurations
		try {
			final InputStream inputStream = Main.class.getResourceAsStream("/logging.properties");
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe("Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
		Logger.getLogger("").addHandler(new ByteStreamHandler("logs/byteStreamLog.log"));

		/*
		 * Add a FileHandler and associate it with a Filter for every type of
		 * log file that we need.
		 */

		// get today's date for log filename
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		String todaysDate = dtf.format(localDate);

		Formatter ourFormatter = Logger.getLogger("").getHandlers()[0].getFormatter();
		// Create a list of (filename, filter) pairs
		List<Entry<String,Filter>> loggersConfig = new ArrayList<>();
		loggersConfig.add(new SimpleEntry<String, Filter>("system", null));
		loggersConfig.add(new SimpleEntry<String, Filter>("stations", new StationsFilter()));
		loggersConfig.add(new SimpleEntry<String, Filter>("energy source", new EnergySourceFilter()));

		for (Entry<String,Filter> loggerConfig: loggersConfig){
			try {
				String filename = String.format("%s/%s - %s.log", logsPath.toString(), todaysDate, loggerConfig.getKey());
				FileHandler fileHandler = Utils.generateFileHandler(filename, ourFormatter, loggerConfig.getValue());
				Logger.getLogger("").addHandler(fileHandler);
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) {
		// initiate logger
		Logger logger = Logger.getLogger("system");
		ChargingStation[] sortedStations = new ChargingStation[4];
		
		// Create pool of stations
		ChargingStation[] stations = ByteStreamInputChargingStations.getChargingStations("objectLists/chargingStationsList.txt");
		logger.info("---------------------------------------");
		logger.info("Created pool of charging stations.");
		logger.info("---------------------------------------");
		LocationAPI locationAPI = new LocationAPI(stations);
		
		// Create pool of cars	
		Car[] cars = ByteStreamInputCars.getCars("objectLists/carsList.txt", locationAPI);
		logger.info("---------------------------------------");
		logger.info("Created pool of cars.");
		logger.info("---------------------------------------");

		// create pool of threads
		logger.info("---------------------------------------");
		logger.info("Starting threads.");
		logger.info("---------------------------------------");

		for (ChargingStation station: stations){
			Thread thread = new Thread(station);
			thread.start();
		}
		
		for(int i = 0; i < cars.length; i++)
		{
			Random random = new Random();
        	int delayTime = random.nextInt(3) + 1;
			try{
				Thread.sleep(delayTime * 1000);
			} catch (Exception e){
				e.printStackTrace();
			}
			Car car = cars[i];
			logger.info(String.format("--- Deploying next car: %s ---", car.toString()));
			Thread carThread = new Thread(car);
			carThread.start();
		}
		logger.info("-------All cars are deployed.-------");
	}
}