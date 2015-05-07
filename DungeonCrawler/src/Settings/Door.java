package Settings;

import java.awt.geom.Line2D;

import DataStructures.Location;

public class Door {
	Line2D line;
	Location location;
	boolean open;

	public Door(Line2D inLine, Location loc) {
		line = inLine;
		location = loc;
		if (Key.random.nextInt(100) % 2 == 1)
			open = false;
		else
			open = true;
		open = false;
	}

	public boolean isDoorOpen() {
		return open;
	}

	public void openDoor() {
		open = true;
	}

	public void closeDoor() {
		open = false;
	}

	public Location getLocation() {
		return location;
	}

	public void setLine(Line2D l2d) {
		line = l2d;
	}

	public Line2D getLine() {
		return line;
	}
}
