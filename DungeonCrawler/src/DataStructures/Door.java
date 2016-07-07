package DataStructures;

import java.awt.geom.Line2D;

import Settings.Key;

public class Door {
	ID id;
	Line2D line;
	Location location;

	public Door(ID i, Line2D inLine, Location loc) {
		id = i;
		line = inLine;
		location = loc;
	}

	public boolean isDoorOpen() {
		if (id == Key.doorClosed)
			return false;
		else if (id == Key.doorOpened)
			return true;
		return true;
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
	
	public ID getID(){
		return id;
	}
	
	public void setID(ID i) {
		id = i;
	}
}
