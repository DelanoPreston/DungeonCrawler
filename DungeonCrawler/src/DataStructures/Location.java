package DataStructures;

import java.awt.geom.Point2D;

import Settings.Key;

public class Location {
	float x;
	float y;

	public Location() {
		this.x = 0;
		this.y = 0;
	}

	public Location(float inX, float inY) {
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

	public Location(Point2D mapLocation) {
		x = (float) mapLocation.getX();
		y = (float) mapLocation.getY();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
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

	public Point2D getPoint() {
		return new Point2D.Float(x, y);
	}

	public void addMovement(float[] inChange) {
		x += inChange[0];
		y += inChange[1];
	}

	public void addLinearMovement(float inX, float inY) {
		x += inX;
		y += inY;
	}

	public void addMovement(double speed, double rotation) {
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
	}

	public float getDistance(Location loc) {
		float a = x - loc.getX();
		float b = y - loc.getY();

		return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
	}

	@Override
	public String toString() {
		return "location: " + x + ", " + y;
	}
}
