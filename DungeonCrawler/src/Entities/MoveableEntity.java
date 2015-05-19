package Entities;

import DataStructures.Location;
//this class will have the pathfinding stuffs TODO
public class MoveableEntity extends Entity {
	private static final long serialVersionUID = 7134108231669813902L;

	protected float movement;

	public MoveableEntity(String name, Location loc) {
		super(name, loc);
	}

	public MoveableEntity() {
	}

	public void update() {
		// nothing for now
	}
}
