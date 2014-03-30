import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class Map {
	public static List<MapTile> wallList = new ArrayList<>();
	int tileHeight;
	int tileWidth;
	protected MapTile[][] map;

	int unused = 0;
	int floor = 1;
	int wall = 4;
	int door = 12;
	int stone = 20;

	public int getWidth() {
		return tileWidth * ContentBank.tileSize;
	}

	public int getHeight() {
		return tileHeight * ContentBank.tileSize;
	}

	public Map(int height, int width) {
		tileWidth = width;
		tileHeight = height;
		createMap();
	}

	public void paint(Graphics g) {
		g.setColor(new Color(155, 112, 105, 255));
		// g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < wallList.size(); i++) {
			if (wallList.get(i).solid)
				g.setColor(new Color(127, 127, 127, 255));
			else
				g.setColor(new Color(32, 127, 32, 255));

			g.fillRect(wallList.get(i).getX(), wallList.get(i).getY(), wallList.get(i).getSize(), wallList.get(i).getSize());

			g.setColor(new Color(180, 180, 180, 255));
			g.drawRect(wallList.get(i).getX(), wallList.get(i).getY(), wallList.get(i).getSize(), wallList.get(i).getSize());
		}
	}

	public void createMap() {
		int tileSize = ContentBank.tileSize;

		CreateDungeon cd = new CreateDungeon(50);
		// dungeon cd = new dungeon();
		// cd.createDungeon(64, 64, 150);
		// System.out.println(cd.showDungeon());
		int[][] tempKey = cd.getMapKey();

		for (int y = 0; y < tempKey.length; y++) {
			for (int x = 0; x < tempKey[0].length; x++) {
				if (tempKey[y][x] == wall) {// || tempKey[y][x] == 1 || tempKey[y][x] == 2) {
					wallList.add(new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), true));
				} else if (tempKey[y][x] == door) {
					wallList.add(new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), false));
				}
			}
		}

		// map = new MapTile[height][width];
		// for (int y = 0; y < height; y++) {
		// for (int x = 0; x < width; x++) {
		// if (x <= 0 || x >= width - 1 || y <= 0 || y >= height - 1) {
		// MapTile temp = new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), true);
		// wallList.add(temp);
		// map[y][x] = temp;
		// }
		//
		// if (x >= 10 && x <= 20 && y >= 10 && y <= 20 && 11 < x && x > 19) {
		// MapTile temp = new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), true);
		// wallList.add(temp);
		// map[y][x] = temp;
		// }
		// }
		// }
	}

	public enum RoomType {
		START, HALL, BEDROOM, ARMORY, THRONE, BATHROOM, BOSS, END;
	}

	class CreateDungeon {

		List<Room> rooms = new ArrayList<>();
		protected int[][] mapKey = new int[tileHeight][tileWidth];
		Room tempRoom = null;
		int tempX = 0;
		int tempY = 0;
		int tempWidth = 0;
		int tempHeight = 0;
		boolean[] side = new boolean[4];

		public int[][] getMapKey() {
			return mapKey;
		}

		/**
		 * 
		 * @param corner
		 *        0 is top left, 1 is top right, 2 is bottom right, 3 is bottom left
		 */
		public CreateDungeon(int roomNum) {

			for (boolean b : side)
				b = true;

			// initialize the map
			for (int y = 0; y < mapKey.length; y++) {
				for (int x = 0; x < mapKey[0].length; x++) {
					setCell(x, y, unused);
				}
			}

			// tempRoom = new Room(RoomType.START, tileWidth / 2, tileHeight / 2, 8, 8, side);
			// rooms.add(tempRoom);
			// setRoomKey(tempRoom);

			// this creates the rooms
			for (int r = 0; rooms.size() < roomNum && r < 1000; r++) {
				// System.out.println("try to make room");
				// gets random room
				// Room roomToExpand = rooms.get(ContentBank.random.nextInt(rooms.size()));
				// int tempSide = ContentBank.random.nextInt(3);
				tempWidth = ContentBank.random.nextInt(8) + 8;
				tempHeight = ContentBank.random.nextInt(8) + 8;
				tempX = ContentBank.random.nextInt(tileWidth - (1 + tempWidth));
				tempY = ContentBank.random.nextInt(tileHeight - (1 + tempHeight));
				createRoom(tempX, tempY, tempWidth, tempHeight);
				// if () {
				// // good
				// } else {
				// r--;
				// }

			}
			// System.out.println("Rooms Created: " + rooms.size());

			// this makes doors
			// for (int r = 0; r < rooms.size(); r++) {
			// // the valid wall range holds the actual longest wall range, the temp one is the real time holder until its verified as the longest wall range
			// List<Point2D> validWallRange = new ArrayList<>();
			// List<Point2D> tempWallRange = new ArrayList<>();
			//
			// // check left side
			// if (rooms.get(r).x >= 2) {
			// tempX = rooms.get(r).x;
			// // set yVal to the rooms Y + 1 so it starts below the corner and it goes until before the corner below
			// for (int yVal = rooms.get(r).getFloorY1(); yVal <= rooms.get(r).getFloorY2(); yVal++) {
			// if (isCell(tempX - 1, yVal, floor)) {
			// // -1 to get the wall, inbetween the 2 floors
			// tempWallRange.add(new Point2D.Double(tempX, yVal));
			// if (tempWallRange.size() > validWallRange.size()) {
			// validWallRange = tempWallRange;
			// }
			// } else {
			// tempWallRange = new ArrayList<>();
			// }
			// if (tempWallRange.size() > validWallRange.size()) {
			// validWallRange = tempWallRange;
			// }
			// }
			// if (tempWallRange.size() >= 3 && validWallRange == null || validWallRange.size() > 3) {
			// System.out.println(r);
			// System.out.println("add wall from left: " + rooms.get(r).getCenter());
			// addDoorToWall(validWallRange);
			// }
			// tempWallRange = new ArrayList<>();
			// validWallRange = new ArrayList<>();
			// }
			// // check top
			// if (rooms.get(r).y >= 2) {
			// tempY = rooms.get(r).y;
			// // set yVal to the rooms Y + 1 so it starts below the corner and it goes until before the corner below
			// for (int xVal = rooms.get(r).getFloorX1(); xVal <= rooms.get(r).getFloorX2(); xVal++) {
			// if (isCell(xVal, tempY - 1, floor)) {
			// // -1 to get the wall, inbetween the 2 floors
			// tempWallRange.add(new Point2D.Double(xVal, tempY));
			//
			// if (tempWallRange.size() > validWallRange.size()) {
			// validWallRange = tempWallRange;
			// }
			// } else {
			// tempWallRange = new ArrayList<>();
			// }
			// if (tempWallRange.size() > validWallRange.size()) {
			// validWallRange = tempWallRange;
			// }
			// }
			// if (tempWallRange.size() >= 3 && validWallRange == null || validWallRange.size() > 3) {
			// System.out.println("add wall to top: " + rooms.get(r).getCenter());
			// addDoorToWall(validWallRange);
			// }
			// tempWallRange = new ArrayList<>();
			// validWallRange = new ArrayList<>();
			// }
			// }
		}

		public boolean createRoom(int rx, int ry, int rwidth, int rheight) {
			Room tempRoom = new Room(rx, ry, rwidth, rheight);
			if (checkSpace(rx, ry, rwidth, rheight)) {
				setRoomTiles(rx, ry, rwidth, rheight);
				rooms.add(tempRoom);
				return true;
			}
			return false;
		}

		public void addDoorToWall(List<Point2D> wallList) {
			// this selects a random spot in the wall 1 space away from either side
			if (wallList.size() > 3) {
				int tempDoorLoc = ContentBank.random.nextInt(wallList.size() - 2) + 1;
				int x = (int) wallList.get(tempDoorLoc).getX();
				int y = (int) wallList.get(tempDoorLoc).getY();
				if (isCell(x, y, wall)) {
					setCell(x, y, door);
					System.out.println("door at :" + x + ", " + y);
				} else {
					System.out.println("problem");
				}
			}
		}

		public void setRoomTiles(int rx, int ry, int rwidth, int rheight) {
			// System.out.println("working on room");
			for (int y = ry; y < ry + rheight; y++) {
				for (int x = rx; x < rx + rwidth; x++) {
					if (y == ry || y == ry + rheight - 1 || x == rx || x == rx + rwidth - 1) {
						setCell(x, y, wall);
					} else {
						setCell(x, y, floor);
					}
					if (x == 13 && y == 23)
						mapKey[y][x] = 1;
				}
			}
		}

		public boolean checkSpace(int rx, int ry, int rwidth, int rheight) {
			for (int y = ry; y < ry + rheight; y++) {
				for (int x = rx; x < rx + rwidth; x++) {
					if (!isCell(x, y, unused) && !isCell(x, y, wall)) {
						return false;
					}
				}
			}
			return true;
		}

		public boolean isCell(int x, int y, int cellType) {
			try {
				return mapKey[y][x] == cellType;
			} catch (Exception e) {
				System.out.println(x);
				System.out.println(y);
				e.printStackTrace();
			}
			return false;
		}

		public int checkCell(int x, int y) {
			return mapKey[y][x];
		}

		public void setCell(int x, int y, int cellType) {
			mapKey[y][x] = cellType;
		}

	}

	class Room {
		RoomType rType;
		public int x;
		public int y;
		public int height;
		public int width;
		// 0 is N, 1 is E, 2 is S, 3 is W
		boolean[] sideAvail;
		Map mapRef;

		public int getRoomX1() {
			return x;
		}

		public int getRoomY1() {
			return y;
		}

		public int getRoomX2() {
			return x + width;
		}

		public int getRoomY2() {
			return y + height;
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

		public Point2D getCenter() {
			int x1 = width / 2 + x;
			int y1 = height / 2 + y;
			// System.out.println("\t\t" + x1 + ", " + y1);
			return new Point2D.Double(x1, y1);
		}

		public Room(int x, int y, int height, int width) {
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
		}

		public Room(RoomType rType, int x, int y, int height, int width, boolean[] sideAvail) {
			this.rType = rType;
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.sideAvail = sideAvail;
		}

		public boolean spaceAvailable(int side, Room toBePlacedRoom, int[][] mapKey) {
			for (int y = toBePlacedRoom.y; y < toBePlacedRoom.y + toBePlacedRoom.height; y++) {
				for (int x = toBePlacedRoom.x; x < toBePlacedRoom.x + toBePlacedRoom.width; x++) {
					if (mapKey[y][x] == 1 || mapKey[y][x] == 2)
						return false;
				}
			}
			return true;
		}

		public Point2D locForDoor(int side) {

			return new Point2D.Double(1, 1);
		}
	}
}
