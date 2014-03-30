import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class MapCreator {
	protected Map map;
	public static List<MapTile> wallList = new ArrayList<>();
	int tileHeight;
	int tileWidth;
	protected boolean showPathMap = false;
	int[][] pathMap;

	public int getWidth() {
		return tileWidth * ContentBank.tileSize;
	}

	public int getHeight() {
		return tileHeight * ContentBank.tileSize;
	}

	public MapCreator(int height, int width) {
		tileWidth = width;
		tileHeight = height;
		createMap();
	}

	public Map createMap() {
		int tileSize = ContentBank.tileSize;

		CreateDungeon cd = new CreateDungeon(50);
		// dungeon cd = new dungeon();
		// cd.createDungeon(64, 64, 150);
		// System.out.println(cd.showDungeon());
		pathMap = cd.getMapKey();

		for (int y = 0; y < pathMap.length; y++) {
			for (int x = 0; x < pathMap[0].length; x++) {
				if (pathMap[y][x] == Key.sideWall) {// || tempKey[y][x] == 1 || tempKey[y][x] == 2) {
					wallList.add(new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), true));
				} else if (pathMap[y][x] == Key.door) {
					wallList.add(new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), false));
				}
			}
		}

		return map;
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

		public CreateDungeon(int roomNum) {
			// initialize the map
			for (int y = 0; y < mapKey.length; y++) {
				for (int x = 0; x < mapKey[0].length; x++) {
					setCell(x, y, Key.unused);
				}
			}
			// this creates the rooms
			for (int r = 0; rooms.size() < roomNum && r < 1000; r++) {
				// random value that decides default room size
				int randVal = ContentBank.random.nextInt(100);
				String roomType = "none";
				int startRoomWidth = 0;
				int startRoomHeight = 0;
				// these if cases, determine if the room is Large, medium, or small
				if (randVal < 40) {
					roomType = "Large";
					startRoomWidth = 10;
					startRoomHeight = 10;
				} else if (randVal < 70) {
					roomType = "Medium";
					startRoomWidth = 8;
					startRoomHeight = 8;
				} else {
					roomType = "Small";
					startRoomWidth = 6;
					startRoomHeight = 6;
				}
				// gets random room

				tempWidth = ContentBank.random.nextInt(startRoomWidth) + startRoomWidth;
				tempHeight = ContentBank.random.nextInt(startRoomHeight) + startRoomHeight;
				tempX = ContentBank.random.nextInt(tileWidth - (1 + tempWidth));
				tempY = ContentBank.random.nextInt(tileHeight - (1 + tempHeight));
				createRoom(tempX, tempY, tempWidth, tempHeight);
			}

			// initialize the map so the pathfinder will work
			map = new Map(mapKey);
			// connect rooms - halls
			// for every room
			for (int r = 0; r < rooms.size(); r++) {
				// -check that you can get to each other room
				for (int tr = r + 1; tr < rooms.size(); tr++) {
					AStarPathFinder pf = new AStarPathFinder(map, (tileHeight * tileWidth) / (rooms.size() * 4), false);// 2
					if (r != tr) {
						// start and end coords
						int sx = (int) rooms.get(r).getCenter().getX();
						int sy = (int) rooms.get(r).getCenter().getY();
						int ex = (int) rooms.get(tr).getCenter().getX();
						int ey = (int) rooms.get(tr).getCenter().getY();
						Path pCheck = pf.findPath(Key.pathFinderRoomCheck, sx, sy, ex, ey);
						// if there was no path from room r to room tr
						if (pCheck == null) {
							// tunnel
							Path p = null;
							AStarPathFinder tpf = new AStarPathFinder(map, rooms.size(), false);// 4
							p = tpf.findPath(Key.pathFinderRoomTunneler, sx, sy, ex, ey);
							if (p != null) {
								if (Key.showDebug && Key.showHallMapping) {
									System.out.println("from room: " + r + " to " + tr);
								}
								// follow the path to get to the room, creating a hall as you go
								for (int pathKey = 0; pathKey < p.getLength(); pathKey++) {
									if (map.isCell(p.getX(pathKey), p.getY(pathKey), Key.sideWall)) {
										map.setCell(p.getX(pathKey), p.getY(pathKey), Key.door);
									} else if (map.isCell(p.getX(pathKey), p.getY(pathKey), Key.unused)) {
										map.setCell(p.getX(pathKey), p.getY(pathKey), Key.floor);
									}
								}
								// redraw the pathmap, so the pathfinder will use already existing halls
								map.createPathMap();
							}
						} else {
							// awesome
							if (Key.showDebug && Key.showHallMapping) {
								System.out.println("path from room: " + r + " to " + tr + " is valid");
							}
						}
					}
				}
			}
			// this should wrap all the halls with walls - and removes double doors
			// this loop goes through the entire mapKey, except row 0, column 0, maxheight - 1, and maxwidth - 1
			for (int y = 1; y < mapKey.length - 1; y++) {
				for (int x = 1; x < mapKey[0].length - 1; x++) {
					if (isCell(x, y, Key.unused)) {
						if (isCell(x + 1, y, Key.floor) || isCell(x - 1, y, Key.floor) || isCell(x, y + 1, Key.floor) || isCell(x, y - 1, Key.floor)) {
							setCell(x, y, Key.sideWall);
						}

					} else if (isCell(x, y, Key.door)) {
						// this removes double doors
						if (isCell(x + 1, y, Key.door) || isCell(x - 1, y, Key.door) || isCell(x, y + 1, Key.door) || isCell(x, y - 1, Key.door)) {
							if (isCell(x + 1, y, Key.floor) && isCell(x - 1, y, Key.floor) || isCell(x, y + 1, Key.floor) && isCell(x, y - 1, Key.floor)) {
								setCell(x, y, Key.sideWall);
							} else {
								setCell(x, y, Key.floor);
							}
						}
					}
				}
			}

			// this removes awkward walls (surrounded by 3 floor tiles
			for (int y = 1; y < mapKey.length - 1; y++) {
				for (int x = 1; x < mapKey[0].length - 1; x++) {
					if (isCell(x, y, Key.sideWall)) {
						if (isCell(x + 1, y, Key.floor) && isCell(x - 1, y, Key.floor)) {
							if (isCell(x, y + 1, Key.floor) || isCell(x, y - 1, Key.floor))
								setCell(x, y, Key.floor);
						} else if (isCell(x, y + 1, Key.floor) && isCell(x, y - 1, Key.floor)) {
							if (isCell(x + 1, y, Key.floor) || isCell(x - 1, y, Key.floor))
								setCell(x, y, Key.floor);
						}
					}
				}
			}
			// reinitialize the map, with the new hallways
			map = new Map(mapKey);
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

		public void setRoomTiles(int rx, int ry, int rwidth, int rheight) {
			// System.out.println("working on room");
			for (int y = ry; y < ry + rheight; y++) {
				for (int x = rx; x < rx + rwidth; x++) {
					// this if gets the border of the room and sets them to side walls
					if (y == ry || y == ry + rheight - 1 || x == rx || x == rx + rwidth - 1) {
						// this gets the corners of the walls and sets them to corner walls
						if (y <= ry + 1 && x <= rx + 1 || y <= ry + 1 && x >= rx + rwidth - 2 || y >= ry + rheight - 2 && x <= rx + 1 || y >= ry + rheight - 2
								&& x >= rx + rwidth - 2)
							setCell(x, y, Key.cornerWall);
						else
							setCell(x, y, Key.sideWall);
					} else {
						// everything inside the room is floor right now, or at least initially
						setCell(x, y, Key.floor);
					}
				}
			}
		}

		public boolean checkSpace(int rx, int ry, int rwidth, int rheight) {
			for (int y = ry; y < ry + rheight; y++) {
				for (int x = rx; x < rx + rwidth; x++) {
					if (!isCell(x, y, Key.unused) && !isCell(x, y, Key.sideWall)) {
						return false;
					}
				}
			}
			return true;
		}

		public boolean isCell(int x, int y, int cellType) {
			return mapKey[y][x] == cellType;
		}

		public int checkCell(int x, int y) {
			return mapKey[y][x];
		}

		public void setCell(int x, int y, int cellType) {
			mapKey[y][x] = cellType;
			if (x == 7 && y == 55 && cellType == Key.door)
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
		MapCreator mapRef;

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
