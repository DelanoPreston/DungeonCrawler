package Entities;

import java.awt.Graphics2D;
import java.io.Serializable;

import DataStructures.Location;
import Player.Gear;

public class Entity implements Serializable {
	private static final long serialVersionUID = -1152523368523661488L;
	protected Gear gear = new Gear();
	private String name;
	public Location location;
	protected int health = 100;

	public Entity() {
		name = "None";
		location = new Location(0, 0);
	}

	public Entity(String inName, Location inLoc) {
		name = inName;
		location = inLoc;
	}

	public void update() {

	}

	public void draw(Graphics2D g2D) {

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

	public int getHealth() {
		return health;
	}
	
	

 	public void damageEntity(int amount) {
		health -= amount;
	}
}
