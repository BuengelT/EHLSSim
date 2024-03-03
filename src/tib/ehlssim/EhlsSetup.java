package tib.ehlssim;

public class EhlsSetup {
	public static final int MAX_AMOUNT = 699;
	public static final double INCREASE_PER_LEVEL = 0.0005;

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

	public double getStartEhlsChance() {
		return INCREASE_PER_LEVEL + (currentWorkshopAmount * INCREASE_PER_LEVEL) + moduleValue + labValue;
	}

	public double getMaxEhlsChance() {
		return INCREASE_PER_LEVEL + (MAX_AMOUNT * INCREASE_PER_LEVEL) + moduleValue + labValue;
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
