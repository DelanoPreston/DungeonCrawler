package Item;

import java.util.HashMap;

import Emuns.Quality;
import Stats.Stat;

public class Weapon extends Item {
	private static final long serialVersionUID = -6139307402634254929L;
	private Quality quality;
	private double condition;
	private HashMap<String, Double> stats = new HashMap<>();

	public Weapon(String name, Quality qual, double cond) {
		super(name);
		quality = qual;
		condition = cond;
	}

	public double getCondition() {
		return condition;
	}

	public Quality getQuality() {
		return quality;
	}
	
	public String getWeaponType(){
		return "Axe";
		//TODO - create something that will return the string name of the weapon type
	}
	
	public double getStat(String s) {
		if (stats.containsKey(s)) {
			return stats.get(s);
		} else {
			return 0.0;
		}
	}
}