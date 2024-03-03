package tib.ehlssim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationSetup {
	private int simulations;
	private int waveReached;
	private int lastUpgradeToBuyRange;
	private int writeRunStatisticsToFile;
	private EhlsSetup ehlsSetup;
	private FreeUtilityUpgradeSetup freeUUSetup;
	private Integer minValue;
	private Integer maxValue;
	private double avgValue;

	public SimulationSetup() {
		this.simulations = 100;
		this.waveReached = 2500;
		this.lastUpgradeToBuyRange = 200;
		this.writeRunStatisticsToFile = 10;
	}

	public void doSimulation() {
		List<Integer> runs = new ArrayList<>();
		for (int sim = 0; sim < simulations; sim++) {
			int total_skips = 0;
			int total_number_of_ehls_increase = 0;
			double current_ehls_chance = ehlsSetup.getStartEhlsChance();
			List<WaveStats> duringRunStats = new ArrayList<WaveStats>();

			for (int wave = 1; wave < waveReached; wave++) {
				double rnd1 = Math.random();

				if (isEhlsSkip(rnd1, current_ehls_chance)) {
					total_skips++;
				}

				if (current_ehls_chance <= ehlsSetup.getMaxEhlsChance()) {
					double rnd2 = Math.random();
					if (isEhlsIncreasedByFreeUp(rnd2)) {
						if (Math.random() < 0.50) { // EALS halfs the chance for EHLS
							current_ehls_chance += EhlsSetup.increasePerLevel;
							total_number_of_ehls_increase++;
						}
					}
					if ((current_ehls_chance < lastUpgradeToBuyRange)) {
						Integer amount = ehlsIncreasedByCash(Math.random(), lastUpgradeToBuyRange,
								total_number_of_ehls_increase);
						current_ehls_chance += (EhlsSetup.increasePerLevel * amount);
						total_number_of_ehls_increase++;
					}

				}
				if ((wave + 1) % 100 == 0) {
					duringRunStats.add(new WaveStats(total_skips, wave + 1, current_ehls_chance));
				}
			}
			runs.add(total_skips);
			if (sim < writeRunStatisticsToFile) {
				String runStats = duringRunStats.stream().map(x -> x.getAllStats()).collect(Collectors.joining("\n"));
				try {
					writeToFile(sim, runStats);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		IntSummaryStatistics summaryStats = runs.stream().mapToInt(n -> n).summaryStatistics();

		minValue = summaryStats.getMin();
		maxValue = summaryStats.getMax();
		avgValue = Math.round(summaryStats.getAverage());
	}

	public void setSimulations(int simulations) {
		this.simulations = simulations;
	}

	public void setWaveReached(int waveReached) {
		this.waveReached = waveReached;
	}

	public void setLastUpgradeToBuyRange(int lastUpgradeToBuyRange) {
		this.lastUpgradeToBuyRange = lastUpgradeToBuyRange;
	}

	/**
	 * Setting the value to amount of files you want from each simulation (first
	 * runs of simulation)
	 * 
	 * @param writeRunStatisticsToFile
	 */
	public void setWriteRunStatisticsToFile(int writeRunStatisticsToFile) {
		this.writeRunStatisticsToFile = writeRunStatisticsToFile;
	}

	public void setEhlsSetup(EhlsSetup ehlsSetup) {
		this.ehlsSetup = ehlsSetup;
	}

	public void setFreeUUSetup(FreeUtilityUpgradeSetup freeUUSetup) {
		this.freeUUSetup = freeUUSetup;
	}

	private boolean isEhlsSkip(double rnd, double curr) {
		return rnd <= curr;
	}

	private boolean isEhlsIncreasedByFreeUp(double rnd) {
		return rnd <= freeUUSetup.getFreeUtilityUpgradeChance();
	}

	private String toPercentage(double val) {
		return String.format("%.2f", val * 100) + "%";
	}

	private double toDouble(Integer i) {
		double result = i;
		return result;
	}

	private Integer ehlsIncreasedByCash(double rnd, int lastUpgradeToBuy, int currUpgradesNumbers) {
		if (currUpgradesNumbers < lastUpgradeToBuy) {
			double percentageReached = currUpgradesNumbers / lastUpgradeToBuy;
			if (percentageReached <= CashTable.first.getDistribution()) {
				return calcCashTable(CashTable.first, rnd);
			} else if (percentageReached <= CashTable.second.getDistribution()) {
				return calcCashTable(CashTable.second, rnd);
			} else if (percentageReached <= CashTable.third.getDistribution()) {
				return calcCashTable(CashTable.third, rnd);
			} else if (percentageReached <= CashTable.fourth.getDistribution()) {
				return calcCashTable(CashTable.fourth, rnd);
			} else if (percentageReached <= CashTable.fifth.getDistribution()) {
				return calcCashTable(CashTable.fifth, rnd);
			} else {
				return calcCashTable(CashTable.sixth, rnd);
			}
		}
		return 0;
	}

	private Integer calcCashTable(CashTable ct, double rnd) {
		return rnd < ct.getChance() ? ct.getAmount() : 0;
	}

	public void writeToFile(int fileNumber, String str) throws IOException {
		String fileName = "waveStats " + fileNumber + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.write(str);
		writer.write("\n\n");

		writer.close();
	}

	public void writeToConsole() {
		System.out.println("Simulating: " + simulations + " runs with " + waveReached + " waves");
		System.out.println("Starting EHLS at " + toPercentage(ehlsSetup.getStartEhlsChance()) + ", max EHLS at "
				+ toPercentage(ehlsSetup.getMaxEhlsChance()) + " and "
				+ toPercentage(freeUUSetup.getFreeUtilityUpgradeChance()) + " Free Utility Upgrade");
		System.out.println("Min: " + minValue + "\tMax: " + maxValue + "\tAvg: " + avgValue + "\t// Amount of skips");
		System.out.println("Min: " + toPercentage(toDouble(minValue) / waveReached) + "\tMax: "
				+ toPercentage(toDouble(maxValue) / waveReached) + "\tAvg: " + toPercentage(avgValue / waveReached)
				+ "\t// Percentage of Skips");
		System.out.println("-----------------------------------------------------------------------------------");
	}

}
