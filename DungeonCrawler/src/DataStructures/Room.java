package DataStructures;

import java.awt.geom.Point2D;

import Settings.Key;

public class Room {
	public String roomType;
	public int x;
	public int y;
	public int height;
	public int width;
	public int numOfDoors = 0;
	public int maxDoors = 0;

	// 0 is N, 1 is E, 2 is S, 3 is W
	// boolean[] sideAvail;

	public int getRoomX1() {
		return x;
	}

	public int getRoomY1() {
		return y;
	}

	public int getRoomX2() {
		return x + width-1;
	}

	public int getRoomY2() {
		return y + height-1;
	}

	public int getFloorX1() {
		return x + 1;
	}

	public int getFloorY1() {
		return y + 1;
	}

	public int getFloorX2() {
		return x + width - 2;
	}

	public int getFloorY2() {
		return y + height - 2;
	}
	
	public int getMaxDoor(){
		return maxDoors;
	}
	
	public Point2D getCenter() {
		int x1 = width / 2 + x;
		int y1 = height / 2 + y;
		// System.out.println("\t\t" + x1 + ", " + y1);
		return new Point2D.Double(x1, y1);
	}

	public boolean containsCoords(int inX, int inY) {
		if (x <= inX && inX <= x + width && y <= inY && inY <= y + height)
			return true;
		return false;
	}

	public Point2D getWindowLocation() {
		int x1 = (width / 2 + x) * Key.tileSize;
		int y1 = (height / 2 + y) * Key.tileSize;
		// System.out.println("\t\t" + x1 + ", " + y1);
		return new Point2D.Double(x1, y1);
	}

	public Room(String roomType, int x, int y, int width, int height) {
		this.roomType = roomType;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.maxDoors = Key.random.nextInt(2)+2;
	}

	public boolean spaceAvailable(int side, Room toBePlacedRoom, int[][] mapKey) {
		for (int y = toBePlacedRoom.y; y < toBePlacedRoom.y
				+ toBePlacedRoom.height; y++) {
			for (int x = toBePlacedRoom.x; x < toBePlacedRoom.x
					+ toBePlacedRoom.width; x++) {
				if (mapKey[y][x] == 1 || mapKey[y][x] == 2)
					return false;
			}
		}
		return true;
	}

	public Point2D locForDoor(int side) {
		return new Point2D.Double(1, 1);
	}

	public void addDoor() {
		numOfDoors += 1;
	}

	public int getDoors() {
		return numOfDoors;
	}

	public void setDoors(int num) {
		numOfDoors = num;
	}
}