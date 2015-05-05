package Entities.Monsters;

import java.util.HashMap;

import DataStructures.Location;
import Entities.Entity;
import Entities.MoveableEntity;

public class Monster extends MoveableEntity {
	private static final long serialVersionUID = 6025468896032999254L;
	private Entity target;
	private HashMap<String, Double> stats;

	public Monster(String name, Location loc, HashMap<String, Double> sta){
		super(name, loc);
		stats = sta;
	}
}
