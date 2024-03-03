package tib.ehlssim;

public class WaveStats {
	private int skips;
	private int wave;
	private double ehlsAtWave;

	public int getSkips() {
		return skips;
	}

	public int getWave() {
		return wave;
	}

	public double getEhlsAtWave() {
		return ehlsAtWave;
	}

	public WaveStats(int skips, int wave, double ehlsAtWave) {
		super();
		this.skips = skips;
		this.wave = wave;
		this.ehlsAtWave = ehlsAtWave;
	}

	public String getAllStats() {
		String delimiter = "\t";
		return "" + skips + delimiter + wave + delimiter + String.format("%.4f", ehlsAtWave);
	}
}
