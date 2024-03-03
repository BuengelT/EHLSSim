package tib.ehlssim;

public class FreeUtilityUpgradeSetup {
	double cardValue;
	double moduleValue;
	double workshopValue;

	public FreeUtilityUpgradeSetup() {
		this.cardValue = 0.0;
		this.moduleValue = 0.0;
		this.workshopValue = 0.0;
	}

	/**
	 * 
	 * @param cardValue
	 * @param moduleValue
	 * @param workshopValue
	 */
	public FreeUtilityUpgradeSetup(double cardValue, double moduleValue, double workshopValue) {
		this.cardValue = cardValue;
		this.moduleValue = moduleValue;
		this.workshopValue = workshopValue;
	}

	public void setCardValue(double cardValue) {
		this.cardValue = cardValue;
	}

	public void setModuleValue(double moduleValue) {
		this.moduleValue = moduleValue;
	}

	public void setWorkshopValue(double workshopValue) {
		this.workshopValue = workshopValue;
	}

	public double getFreeUtilityUpgradeChance() {
		return workshopValue + moduleValue + cardValue;
	}
}
