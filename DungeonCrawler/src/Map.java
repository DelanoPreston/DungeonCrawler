import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Map implements TileBasedMap {
	int[][] pathMap;
	private boolean[][] visited;
	private boolean[][] visible;
	int[][] mapKey;
	List<MapTile> wallList = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();

	public Map(int width, int height) {
		mapKey = new int[height][width];
		initializeVisible();
		// this initializes and creates the mapKey, pathMap, and the walls, and rooms
		createDungeon(50);
		// this resets and initializes the visited map;
		resetVisitedMap();
	}

	public List<MapTile> getWalls() {
		return wallList;
	}

	public void initializeVisible() {
		visible = new boolean[mapKey.length][mapKey[0].length];
		for (int y = 0; y < visible.length; y++) {
			for (int x = 0; x < visible[0].length; x++) {
				visible[y][x] = false;
			}
		}
	}

	public void setVisible(Shape s) {
		int tS = ContentBank.tileSize;
		for (int y = 0; y < visible.length; y++) {
			for (int x = 0; x < visible[0].length; x++) {
				// this gets point on the displayed map and finds its array location
				if (s.contains(new Point2D.Double((x * tS) + (tS / 2), (y * tS) + (tS / 2)))) {
					if (!visible[y][x]) {
						visible[y][x] = true;
						// this goes through the surrounding walls of any new discovered tile, and sets those walls to visible
						for (int ty = y - 1; ty <= y + 1; ty++) {
							for (int tx = x - 1; tx <= x + 1; tx++) {
								if (tx >= 0 && ty >= 0 && tx < mapKey[0].length && ty < mapKey.length) {
									if (Key.isWall(checkCell(tx, ty)))
										visible[ty][tx] = true;
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This draws the miniature map in the top right corner
	 * 
	 * @param d
	 *        - the dimensions of the panel
	 * @param g2D
	 *        - graphics
	 * @param px
	 *        - the player's x position
	 * @param py
	 *        - the player's y position
	 * @param w
	 *        - the miniature map tile width
	 * @param h
	 *        - the miniature map tile height
	 */
	public void drawMiniMap(Graphics2D g2D, Dimension d, int px, int py, int w, int h) {
		// Start location
		int mmTileSize = 6;
		int sx = d.width - (w * mmTileSize);
		int sy = 0;
		int xOffset = w / 2;
		int yOffset = h / 2;

		Color yellow = Color.YELLOW;
		Color green = Color.GREEN;
		Color gray = Color.GRAY;
		// Color darkGray = Color.DARK_GRAY;
		Color brown = new Color(187, 122, 80, 255);
		Color transparent = new Color(0, 0, 0, 0);
		// Color black = Color.BLACK;

		for (int y = py - yOffset; y < py + yOffset; y++) {
			for (int x = px - xOffset; x < px + xOffset; x++) {
				if (x >= 0 && y >= 0 && x < mapKey[0].length && y < mapKey.length) {
					if (visible[y][x] || !Key.drawMMFogOfWar) {
						// if the x an y are out of bounds it draws a transparent
						// if (x < 0 || y < 0 || x >= mapKey[0].length || y >= mapKey.length) {
						// g2D.setColor(new Color(0, 0, 0, 0));
						// } else
						if (x == px && y == py) {
							g2D.setColor(yellow);
						} else if (isCell(x, y, Key.floor)) {
							g2D.setColor(green);
						} else if (Key.isWall(checkCell(x, y))) {
							g2D.setColor(gray);
						} else if (isCell(x, y, Key.door)) {
							g2D.setColor(brown);
						} else {
							// Redundant
							g2D.setColor(transparent);
						}
					} else {
						// Redundant
						g2D.setColor(transparent);
					}
				} else {
					// Redundant
					g2D.setColor(transparent);
				}
				// does not even draw anything if its out of bounds of the mapkey
				// if (x >= 0 || y >= 0 || x < mapKey[0].length || y < mapKey.length)
				g2D.fillRect(sx + ((x - (px - xOffset)) * mmTileSize), sy + ((y - (py - yOffset)) * mmTileSize), mmTileSize, mmTileSize);
			}
		}
	}

	public void drawGameMap(Graphics2D g2D, Dimension d, Shape visible) {
		Area screen = new Area(new Rectangle(0, 0, d.height, d.width));
		screen.subtract((Area) visible);

		g2D.draw(screen);
	}

	public void drawWholeMap(Graphics2D g2D) {
		int tS = ContentBank.tileSize;
		if (Key.drawPathMap) {
			int tempVal = 0;
			for (int y = 0; y < pathMap.length; y++) {
				for (int x = 0; x < pathMap[0].length; x++) {
					if (pathMap[y][x] > tempVal)
						tempVal = pathMap[y][x];
				}
			}
			tempVal = 255 / tempVal;
			for (int y = 0; y < pathMap.length; y++) {
				for (int x = 0; x < pathMap[0].length; x++) {
					int value = 255 - (pathMap[y][x] * tempVal);

					g2D.setColor(new Color(value, value, value, 255));

					g2D.fillRect(x * tS, y * tS, tS, tS);

					if (Key.drawGrid) {
						g2D.setColor(new Color(180, 180, 180, 255));
						g2D.drawRect(x * tS, y * tS, tS, tS);
					}
				}
			}
		} else {
			for (int y = 0; y < mapKey.length; y++) {
				for (int x = 0; x < mapKey[0].length; x++) {
					g2D.setColor(new Color(110, 110, 110, 255));
					if (isCell(x, y, Key.floor)) {
						g2D.setColor(new Color(32, 127, 32, 255));
					} else if (isCell(x, y, Key.sideWall)) {
						g2D.setColor(new Color(127, 127, 127, 255));
					} else if (isCell(x, y, Key.cornerWall)) {
						g2D.setColor(new Color(137, 137, 137, 255));
					} else if (isCell(x, y, Key.door)) {
						g2D.setColor(new Color(32, 32, 32, 255));
					}
					g2D.fillRect(x * tS, y * tS, tS, tS);

					if (Key.drawGrid) {
						g2D.setColor(new Color(180, 180, 180, 255));
						g2D.drawRect(x * tS, y * tS, tS, tS);
					}
				}
			}
		}

		if (Key.drawRoomNumbers) {
			g2D.setColor(new Color(255, 255, 255, 255));
			for (int r = 0; r < rooms.size(); r++) {
				int x = (int) (rooms.get(r).getCenter().getX() * tS);
				int y = (int) (rooms.get(r).getCenter().getY() * tS);
				g2D.drawString(String.valueOf(r), x, y);
			}
		}
	}

	public void createPathMap() {
		int[][] temp = new int[mapKey.length][mapKey[0].length];

		for (int y = 0; y < temp.length; y++) {
			for (int x = 0; x < temp[0].length; x++) {
				if (isCell(x, y, Key.unused)) {
					temp[y][x] = 4;
				} else if (isCell(x, y, Key.floor)) {
					temp[y][x] = 2;
				} else if (isCell(x, y, Key.door)) {
					temp[y][x] = 1;
				} else if (Key.isWall(checkCell(x, y))) {
					temp[y][x] = calcWallCost(x, y);
					if (temp[y][x] < 5)
						temp[y][x] = 5;
				}
			}
		}

		pathMap = temp;
	}

	private int calcWallCost(int x, int y) {
		int tempCost = 0;
		// makes sure its in the bound of the array
		int startX = Math.min(Math.max(x - 2, 0), mapKey.length - 1);
		int startY = Math.min(Math.max(y - 2, 0), mapKey[0].length - 1);
		int endX = Math.min(Math.max(x + 2, 0), mapKey.length - 1);
		int endY = Math.min(Math.max(y + 2, 0), mapKey[0].length - 1);
		// the t is for temp
		for (int ty = startY; ty < endY; ty++) {
			for (int tx = startX; tx < endX; tx++) {
				if (isCell(tx, ty, Key.sideWall))
					// if (Key.isWall(checkCell(x, y)))
					tempCost++;
				if (isCell(tx, ty, Key.cornerWall))
					tempCost += 2;
				if (isCell(x, y, Key.door))
					tempCost += 5;
			}
		}
		return tempCost;
	}

	public boolean isCell(int x, int y, int cellType) {
		// try {
		return mapKey[y][x] == cellType;
		// } catch (Exception e) {
		// System.out.println(x);
		// System.out.println(y);
		// e.printStackTrace();
		// }
		// return false;
	}

	public int checkCell(int x, int y) {
		return mapKey[y][x];
	}

	public void setCell(int x, int y, int cellType) {
		mapKey[y][x] = cellType;
		if (x == 7 && y == 55 && cellType == Key.door)
			mapKey[y][x] = cellType;
	}

	public void resetVisitedMap() {
		visited = new boolean[mapKey.length][mapKey[0].length];
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				visited[i][j] = false;
			}
		}
	}

	@Override
	public int getWidthInTiles() {
		return mapKey[0].length;
	}

	@Override
	public int getHeightInTiles() {
		return mapKey.length;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}

	@Override
	public boolean blocked(int type, int x, int y) {
		if (x != 0 || x != mapKey[0].length - 1 || y != 0 || y != mapKey.length) {
			if (type == Key.pathFinderRoomCheck) {
				if (Key.isWall(checkCell(x, y)) || isCell(x, y, Key.unused))
					return true;
			} else if (type == Key.pathFinderRoomTunneler) {
				if (Key.isWall(checkCell(x, y))) {
					if (isCell(x, y, Key.cornerWall))
						return true;
					else if (x < getWidthInTiles() - 1 && isCell(x + 1, y, Key.cornerWall) || x >= 1 && isCell(x - 1, y, Key.cornerWall)
							|| y < getHeightInTiles() - 1 && isCell(x, y + 1, Key.cornerWall) || y >= 1 && isCell(x, y - 1, Key.cornerWall))
						return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public float getCost(int type, int sx, int sy, int tx, int ty) {
		double tempCost = Math.max(pathMap[ty][tx], pathMap[sy][sx]);
		if (Math.abs(sx - tx) == 1 && Math.abs(sy - ty) == 1)
			// return (float) Math.sqrt(tempCost + tempCost);
			return 2;// no diagonal right now anyway
		return (float) tempCost;
	}

	private void createDungeon(int roomNum) {
		// ************************************************************************
		// initialize the map
		// ************************************************************************
		for (int y = 0; y < mapKey.length; y++) {
			for (int x = 0; x < mapKey[0].length; x++) {
				setCell(x, y, Key.unused);
			}
		}
		// ************************************************************************
		// this creates the rooms
		// ************************************************************************
		for (int r = 0; rooms.size() < roomNum && r < 2500; r++) {
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

			int tempWidth = ContentBank.random.nextInt(startRoomWidth) + startRoomWidth;
			int tempHeight = ContentBank.random.nextInt(startRoomHeight) + startRoomHeight;
			int tempX = ContentBank.random.nextInt(getWidthInTiles() - (1 + tempWidth));
			int tempY = ContentBank.random.nextInt(getHeightInTiles() - (1 + tempHeight));
			createRoom(roomType, tempX, tempY, tempWidth, tempHeight);
		}

		// ************************************************************************
		// connect rooms - with halls, for every room
		// ************************************************************************
		for (int r = 0; r < rooms.size(); r++) {
			// -check that you can get to each other room
			for (int tr = r + 1; tr < rooms.size(); tr++) {
				// redraw the pathmap, so the pathfinder will use already existing halls and rooms
				createPathMap();
				AStarPathFinder pf = new AStarPathFinder(this, 10000, false);// 2
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
						// redraw the pathmap, so the pathfinder will use already existing halls and rooms
						createPathMap();
						AStarPathFinder tpf = new AStarPathFinder(this, (getHeightInTiles() * getWidthInTiles()) / ((rooms.size() * 2)), false);// 4
						p = tpf.findPath(Key.pathFinderRoomTunneler, sx, sy, ex, ey);
						if (p != null) {
							if (Key.showDebug && Key.showHallMapping) {
								System.out.println("from room: " + r + " to " + tr);
							}
							// follow the path to get to the room, creating a hall as you go
							for (int pathKey = 0; pathKey < p.getLength(); pathKey++) {
								if (isCell(p.getX(pathKey), p.getY(pathKey), Key.sideWall)) {
									setCell(p.getX(pathKey), p.getY(pathKey), Key.door);
								} else if (isCell(p.getX(pathKey), p.getY(pathKey), Key.unused)) {
									setCell(p.getX(pathKey), p.getY(pathKey), Key.floor);
								}
							}
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

		// ************************************************************************
		// this should wrap all the halls with walls - and removes double doors
		// this loop goes through the entire mapKey, except row 0, column 0, maxheight - 1, and maxwidth - 1
		// ************************************************************************
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

		// ************************************************************************
		// this removes awkward walls (surrounded by 3 floor tiles
		// ************************************************************************
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

		// ************************************************************************
		// this adds the walls to the walllist
		// ************************************************************************
		int tS = ContentBank.tileSize;
		for (int y = 0; y < mapKey.length; y++) {
			for (int x = 0; x < mapKey[0].length; x++) {
				if (Key.isWall(checkCell(x, y))) {// || tempKey[y][x] == 1 || tempKey[y][x] == 2) {
					wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), true));
				} else if (mapKey[y][x] == Key.door) {
					wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), false));
				}
			}
		}

		// ************************************************************************
		// for debugging purposes
		// ************************************************************************
		createPathMap();
	}

	public boolean createRoom(String roomType, int rx, int ry, int rwidth, int rheight) {
		Room tempRoom = new Room(roomType, rx, ry, rwidth, rheight);
		boolean spaceValid = true;
		// this checks if the space is valid
		for (int y = ry; y < ry + rheight; y++) {
			for (int x = rx; x < rx + rwidth; x++) {
				if (!isCell(x, y, Key.unused) && !Key.isWall(checkCell(x, y))) {// this used to be just side walls
					spaceValid = false;
				}
			}
		}
		// if the space is valid then it creates the room
		if (spaceValid) {
			for (int y = ry; y < ry + rheight; y++) {
				for (int x = rx; x < rx + rwidth; x++) {
					// this if gets the border of the room and sets them to side walls
					if (y == ry || y == ry + rheight - 1 || x == rx || x == rx + rwidth - 1) {
						// this gets the corners of the walls and sets them to corner walls
						if (y <= ry && x <= rx || y <= ry && x >= rx + rwidth - 1 || y >= ry + rheight - 1 && x <= rx || y >= ry + rheight - 1
								&& x >= rx + rwidth - 1)
							setCell(x, y, Key.cornerWall);
						// this makes sure if its a corner wall it leaves it alone
						else if (!isCell(x, y, Key.cornerWall))
							setCell(x, y, Key.sideWall);
					} else {
						// everything inside the room is floor right now, or at least initially
						setCell(x, y, Key.floor);
					}
				}
			}
			// the room was added to the map, so it is added to the list of rooms
			rooms.add(tempRoom);
			// returns true for placing the room
			return true;
		}
		// returns false because there is no space for the room
		return false;
	}

	class Room {
		public String roomType;
		public int x;
		public int y;
		public int height;
		public int width;
		// 0 is N, 1 is E, 2 is S, 3 is W
		boolean[] sideAvail;

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

		public Room(String roomType, int x, int y, int width, int height) {
			this.roomType = roomType;
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
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
