package tib.ehlssim;

public class Main {
	static int SIMULATIONS = 10000;
	static int WAVE_REACHED = 2500;

	static double FREE_UTILITY_CARD = 0.1; // 10% == 0.1
	static double FREE_UTILITY_MODULE = 0.08; // 8% == 0.08
	static double FREE_UTILITY_WS = 0.495; // 49.5% == 0.495

	static double EHLS_LAB = 0.02; // 2% == 0.02
	static double EHLS_MODULE = 0.08; // 8% == 0.08
	static int EHLS_AMOUNT_WS = 315; // Current value of Workshop

	public static void main(String[] args) {
		SimulationSetup setup = new SimulationSetup();
		setup.setLastUpgradeToBuyRange(200);
		setup.setWriteRunStatisticsToFile(0);
		setup.setEhlsSetup(new EhlsSetup(EHLS_LAB, EHLS_MODULE, EHLS_AMOUNT_WS));
		setup.setFreeUUSetup(new FreeUtilityUpgradeSetup(FREE_UTILITY_CARD, FREE_UTILITY_MODULE, FREE_UTILITY_WS));
		setup.setSimulations(SIMULATIONS);
		setup.setWaveReached(WAVE_REACHED);
		setup.doSimulation();
		setup.writeToConsole();

		setup.setEhlsSetup(new EhlsSetup(EHLS_LAB, EHLS_MODULE, 400));
		setup.setFreeUUSetup(new FreeUtilityUpgradeSetup(0.1, 0.08, 0.495));
		setup.doSimulation();
		setup.writeToConsole();
/*
		setup.setWaveReached(1000);
		setup.setEhlsSetup(new EhlsSetup(0.02, 0.08, 100));
		setup.setFreeUUSetup(new FreeUtilityUpgradeSetup(0.1, 0.08, 0.495));
		setup.doSimulation();
		setup.writeToConsole();

		setup.setEhlsSetup(new EhlsSetup(0.02, 0.04, 400));
		setup.setFreeUUSetup(new FreeUtilityUpgradeSetup(0.1, 0.08, 0.495));
		setup.doSimulation();
		setup.writeToConsole();
		*/
	}

}
