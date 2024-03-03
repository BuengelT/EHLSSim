package tib.ehlssim;

public class EhlsSetup {
	public static final int maxAmount = 699;
	public static final double increasePerLevel = 0.0005;

	private double labValue;
	private double moduleValue;
	private int currentWorkshopAmount;

	public EhlsSetup() {
		this.labValue = 0.00;
		this.moduleValue = 0.00;
		this.currentWorkshopAmount = 0;
	}

	/**
	 * 
	 * @param labValue
	 * @param moduleValue
	 * @param currentWorkshopAmount
	 */
	public EhlsSetup(double labValue, double moduleValue, int currentWorkshopAmount) {
		this.labValue = labValue;
		this.moduleValue = moduleValue;
		this.currentWorkshopAmount = currentWorkshopAmount;
	}

	public double getIncreasePerLevel() {
		return increasePerLevel;
	}

	public double getStartEhlsChance() {
		return increasePerLevel + (currentWorkshopAmount * increasePerLevel) + moduleValue + labValue;
	}

	public double getMaxEhlsChance() {
		return increasePerLevel + (maxAmount * increasePerLevel) + moduleValue + labValue;
	}

	public void setLabValue(double labValue) {
		this.labValue = labValue;
	}

	public void setModuleValue(double moduleValue) {
		this.moduleValue = moduleValue;
	}

	public void setCurrentWorkshopAmount(int currentWorkshopAmount) {
		this.currentWorkshopAmount = currentWorkshopAmount;
	}

}
