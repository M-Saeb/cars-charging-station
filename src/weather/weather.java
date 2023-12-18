package weather;

import java.util.Random;

public class weather {
	public static WeatherState getRandomWeather()
	{
        Random random = new Random();
        WeatherState[] conditions = WeatherState.values();
        return conditions[random.nextInt(conditions.length)];
	}
}
