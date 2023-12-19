package weather;

public enum WeatherState {
	sunny("sunny"),
	windy("windy"),
	cloudy("cloudy"),
	rainy("rainy"),
	snowying("snowying"),
	thunderstorm("thunderstorm");

	private String weatherString;
	
	WeatherState(String string) {
		this.weatherString = string;
	}
	
	public String getWeatherString()
	{
		return this.weatherString;
	}
}
