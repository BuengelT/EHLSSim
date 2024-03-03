package tib.ehlssim;

/**
 * Simulating Cash Buys via Distribution-Table
 * 
 * The Distribution is mapped on a range that can be set. The range should be in
 * relation to the WS-value where you can buy the last EHLS upgrade (until it is
 * to expensive), compared to the starting EHLS WS-value. For example starting
 * at WS 320 and the last possible buy is at WS 520, that would mean the range
 * is 200.
 * 
 * Via the distribution you can manage different buying amount for each part. If
 * the distribution is as follows |15%<30%<50%<65%<80%<100%| for a range of 200,
 * that would mean the first 15% of WS-Amount is 30 WS-upgrades, 30% is 60
 * WS-upgrades, 50% is 100 WS-upgrades, and so on.
 * 
 * With chance and amount you can regulate how many WS-upgrades per wave you can
 * get via cash. If the chance is set to 1.0 (100%) you would get a buy every
 * round. 0.5 (50%) equals every 2nd wave, 0.083 (8.33%) equals every 12th wave
 * The amount will determine how many buys you get per wave.
 * 
 * In the case below: If the amount of WS EHLS is below 30 from starting EHLS
 * (Upgrade 320-350), you would get 2 extra EHLS with cash every wave until 30
 * buys (total ~60 upgrades in 30 waves). From range 31-60 you would get 1 extra
 * buy with cash every 2nd wave until 60 buys (total ~15 upgrades in 30 waves).
 * From range 61-100 you would get 1 extra buy with cash every 3rd wave until
 * 100 buys (total ~ 13 upgrades in 40 waves). Increasing the amount to 3, would
 * mean for the first 30 waves you get 3 buys per round.
 * 
 * In this way it is guaranteed that EHLS Upgrades from FreeUtilityUpgrade is
 * taken into account too Setting the amount to 0 means not getting any upgrades
 * in that area
 */
enum CashTable {
	first(0.15, 1.0, 1), // 15% each wave
	second(0.3, 0.5, 1), // 30% each 2nd wave
	third(0.5, 0.3333, 1), // 50% each 3rd wave
	fourth(0.65, 0.1667, 1), // 75% each 6th wave
	fifth(0.80, 0.0833, 1), // 80% each 12th wave
	sixth(1.0, 0.0333, 1) // until 100% each 30th wave
	;

	private double distribution;
	private double chance;
	private Integer amount;

	CashTable(double distribution, double chance, Integer amount) {
		this.distribution = distribution;
		this.chance = chance;
		this.amount = amount;
	}

	public double getDistribution() {
		return this.distribution;
	}

	public double getChance() {
		return this.chance;
	}

	public Integer getAmount() {
		return this.amount;
	}
}