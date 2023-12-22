package stations;

import java.util.logging.Logger;


public class EnergySource
{
	EnergyState energyState;
	ChargingStation station;
	Logger solarLogger;
	Logger powerGridLogger;

	public EnergySource(ChargingStation station)
	{
		this.energyState = EnergyState.powerGrid;
		this.station = station;

		this.powerGridLogger = Logger.getLogger("PowerGrid");
		this.solarLogger = Logger.getLogger("Solar");
	}

	@Override
	public String toString() {
		return String.format("%s - %s", this.station.toString(), this.getClass().getSimpleName());
	}

	public EnergyState getEnergyValue()
	{
		return this.energyState;
	}
	
	public String getEnergyString()
	{
		return this.energyState.getEnergyString();
	}
	
	public void setSolar(String reason)
	{
		this.energyState = EnergyState.solar;
		this.solarLogger.info(String.format(
			"Power source of %s set to solar because of %s",
			this.station.toString(),
			reason));
	}
	
	public void setPowerGrid(String reason)
	{
		this.energyState = EnergyState.powerGrid;
		this.powerGridLogger.info(String.format(
			"Power source of %s set to power grid because of %s",
			this.station.toString(),
			reason));
	}
}