package DataStructures;

import Settings.Key;

public class Location {
	double x;
	double y;

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getTileX() {
		double temp = 0;
		if (x % Key.tileSize > Key.tileSize / 2)
			temp = x - Key.tileSize / 2;

		temp = x / Key.tileSize;

		return (int) temp;
	}

	public int getTileY() {
		double temp = 0;
		if (y % Key.tileSize > Key.tileSize / 2)
			temp = y - Key.tileSize / 2;

		temp = y / Key.tileSize;

		return (int) temp;
	}

	public void setLocationAtTile() {
		x = getTileX() * Key.tileSize + (Key.tileSize / 2);
		y = getTileY() * Key.tileSize + (Key.tileSize / 2);
		int i = 0;
		if (i == 0) {

		}
	}

	public Location() {
		this.x = 0;
		this.y = 0;
	}

	public Location(double inX, double inY) {
		x = inX;
		y = inY;
	}

	public Location(int inX, int inY) {
		x = inX;
		y = inY;
	}

	public Location(Location mapLocation) {
		x = mapLocation.getX();
		y = mapLocation.getY();
	}

	public void addMovement(double[] inChange) {
		x -= inChange[0];
		y -= inChange[1];
	}

	public void addMovement(float inX, float inY) {
		x -= inX;
		y -= inY;
	}

	public void addMovement(double speed, double rotation) {
		x += speed * Math.cosh(rotation);
		y += speed * Math.sinh(rotation);
	}
}
