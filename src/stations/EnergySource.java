package stations;

public class EnergySource
{
	EnergyState energyState;
	
	public EnergySource()
	{
		this.energyState = EnergyState.powerGrid;
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