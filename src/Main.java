import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import api.LocationAPI;
import byteStream.ByteStreamHandler;
import byteStream.ByteStreamInputCars;
import byteStream.ByteStreamInputChargingStations;
import car.Car;
import stations.ChargingStation;
import utils.Utils;

/* Folder creation for logs */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

	static {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateFormatted = currentDate.format(dateFormat);
		
		
		Path currentPath = Paths.get("logs");
		String folderString = currentPath.toAbsolutePath().toString();
		File folder = new File(folderString + "/" + dateFormatted);
		System.out.println("Current absolute path is: " + folder);
		if(!folder.exists())
		{
			folder.mkdir();
			System.out.println("Folder created");
		}
		else
		{
			System.out.println("Folder already exists");
		}
		
		Path logsPath = Paths.get("logs" + "/" + dateFormatted);
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

		/*
		 * Add a FileHandler and associate it with a Filter for every type of
		 * log file that we need.
		 */

		// get today's date for log filename
		String todaysDate = Utils.getTodaysDate();

		Formatter ourFormatter = Utils.getGlobalFormatter();

		/*
		 * Add logging file handler for solar and power grid energy sources,
		 * as well as for the whole system.
		 */
		try {		
			Logger.getLogger("").addHandler(
				Utils.generateFileHandler(
					String.format("%s/%s - %s.log", logsPath.toString(), todaysDate, "system"),
					ourFormatter
					)
			);
			Logger.getLogger("Solar").addHandler(
				Utils.generateFileHandler(
					String.format("%s/%s - %s.log", logsPath.toString(), todaysDate, "solar"),
					ourFormatter
					)
			);
			Logger.getLogger("PowerGrid").addHandler(
				Utils.generateFileHandler(
					String.format("%s/%s - %s.log", logsPath.toString(), todaysDate, "power grid"),
					ourFormatter
					)
			);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// initiate logger
		Logger logger = Logger.getLogger("system");
		LocationAPI locationAPI = new LocationAPI(null);
		Car[] allCars = ByteStreamInputCars.getCars("objectLists/carsList.txt", locationAPI); 
		ArrayList<Car> selectedCars = new ArrayList<>();
		ChargingStation[] allChargingStations = ByteStreamInputChargingStations.getChargingStations("objectLists/chargingStationsList.txt");
		ArrayList<ChargingStation> selectedStations = new ArrayList<>();
		

        Scanner scanner = new Scanner(System.in);
        int choice;
		int exitChoice;
		boolean simulationChoice = false;

        do {
            // display menu options
            System.out.println("Menu:");
            System.out.println("1. Select Cars In Simulation");
            System.out.println("2. Select Stations In Simulation");
            System.out.println("3. Begin Simulation");
			System.out.println("4. Display logs");
            System.out.println("5. Exit");
			exitChoice = 5;

            // get user input
            System.out.print("Enter your choice (1-5): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
					int carChoice;
					do {
						System.out.println("Select cars to be included in simulation");
						System.out.println("0. Go Back");
						// Get a list of cars that aren't already selected for the user
						ArrayList<Car> unselectedCars = new ArrayList<>();
						// Add all cars
						unselectedCars.addAll(Arrays.asList(allCars));
						// Remove ones that have been selected
						for (Car car : selectedCars) {
							unselectedCars.remove(car);
						}
						// Present choices
						for (int i = 0; i < unselectedCars.size(); i++) {
							System.out.println(String.format(
								"%d. %s %s with tank capacity %f and current capacity %f",
								i + 1,
								unselectedCars.get(i).getClass().getSimpleName(),
								unselectedCars.get(i).getCarNumber(),
								unselectedCars.get(i).getTankCapacity(),
								unselectedCars.get(i).getCurrentCapacity()));
						}
						if (unselectedCars.size() == 0){
							System.out.println("All cars have already been selected");
						}
						System.out.printf("Enter choice: ");
						carChoice = scanner.nextInt();
						Utils.clearTerminal();
						if (carChoice == 0){
							break;
						} else if (carChoice > unselectedCars.size()){
							System.out.println("Invalid choice. Try again.");
						} else {
							selectedCars.add(unselectedCars.get(carChoice - 1));
							System.out.println("Car was added. Number of selected cars: " + selectedCars.size());
						}
					} while (carChoice != 0);
                    break;
                case 2:
				int stationChoice;
				do {
					System.out.println("Select charging stations to be included in simulation");
					System.out.println("0. Go Back");
					// Get a list of stations that aren't already selected for the user
					ArrayList<ChargingStation> unselectedStations = new ArrayList<>();
					// Add all stations
					unselectedStations.addAll(Arrays.asList(allChargingStations));
					// Remove ones that have been selected
					for (ChargingStation station : selectedStations) {
						unselectedStations.remove(station);
					}
					// Present choices
					for (int i = 0; i < unselectedStations.size(); i++) {
						System.out.println(String.format(
							"%d. Charging Station with %d gas and %d electric slots, gas output: %f, electricity output: %f",
							i + 1,
							unselectedStations.get(i).getAvailableGasSlots(),
							unselectedStations.get(i).getAvailableElectricSlots(),
							unselectedStations.get(i).getGasOutputPerSecond(),
							unselectedStations.get(i).getElectricityOutputPerSecond()));
					}
					if (unselectedStations.size() == 0){
						System.out.println("All stations have already been selected");
					}
					System.out.printf("Enter choice: ");
					stationChoice = scanner.nextInt();
					Utils.clearTerminal();
					if (stationChoice == 0){
						break;
					} else if (stationChoice > unselectedStations.size()){
						System.out.println("Invalid choice. Try again.");
					} else {
						selectedStations.add(unselectedStations.get(stationChoice - 1));
						System.out.println("Station was added. Number of selected stations: " + selectedStations.size());
					}
				} while (stationChoice != 0);
				break;
                case 3:
                    System.out.println("Beginning simulation...");
					simulationChoice = true;
                    break;
                case 4:
                    System.out.println("Select desired day:");
					String directoryPath = "logs";

					// Create a File object for the directory
					File directory = new File(directoryPath);
			
					// Get all folders within the directory
					File[] folders = directory.listFiles(File::isDirectory);
			
					int folderChoice;
					do {
						System.out.println("0. Go Back");
						// Loop through the folders and print their names
						for (int i = 0; i < folders.length; i++) {
							System.out.println(i + 1 + ". " + folders[i].getName());
						}
						System.out.printf("Enter choice: ");
						folderChoice = scanner.nextInt();
						Utils.clearTerminal();
						if (folderChoice == 0){
							break;
						} else if (folderChoice - 1 > folders.length){
							System.out.println("Invalid choice. Try again.");
						} else {
							// Check if Desktop is supported (available on some platforms, e.g., Windows)
							if (Desktop.isDesktopSupported()) {
								// Get the Desktop instance
								Desktop desktop = Desktop.getDesktop();

								try {
									// Open the folder with the default file manager
									desktop.open(folders[folderChoice - 1]);
								} catch (IOException e) {
									// Handle the exception if the folder cannot be opened
									e.printStackTrace();
								}
							} else {
								System.out.println("Desktop is not supported on this platform.");
							}
						}
					} while (folderChoice != 0);
                    break;
					
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and " + exitChoice);
            }

            System.out.println(); // add a newline for better readability

        } while (choice != exitChoice && simulationChoice != true); // continue the loop until the user chooses to exit

        scanner.close(); // close the scanner to prevent resource leak

		if (!simulationChoice){
			return;
		}

		// Create pool of stations
		ChargingStation[] stations = selectedStations.toArray(new ChargingStation[selectedStations.size()]);
		logger.info("---------------------------------------");
		logger.info("Created pool of charging stations.");
		logger.info("---------------------------------------");
		
		// Update location API
		locationAPI.setChargingStations(stations);
		
		// Create pool of cars	
		Car[] cars = selectedCars.toArray(new Car[selectedCars.size()]);
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