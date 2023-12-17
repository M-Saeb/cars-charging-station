package stations;

import weather.WeatherState;

public class EnergySource
{
	private EnergyState energyState;
	
	public void setPowerSource(WeatherState varWeatherState)
	{
		if(varWeatherState.ordinal() > WeatherState.cloudy.ordinal())
		{
			this.energyState = EnergyState.solar;
		}
		else 
		{
			this.energyState = EnergyState.powerGrid;
		}
	}
}