package Entities;

import DataStructures.Location;
import Map.Map;
import Settings.Key;
import Settings.Vision;

//this class will have the pathfinding stuffs TODO
public class MoveableEntity extends Entity {
	private static final long serialVersionUID = 7134108231669813902L;

	protected Vision vision;
	protected float movement;

	public MoveableEntity(String name, Location loc) {
		super(name, loc);
	}

	public MoveableEntity(String name, Location loc, Map mapRef) {
		super(name, loc);
		vision = new Vision(mapRef, Key.rayCastResolution, Key.rayCastingDistance, this);
	}

	public MoveableEntity() {
	}

	public void update() {
		// nothing for now
	}

	public Vision getVision() {
		return vision;
	}
}
