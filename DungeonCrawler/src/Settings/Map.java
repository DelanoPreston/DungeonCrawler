package Settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import DataStructures.Path;
import DataStructures.Room;
import DataStructures.Tile;
import Pathfinding.AStarPathFinder;
import Pathfinding.TileBasedMap;

public class Map implements TileBasedMap {
	Tile[][] map;
	List<MapTile> wallList = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();

	public Map(int width, int height) {

		// mapKey = new int[height][width];
		initializeTiles(width, height);
		// this initializes and creates the mapKey, pathMap, and the walls, and
		// rooms
		createDungeon(Key.numOfRooms);
		// this resets and initializes the visited map;
		resetVisitedMap();
	}

	public List<MapTile> getWalls() {
		return wallList;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Room getRoom(int i) {
		if (i <= rooms.size() - 1)
			return rooms.get(i);
		else
			return null;
	}

	public void resetVisited() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {

				map[x][y].setVisited(false);
			}
		}
	}

	public void initializeTiles(int width, int height) {
		map = new Tile[width][height];
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				map[x][y] = new Tile();
			}
		}
	}

	public void setVisible(Shape s) {
		int tS = Key.tileSize;
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {

				// this gets point on the displayed map and finds its array
				// location
				if (s.contains((x * tS) + (tS / 2), (y * tS) + (tS / 2))) {
					if (!map[x][y].getVisible()) {
						map[x][y].setVisible(true);
						// this goes through the surrounding walls of any new
						// discovered tile, and sets those walls to visible
						for (int tx = x - 1; tx <= x + 1; tx++) {
							for (int ty = y - 1; ty <= y + 1; ty++) {

								if (tx >= 0 && ty >= 0 && tx < getWidthInTiles() && ty < getHeightInTiles()) {
									if (Key.isWall(checkCell(tx, ty)))
										map[tx][ty].setVisible(true);
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
	 *            - the dimensions of the panel
	 * @param g2D
	 *            - graphics
	 * @param px
	 *            - the player's x position
	 * @param py
	 *            - the player's y position
	 * @param w
	 *            - the miniature map tile width
	 * @param h
	 *            - the miniature map tile height
	 */
	public void drawMiniMap(Graphics g2D, Dimension d, int px, int py, int w, int h) {
		// Start location
		int mmTileSize = 6;
		int sx = d.width - (w * mmTileSize);
		int sy = 0;
		int xOffset = w / 2;
		int yOffset = h / 2;

		Color yellow = Color.yellow;
		Color green = Color.green;
		Color gray = Color.gray;
		// Color darkGray = Color.DARK_GRAY;
		Color brown = new Color(187, 122, 80, 255);
		Color transparent = new Color(0, 0, 0, 0);
		// Color black = Color.BLACK;

		for (int x = px - xOffset; x < px + xOffset; x++) {
			for (int y = py - yOffset; y < py + yOffset; y++) {
				if (x >= 0 && y >= 0 && x < getWidthInTiles() && y < getHeightInTiles()) {
					if (map[x][y].getVisible() || !Key.drawMMFogOfWar) {
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
				// does not even draw anything if its out of bounds of the
				// mapkey
				// g2D.fillRect(sx + ((x - (px - xOffset)) * mmTileSize), sy +
				// ((y - (py - yOffset)) * mmTileSize), mmTileSize, mmTileSize);
				g2D.fillRect(sx + ((x - (px - xOffset)) * mmTileSize), sy + ((y - (py - yOffset)) * mmTileSize), mmTileSize, mmTileSize);
			}
		}
	}

	// public void drawGameMap(Graphics2D g2D, Dimension d, Shape visible) {
	// Area screen = new Area(new Rectangle(0, 0, d.height, d.width));
	// screen.subtract((Area) visible);
	//
	// g2D.draw(screen);
	// }

	public void drawWholeMap(Graphics g2D) {
		int tS = Key.tileSize;
		if (Key.drawPathMap) {
			int tempVal = 0;
			for (int x = 0; x < getWidthInTiles(); x++) {
				for (int y = 0; y < getHeightInTiles(); y++) {

					if (map[x][y].getCost() > tempVal)
						map[x][y].setCost(tempVal);
				}
			}
			tempVal = 255 / tempVal;
			for (int x = 0; x < getWidthInTiles(); x++) {
				for (int y = 0; y < getHeightInTiles(); y++) {

					int value = 255 - (map[x][y].getCost() * tempVal);

					g2D.setColor(new Color(value, value, value, 255));

					g2D.fillRect(x * tS, y * tS, tS, tS);

					if (Key.drawGrid) {
						g2D.setColor(new Color(180, 180, 180, 255));
						g2D.drawRect(x * tS, y * tS, tS, tS);
					}
				}
			}
		} else {
			for (int x = 0; x < getWidthInTiles(); x++) {
				for (int y = 0; y < getHeightInTiles(); y++) {

					g2D.setColor(new Color(110, 110, 110, 255));
					if (isCell(x, y, Key.floor)) {
						g2D.setColor(new Color(32, 127, 32, 255));
					} else if (isCell(x, y, Key.sideWall) || isCell(x, y, Key.lockedWall)) {
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

	public boolean isCell(int x, int y, int cellType) {
		// try {
		return map[x][y].getKey() == cellType;
		// } catch (Exception e) {
		// System.out.println(x);
		// System.out.println(y);
		// e.printStackTrace();
		// }
		// return false;
	}

	public int checkCell(int x, int y) {
		return map[x][y].getKey();
	}

	public void setCell(int x, int y, int cellType) {
		map[x][y].setKey(cellType);
		updateTileCost(x, y);
	}

	public void resetVisitedMap() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				map[x][y].setVisible(false);
			}
		}
	}

	@Override
	public int getWidthInTiles() {
		return map.length;
	}

	@Override
	public int getHeightInTiles() {
		return map[0].length;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		map[x][y].setVisited(true);
	}

	private void createDungeon(int roomNum) {
		// ************************************************************************
		// initialize the map - already initialized
		// ************************************************************************
		// for (int y = 0; y < mapKey.length; y++) {
		// for (int x = 0; x < mapKey[0].length; x++) {
		// setCell(x, y, Key.unused);
		// }
		// }
		// ************************************************************************
		// this creates the rooms
		// ************************************************************************
		for (int r = 0; rooms.size() < roomNum && r < 2500; r++) {
			// random value that decides default room size
			int randVal = Key.random.nextInt(100);
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

			int tempWidth = Key.random.nextInt(startRoomWidth) + startRoomWidth;
			int tempHeight = Key.random.nextInt(startRoomHeight) + startRoomHeight;
			int tempX = Key.random.nextInt(getWidthInTiles() - (1 + tempWidth));
			int tempY = Key.random.nextInt(getHeightInTiles() - (1 + tempHeight));
			createRoom(roomType, tempX, tempY, tempWidth, tempHeight);
		}

		// ************************************************************************
		// connect rooms - with halls, for every room
		// ************************************************************************
		for (int r = 0; r < rooms.size(); r++) {
			// -check that you can get to each other room and stop one the room
			// we are in has 3+ doors
			for (int tr = r + 1; tr < rooms.size() && rooms.get(r).getDoors() <= 3; tr++) {
				// redraw the pathmap, so the pathfinder will use already
				// existing halls and rooms
				createPathMap();
				AStarPathFinder pf = new AStarPathFinder(this, 3 * Math.max(Key.width / 2, Key.height / 2), false);
				// AStarPathFinder pf = new AStarPathFinder(this, 10000,
				// false);//replaced with above line
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
						// redraw the pathmap, so the pathfinder will use
						// already existing halls and rooms
						// createPathMap();//the path map is already redrawn
						// about 15 lines above
						// AStarPathFinder tpf = new AStarPathFinder(this, 3 *
						// Math.max(Key.width / 2, Key.height / 2), false);
						AStarPathFinder tpf = new AStarPathFinder(this, 3 * Math.max(Key.width / 2, Key.height / 2), false);// 4
						// AStarPathFinder tpf = new AStarPathFinder(this,
						// (getHeightInTiles() * getWidthInTiles()) /
						// ((rooms.size() * 2)), false);// 4
						// TODO fix the dead end hallways
						p = tpf.findPath(Key.pathFinderRoomTunneler, sx, sy, ex, ey);
						if (p != null) {
							if (Key.showDebug && Key.showHallMapping) {
								System.out.println("from room: " + r + " to " + tr);
							}
							// follow the path to get to the room, creating a
							// hall as you go
							for (int pathKey = 0; pathKey < p.getLength(); pathKey++) {
								if (isCell(p.getX(pathKey), p.getY(pathKey), Key.sideWall)) {
									setCell(p.getX(pathKey), p.getY(pathKey), Key.door);
									Room tempR = getRoomAt(p.getX(pathKey), p.getY(pathKey));
									tempR.addDoor();
									if (tempR.getDoors() >= 3) {
										for (int roomX = tempR.getRoomX1(); roomX <= tempR.getRoomX2(); roomX++) {
											for (int roomY = tempR.getRoomY1(); roomY <= tempR.getRoomY2(); roomY++) {
												if (isCell(roomX, roomY, Key.sideWall))
													setCell(roomX, roomY, Key.lockedWall);
											}
										}
									}
								} else if (isCell(p.getX(pathKey), p.getY(pathKey), Key.unused)) {
									setCell(p.getX(pathKey), p.getY(pathKey), Key.floor);
								}
							}

							// for (int pathKey = 0; pathKey < p.getLength();
							// pathKey++) {
							// if (isCell(p.getX(pathKey + 1), p.getY(pathKey),
							// Key.unused))
							// setCell(p.getX(pathKey + 1), p.getY(pathKey),
							// Key.sideWall);
							// if (isCell(p.getX(pathKey - 1), p.getY(pathKey),
							// Key.unused))
							// setCell(p.getX(pathKey - 1), p.getY(pathKey),
							// Key.sideWall);
							// if (isCell(p.getX(pathKey), p.getY(pathKey + 1),
							// Key.unused))
							// setCell(p.getX(pathKey), p.getY(pathKey + 1),
							// Key.sideWall);
							// if (isCell(p.getX(pathKey), p.getY(pathKey - 1),
							// Key.unused))
							// setCell(p.getX(pathKey), p.getY(pathKey - 1),
							// Key.sideWall);
							// }

							// // this wraps the path in walls if needed
							// int x = 0;
							// int y = 0;
							// for (int tile = 0; tile < p.getLength(); tile++)
							// {
							// x = p.getX(tile);
							// y = p.getY(tile);
							// // if (isCell(x, y, Key.unused) ||
							// // Key.isWall(this.checkCell(x, y))) {
							// if (isCell(x + 1, y, Key.floor) || isCell(x - 1,
							// y, Key.floor) || isCell(x, y + 1, Key.floor) ||
							// isCell(x, y - 1, Key.floor)) {
							// setCell(x, y, Key.sideWall);
							// }
							// // }
							// }
						}
					} else {
						// awesome...
						if (Key.showDebug && Key.showHallMapping) {
							System.out.println("path from room: " + r + " to " + tr + " is valid");
						}
					}
				}
			}
		}

		// ************************************************************************
		// this should wrap all the halls with walls - and removes double doors
		// this loop goes through the entire mapKey, except row 0, column 0,
		// maxheight - 1, and maxwidth - 1
		// ************************************************************************
		for (int x = 1; x < getWidthInTiles() - 1; x++) {
			for (int y = 1; y < getHeightInTiles() - 1; y++) {

				if (isCell(x, y, Key.unused)) {
					if (isCell(x + 1, y, Key.floor) || isCell(x - 1, y, Key.floor) || isCell(x, y + 1, Key.floor) || isCell(x, y - 1, Key.floor)) {
						setCell(x, y, Key.sideWall);
					}
				}
				// TODO try and remove the following code - double doors should
				// not even generate
				// else if (isCell(x, y, Key.door)) {
				// // this removes double doors
				// if (isCell(x + 1, y, Key.door) || isCell(x - 1, y, Key.door)
				// || isCell(x, y + 1, Key.door) || isCell(x, y - 1, Key.door))
				// {
				// if (isCell(x + 1, y, Key.floor) && isCell(x - 1, y,
				// Key.floor) || isCell(x, y + 1, Key.floor) && isCell(x, y - 1,
				// Key.floor)) {
				// setCell(x, y, Key.sideWall);
				// } else {
				// setCell(x, y, Key.floor);
				// }
				// }
				// }
			}
		}

		// ************************************************************************
		// this removes awkward walls (surrounded by 3 floor tiles) some of them
		// ************************************************************************
		// for (int x = 1; x < getWidthInTiles() - 1; x++) {
		// for (int y = 1; y < getHeightInTiles() - 1; y++) {
		// if (isCell(x, y, Key.sideWall)) {
		// if (isCell(x + 1, y, Key.floor) && isCell(x - 1, y, Key.floor)) {
		// if (isCell(x, y + 1, Key.floor) || isCell(x, y - 1, Key.floor))
		// setCell(x, y, Key.floor);
		// } else if (isCell(x, y + 1, Key.floor) && isCell(x, y - 1,
		// Key.floor)) {
		// if (isCell(x + 1, y, Key.floor) || isCell(x - 1, y, Key.floor))
		// setCell(x, y, Key.floor);
		// }
		// }
		// }
		// }

		// ************************************************************************
		// this adds the walls to the walllist
		// ************************************************************************
		int tS = Key.tileSize;
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				if (Key.isWall(checkCell(x, y))) {// || tempKey[y][x] == 1 ||
													// tempKey[y][x] == 2) {
					wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), true));
				} else if (map[x][y].isKey(Key.door)) {
					wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), false));
				}
			}
		}

		// ************************************************************************
		// for debugging purposes
		// ************************************************************************
		createPathMap();
	}

	public Room getRoomAt(int x, int y) {
		for (Room r : rooms) {
			if (r.containsCoords(x, y))
				return r;
		}
		return null;
	}

	public boolean createRoom(String roomType, int rx, int ry, int rwidth, int rheight) {
		Room tempRoom = new Room(roomType, rx, ry, rwidth, rheight);
		boolean spaceValid = true;
		// this checks if the space is valid
		for (int x = rx; x < rx + rwidth; x++) {
			for (int y = ry; y < ry + rheight; y++) {
				if (!isCell(x, y, Key.unused)) {// && !Key.isWall(checkCell(x,
												// y))) {
					spaceValid = false;
				}
			}
		}
		// if the space is valid then it creates the room
		if (spaceValid) {
			for (int x = rx; x < rx + rwidth; x++) {
				for (int y = ry; y < ry + rheight; y++) {
					// this if gets the border of the room and sets them to side
					// walls
					if (y == ry || y == ry + rheight - 1 || x == rx || x == rx + rwidth - 1) {
						// this gets the corners of the walls and sets them to
						// corner walls
						if (y <= ry && x <= rx || y <= ry && x >= rx + rwidth - 1 || y >= ry + rheight - 1 && x <= rx || y >= ry + rheight - 1
								&& x >= rx + rwidth - 1)
							setCell(x, y, Key.cornerWall);
						// this makes sure if its a corner wall it leaves it
						// alone
						else if (!isCell(x, y, Key.cornerWall))
							setCell(x, y, Key.sideWall);
					} else {
						// everything inside the room is floor right now, or at
						// least initially
						setCell(x, y, Key.floor);
					}
				}
			}
			// the room was added to the map, so it is added to the list of
			// rooms
			rooms.add(tempRoom);
			// returns true for placing the room
			return true;
		}
		// returns false because there is no space for the room
		return false;
	}

	public void createPathMap() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				updateTileCost(x, y);
			}
		}
	}

	public void updateTileCost(int x, int y) {
		if (isCell(x, y, Key.unused)) {
			map[x][y].setCost(8);
		} else if (isCell(x, y, Key.floor)) {
			map[y][x].setCost(5);// 2
		} else if (isCell(x, y, Key.door)) {
			map[x][y].setCost(1);
		} else if (Key.isWall(checkCell(x, y))) {
			map[x][y].setCost(10);
			// map[x][y].setCost(calcWallCost(x, y));
			// if (map[x][y].getCost() < 5)
			// map[x][y].setCost(5);
		} else if (isCell(x, y, Key.lockedWall)) {
			// TODO - make sure cost is working right
			map[x][y].setCost(50);
		}
	}

	private int calcWallCost(int x, int y) {
		int tempCost = 0;
		// makes sure its in the bound of the array
		int startX = Math.min(Math.max(x - 2, 0), getWidthInTiles() - 1);
		int startY = Math.min(Math.max(y - 2, 0), getHeightInTiles() - 1);
		int endX = Math.min(Math.max(x + 2, 0), getWidthInTiles() - 1);
		int endY = Math.min(Math.max(y + 2, 0), getHeightInTiles() - 1);
		// the t is for temp
		for (int tx = startX; tx < endX; tx++) {
			for (int ty = startY; ty < endY; ty++) {
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

	@Override
	public boolean blocked(int type, int x, int y) {
		if (x != 0 || x != getWidthInTiles() - 1 || y != 0 || y != getHeightInTiles()) {
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
					else if(isCell(x,y,Key.lockedWall))
						return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public float getCost(int type, int sx, int sy, int tx, int ty) {
		float tempCost = Math.max(map[tx][ty].getCost(), map[sx][sy].getCost());
		if (Math.abs(sx - tx) == 1 && Math.abs(sy - ty) == 1)
			// return (float) Math.sqrt(tempCost + tempCost);
			return tempCost * 2 + 1;// no diagonal right now anyway
		return tempCost;
	}
}