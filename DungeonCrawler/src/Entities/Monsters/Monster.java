package Entities.Monsters;

import DataStructures.Location;
import Entities.Entity;
import Entities.MoveableEntity;
import Stats.Stats;

public class Monster extends MoveableEntity {
	private static final long serialVersionUID = 6025468896032999254L;
	private Entity target;
	private Stats stats;

	public Monster(String name, Location loc, Stats sta){
		super(name, loc);
		stats = sta;
	}
}
