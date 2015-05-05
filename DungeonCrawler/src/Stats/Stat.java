package Stats;

public class Stat {
	private String type;
	private double val;

	public Stat(String type, double val) {
		this.type = type;
		this.val = val;
	}

	public String getName() {
		return type;
	}

	public double getStat() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}
}
