package stations;

import java.util.Random;

import weather.WeatherState;

public class EnergySource
{
	private WeatherState energyState;
	
	public void setPowerSource()
	{
		
	}
	public WeatherState getRandomWeather()
	{
		Random objRandom = new Random();
		WeatherState[] varWeatherState = WeatherState.values();
		this.energyState = varWeatherState[objRandom.nextInt(varWeatherState.length)];
		return this.energyState;
	}
}