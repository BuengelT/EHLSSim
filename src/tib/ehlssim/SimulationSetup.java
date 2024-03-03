package tib.ehlssim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationSetup {
	private int simulations;
	private int waveReached;
	private int lastUpgradeToBuyRange;
	private int writeRunStatisticsToFile;
	private EhlsSetup ehlsSetup;
	private FreeUtilityUpgradeSetup freeUUSetup;
	private int minValue;
	private int maxValue;
	private double avgValue;

	public SimulationSetup() {
		this.simulations = 100;
		this.waveReached = 2500;
		this.lastUpgradeToBuyRange = 200;
		this.writeRunStatisticsToFile = 0;
	}

	public void doSimulation() {
		List<Integer> runs = new ArrayList<>();
		for (int sim = 0; sim < simulations; sim++) {
			int totalSkips = 0;
			int totalNumberOfEhlsIncrease = 0;
			double currentEhlsChance = ehlsSetup.getStartEhlsChance();
			List<WaveStats> duringRunStats = new ArrayList<WaveStats>();

			for (int wave = 1; wave < waveReached; wave++) {
				double rnd1 = Math.random();

				if (isEhlsSkip(rnd1, currentEhlsChance)) {
					totalSkips++;
				}

				if (currentEhlsChance <= ehlsSetup.getMaxEhlsChance()) {
					double rnd2 = Math.random();
					if (isEhlsIncreasedByFreeUp(rnd2)) {
						if (Math.random() < 0.50) { // EALS halfs the chance for EHLS
							currentEhlsChance += EhlsSetup.INCREASE_PER_LEVEL;
							totalNumberOfEhlsIncrease++;
						}
					}
					if ((totalNumberOfEhlsIncrease < lastUpgradeToBuyRange)) {
						int amount = ehlsIncreasedByCash(Math.random(), lastUpgradeToBuyRange,
								totalNumberOfEhlsIncrease);
						currentEhlsChance += (EhlsSetup.INCREASE_PER_LEVEL * amount);
						totalNumberOfEhlsIncrease++;
					}

				}
				if ((wave + 1) % 100 == 0) {
					duringRunStats.add(new WaveStats(totalSkips, wave + 1, currentEhlsChance));
				}
			}
			runs.add(totalSkips);
			if (sim < writeRunStatisticsToFile) {
				String runStats = duringRunStats.stream().map(x -> x.getAllStats()).collect(Collectors.joining("\n"));
				try {
					writeToFile("waveStats" + sim, runStats);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		IntSummaryStatistics summaryStats = runs.stream().mapToInt(n -> n).summaryStatistics();

		minValue = summaryStats.getMin();
		maxValue = summaryStats.getMax();
		avgValue = Math.round(summaryStats.getAverage());
		
		writeToConsole();
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

	private int ehlsIncreasedByCash(double rnd, int lastUpgradeToBuy, int currUpgradesNumbers) {
		double percentageReached = (double) currUpgradesNumbers / (double) lastUpgradeToBuy;
		for (CashTable ct : CashTable.values()) {
			if (percentageReached <= ct.getDistribution()) {
				return calcCashTable(ct, rnd);
			}
		}
		return 0;
	}

	private int calcCashTable(CashTable ct, double rnd) {
		return rnd < ct.getChance() ? ct.getAmount() : 0;
	}

	public void writeToFile(String fileName, String fileContent) throws IOException {
		String file = fileName + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		writer.write(fileContent);
		writer.write("\n\n");

		writer.close();
	}

	public void writeToConsole() {
		System.out.println("Simulating EHLS " + simulations + " times for " + waveReached + " waves");
		System.out.println("Min: " + minValue + "\tMax: " + maxValue + "\tAvg: " + String.format("%.0f", avgValue) + "\t| " + "Start-EHLS: "
				+ toPercentage(ehlsSetup.getStartEhlsChance()) + "; Max-EHLS: "
				+ toPercentage(ehlsSetup.getMaxEhlsChance()));
		System.out.println("Min: " + toPercentage((double) minValue / waveReached) + "\tMax: "
				+ toPercentage((double) maxValue / waveReached) + "\tAvg: " + toPercentage(avgValue / waveReached)
				+ "\t| " + "Free Utility Upgrade: " + toPercentage(freeUUSetup.getFreeUtilityUpgradeChance()));
		
	}

}
