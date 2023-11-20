import API.LocationAPI;
import Car.*;
import java.util.logging.Logger;


public class Main {
	
	  static {
	      System.setProperty("java.util.logging.SimpleFormatter.format",
	              "[%1$tF %1$tT] [%4$-7s] %5$s %n");

	  }
	 
	public static void main(String[] args) {
		// initiate logger
		
		Logger logger = Logger.getLogger("Main");
		
		// create pool of cars
		
		Car[] cars = {
			new GasCar("Ford Mustang", (float) 60.9, (float) 120.0, new LocationAPI(), new float[40][20]),
			new ElectricCar("Toyota Prius", (float) 13.0, (float) 30.0, new LocationAPI(), new float[10][70]),
			new GasCar("Peugeot 206", (float) 49.0, (float) 30.0, new LocationAPI(), new float[44][170]),
			new ElectricCar("BW i5", (float) 83.9, (float) 70.0, new LocationAPI(), new float[-40][56]),
			new GasCar("Audi A3", (float) 48.0, (float) 90.0, new LocationAPI(), new float[40][25])
		};
		logger.info("Created pool of cars.");
		
		// create pool of charging stations

		// create pool of threads
		
		// send cars to charging stations

	}
}
