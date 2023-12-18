package weather;

import java.util.Random;

public class weather {
	private WeatherState weatherState;
	
	public weather()
	{
		this.weatherState = WeatherState.sunny;
	}
	
	public WeatherState getWeatherValue()
	{
		return this.weatherState;
	}
	public String getWeather()
	{
		return this.weatherState.getWeatherString();
	}
	public WeatherState getRandomWeather()
	{
		Random objRandom = new Random();
		WeatherState[] varWeatherState = WeatherState.values();
		this.weatherState = varWeatherState[objRandom.nextInt(varWeatherState.length)];
		return this.weatherState;
	}
}
