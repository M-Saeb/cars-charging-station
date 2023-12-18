package stations;

import java.util.Random;

import weather.WeatherState;

public class EnergySource
{
	private WeatherState energyState;
	
	public void EnergySource()
	{
		this.energyState = WeatherState.sunny;
	}
	public WeatherState getWeatherValue()
	{
		return this.energyState;
	}
	public String getWeather()
	{
		return this.energyState.getWeatherString();
	}
	public WeatherState getRandomWeather()
	{
		Random objRandom = new Random();
		WeatherState[] varWeatherState = WeatherState.values();
		this.energyState = varWeatherState[objRandom.nextInt(varWeatherState.length)];
		return this.energyState;
	}
}