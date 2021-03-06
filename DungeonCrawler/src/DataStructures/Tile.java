package DataStructures;

import Settings.Key;

public class Tile {
	ID key;
	int cost;
	boolean visible;
	boolean visited;

	public Tile() {
		this.key = Key.unused;
		this.cost = 0;
		this.visible = false;
		this.visited = false;
	}

	public Tile(ID key, int cost, boolean visible){
		this.key = key;
		this.cost = cost;
		this.visible = visible;
		this.visited = false;
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
}
