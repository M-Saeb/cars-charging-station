package stations;

public enum EnergyState {
	solar("solar"),
	powerGrid("solar");
	
	private String energyString;
	
	EnergyState(String string) {
		this.energyString = string;
	}
	
	public String getEnergyString()
	{
		return this.energyString;
	}
}
