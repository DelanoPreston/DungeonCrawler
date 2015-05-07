package Stats;

import java.util.HashMap;

public class Stat {
	private HashMap<String, Double> stats = new HashMap<>();

	public Stat() {
	}

	// TODO this does not account for adding 2 of the same stats (might be a
	// problem
	public boolean addStat(String s, double val) {
		if (!hasStat(s)) {
			stats.put(s, val);
			return true;
		} else
			return false;
	}

	public boolean hasStat(String s) {
		return stats.containsKey(s);
	}

	public double getStatVal(String s) {
		if (hasStat(s))
			return stats.get(s);
		return 0.0;
	}

	public HashMap<String, Double> getStats() {
		return stats;
	}
}
