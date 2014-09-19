package Entities;

import java.io.Serializable;

import DataStructures.Location;

public class Entity implements Serializable {
	private static final long serialVersionUID = -1152523368523661488L;

	private String name;
	public Location location;

	public Entity() {
		name = "None";
		location = new Location(0, 0);
	}

	public Entity(String inName, Location inLoc) {
		name = inName;
		location = inLoc;
	}

	public String getName() {
		return name;
	}

	public void setName(String in) {
		name = in;
	}

	public Location getLoc() {
		return location;
	}

	public void addLoc(float x, float y) {
		location.addLinearMovement(x, y);
	}

	public void setLoc(Location loc) {
		location = loc;
	}
}
