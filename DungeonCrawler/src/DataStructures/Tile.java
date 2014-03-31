package DataStructures;

import java.awt.Rectangle;

import Settings.Key;

public class Tile {
	int key;
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

	public Tile(int key, int cost, boolean visible, Rectangle wall) {
		this.key = key;
		this.cost = cost;
		this.visible = visible;
		this.visited = false;
		this.wall = wall;
	}

	public int getKey() {
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

	public void setKey(int key) {
		this.key = key;
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
