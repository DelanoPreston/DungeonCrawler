package DataStructures;

import java.awt.Rectangle;

import Settings.Key;

public class Tile {
	ID key;
	int cost;
	boolean visible;
	boolean visited;
	Rectangle wall;

	public Tile() {
		this.key = Key.unused;
		this.cost = 0;
		this.visible = false;
		this.visited = false;
		this.wall = null;
	}

	public Tile(ID key, int cost, boolean visible, Rectangle wall) {
		this.key = key;
		this.cost = cost;
		this.visible = visible;
		this.visited = false;
		this.wall = wall;
	}

	public ID getID() {
		return key;
	}

	public int getCost() {
		return cost;
	}

	public boolean getVisible() {
		return visible;
	}

	public boolean getVisited() {
		return visited;
	}

	public Rectangle getWall() {
		return wall;
	}

	public void addCost(int i) {
		cost += i;
	}

	public void setKey(ID key) {
		this.key = key;
	}

	public boolean isKey(ID key) {
		return this.key.equals(key);
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setWall(Rectangle wall) {
		this.wall = wall;
	}
}
