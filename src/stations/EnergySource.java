package stations;

import java.util.logging.Logger;

public class EnergySource
{
	EnergyState energyState;
	ChargingStation station;
	Logger logger;

	public EnergySource(ChargingStation station)
	{
		this.energyState = EnergyState.powerGrid;
		this.station = station;
		this.logger = Logger.getLogger(this.toString());
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
	
	public void setSolar()
	{
		this.energyState = EnergyState.solar;
	}
	
	public void setPowerGrid()
	{
		this.energyState = EnergyState.powerGrid;
	}
}