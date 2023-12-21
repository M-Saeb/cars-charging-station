package stations;

public enum EnergyState {
	solar("solar"),
	powerGrid("powerGrid");
	
	private String energyString;
	
	EnergyState(String string) {
		this.energyString = string;
	}
	
	public String getEnergyString()
	{
		return this.energyString;
	}
}
