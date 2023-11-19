package chargeStation;

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
			new GasCar("Ford Mustang", 60.9, 120.0, new LocationAPI()),
			new ElectricCar("Toyota Prius", 13.0, 30.0, new LocationAPI()),
			new GasCar("Peugeot 206", 49.0, 30.0, new LocationAPI()),
			new ElectricCar("BW i5", 83.9, 70.0, new LocationAPI()),
			new GasCar("Audi A3", 48.0, 90.0, new LocationAPI())
		};
		logger.info("Created pool of cars.");
		
		// create pool of charging stations

		// create pool of threads
		
		// send cars to charging stations

	}
}
