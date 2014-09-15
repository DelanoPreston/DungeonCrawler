package Settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import DataStructures.Path;
import DataStructures.Room;
import DataStructures.Tile;
import Pathfinding.AStarPathFinder;
import Pathfinding.TileBasedMap;

public class Map implements TileBasedMap {
	Tile[][] map;
	List<Line2D> wallList2 = new ArrayList<>();
	List<MapTile> wallList = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();
	// for testing only
	List<Path> paths = new ArrayList<>();

	public Map(int width, int height) {

		// mapKey = new int[height][width];
		initializeTiles(width, height);
		// this initializes and creates the mapKey, pathMap, and the walls, and
		// rooms
		createDungeon(Key.numOfRooms);
		// this resets and initializes the visited map;
		resetVisitedMap();
	}

	public void paintComponent(Graphics g) {
		// double translateX, double translateY, double scale){
		// super.paintComponent(g);
		//
		// AffineTransform holder = new AffineTransform();
		//
		// holder.translate(getWidth() / 2, getHeight() / 2);
		// holder.scale(scale, scale);
		// holder.translate(-getWidth() / 2, -getHeight() / 2);
		//
		// holder.translate(translateX, translateY);
		// Graphics2D g2D = (Graphics2D) g;
		// g2D.setTransform(holder);
		//
		// if (level != null)
		// level.paintComponent(g2D);
		//
		// // this resets the at for the j components to draw normally
		// AffineTransform at = new AffineTransform();
		// g2D.setTransform(at);
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

									if (isWall(checkCell(tx, ty)))
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
						} else if (isCell(x, y, Key.floor) || isCell(x, y, Key.hallwayFloor)) {
							g2D.setColor(green);
						} else if (isWall(checkCell(x, y))) {
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

	public void drawGameMap(Graphics2D g2D, Dimension screen, double translateX, double translateY, double scale) {
		AffineTransform holder = new AffineTransform();

		holder.translate(screen.getWidth() / 2, screen.getHeight() / 2);
		holder.scale(scale, scale);
		holder.translate(-screen.getWidth() / 2, -screen.getHeight() / 2);

		holder.translate(translateX, translateY);
		g2D.setTransform(holder);

		if (map != null) {
			for (int x = 0; x < Key.width; x++) {
				for (int y = 0; y < Key.height; y++) {
					int xLoc = x * Key.tileSize;
					int yLoc = y * Key.tileSize;
					g2D.setColor(new Color(110, 110, 110, 255));
					switch (checkCell(x, y)) {
					case Key.floor:
					case Key.hallwayFloor:
						g2D.setColor(new Color(32, 127, 32, 255));
						break;
					case Key.sideWall:
						g2D.setColor(new Color(127, 127, 127, 255));
						break;
					case Key.lockedWall:
						g2D.setColor(new Color(137, 137, 137, 255));
						break;
					case Key.door:
						g2D.setColor(new Color(32, 32, 32, 255));
						break;
					}
					g2D.fillRect(xLoc, yLoc, Key.tileSize, Key.tileSize);

					if (Key.drawGrid) {
						g2D.setColor(new Color(180, 180, 180, 255));
						g2D.drawRect(xLoc, yLoc, Key.tileSize, Key.tileSize);
					}
				}
			}
		}

		// this resets the at for the j components to draw normally
		AffineTransform at = new AffineTransform();
		g2D.setTransform(at);
	}

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
					if (isCell(x, y, Key.floor) || isCell(x, y, Key.hallwayFloor)) {
						g2D.setColor(new Color(32, 127, 32, 255));
					} else if (isCell(x, y, Key.sideWall)) {// || isCell(x, y,
															// Key.lockedWall))
															// {
						g2D.setColor(new Color(127, 127, 127, 255));
					} else if (isCell(x, y, Key.lockedWall)) {
						g2D.setColor(new Color(137, 137, 137, 255));
						// } else if (isCell(x, y, Key.cornerWall)) {
						// g2D.setColor(new Color(137, 137, 137, 255));
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
		if (Key.drawRoomCenters) {
			g2D.setColor(new Color(32, 63, 95, 255));
			for (int r = 0; r < rooms.size(); r++) {
				int x = (int) rooms.get(r).getCenter().getX();
				int y = (int) rooms.get(r).getCenter().getY();
				g2D.fillRect(x * tS, y * tS, tS, tS);
			}
		}
		if (Key.drawTunnelingPaths) {

			for (Path p : paths) {
				for (int i = 0; i < p.getLength() - 1; i++) {
					g2D.setColor(new Color(Math.min(i * 2 + 45, 255), 0, 0, 255));
					g2D.drawLine(p.getX(i) * Key.tileSize + (Key.tileSize / 2), p.getY(i) * Key.tileSize + (Key.tileSize / 2), p.getX(i + 1) * Key.tileSize
							+ (Key.tileSize / 2), p.getY(i + 1) * Key.tileSize + (Key.tileSize / 2));
				}
			}
		}
	}

	public boolean isCell(int x, int y, int cellType) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height)
			return false;
		return map[x][y].getKey() == cellType;
	}

	public int checkCell(int x, int y) {
		return map[x][y].getKey();
	}

	public void setCell(int x, int y, int cellType) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height) {
		} else {
			map[x][y].setKey(cellType);
			updateTileCost(x, y);
		}
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
			for (int tr = r + 1; tr < rooms.size() && rooms.get(r).getDoors() < 3; tr++) {
				// redraw the pathmap, so the pathfinder will use already
				// existing halls and rooms
				createPathMap();
				AStarPathFinder pf = new AStarPathFinder(this, Math.max(Key.width, Key.height), false);
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
						AStarPathFinder tpf = new AStarPathFinder(this, Math.max(Key.width, Key.height), false);
						p = tpf.findPath(Key.pathFinderRoomTunneler, sx, sy, ex, ey);
						if (p != null) {
							paths.add(p);
							if (Key.showDebug && Key.showHallMapping) {
								System.out.println("from room: " + r + " to " + tr + " was mapped");
							}
							// follow the path to get to the room, creating a
							// hall as you go
							for (int pathKey = 0; pathKey < p.getLength(); pathKey++) {
								int x = p.getX(pathKey);
								int y = p.getY(pathKey);
								if (isCell(x, y, Key.sideWall)) {
									if (!(isCell(x + 1, y, Key.door) || isCell(x - 1, y, Key.door) || isCell(x, y + 1, Key.door) || isCell(x, y - 1, Key.door))) {
										setCell(x, y, Key.door);
										Room tempR = getRoomAt(x, y);
										tempR.addDoor();
										if (tempR.getDoors() >= 3) {
											for (int roomX = tempR.getRoomX1(); roomX <= tempR.getRoomX2(); roomX++) {
												for (int roomY = tempR.getRoomY1(); roomY <= tempR.getRoomY2(); roomY++) {
													if (isCell(roomX, roomY, Key.sideWall))
														setCell(roomX, roomY, Key.lockedWall);
												}
											}
										}
									} else if (isCell(x, y, Key.lockedWall)) {
										System.out.println("what????????????????????????????????????????????????????????");
									} else {

										setCell(x, y, Key.hallwayFloor);
									}

								} else if (isCell(x, y, Key.unused)) {
									setCell(x, y, Key.hallwayFloor);
								}
							}
						} else {
							if (Key.showDebug && Key.showHallMapping) {
								System.out.println("path from room: " + r + " to " + tr + " was not completed");
							}
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
					for (int x2 = x - 1; x2 <= x + 1; x2++) {
						for (int y2 = y - 1; y2 <= y + 1; y2++) {
							if (isCell(x2, y2, Key.hallwayFloor)) {
								setCell(x, y, Key.sideWall);
								break;
							}
						}
					}
				}
			}
		}

		// ************************************************************************
		// this adds the walls to the walllist
		// ************************************************************************
		int tS = Key.tileSize;
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				// the pathFinderRoomTunneler key returns all walls
				if (isWall(checkCell(x, y))) {// ||
												// tempKey[y][x]
												// ==
												// 1
												// ||
					// tempKey[y][x] == 2) {
					wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), true));
				} else if (map[x][y].isKey(Key.door)) {
					wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), false));
				}
			}
		}
		// ************************************************************************
		// testing more line creation
		// ************************************************************************
		// set all walls to side walls
		for (int x = 0; x < Key.width; x++) {
			for (int y = 0; y < Key.height; y++) {
				if (isCell(x, y, Key.lockedWall))
					setCell(x, y, Key.sideWall);
			}
		}
//		for(int r = 0; r<rooms.size();r++){
//			setCell(rooms.get(r).x, rooms.get(r).y, Key.lockedWall);
//			setCell(rooms.get(r).x, rooms.get(r).getRoomY2(), Key.lockedWall);
//			setCell(rooms.get(r).getRoomX2(), rooms.get(r).y, Key.lockedWall);
//			setCell(rooms.get(r).getRoomX2(), rooms.get(r).getRoomY2(), Key.lockedWall);
//		}
		
//		for (int x = 0; x < Key.width; x++) {
//			for (int y = 0; y < Key.height; y++) {
//				if (isCell(x, y, Key.sideWall)) {
//					
//				}
//			}
//		}

		// ************************************************************************
		// testing line creation for vision interrupts instead of walls
		// ************************************************************************

		// boolean firstWall = true;
		// int startX = -1;
		// int startY = -1;
		// // for vertical lines
		// for (int x = 0; x < Key.width; x++) {
		// for (int y = 0; y < Key.height; y++) {
		// if (isWall(x, y)) {
		// if (firstWall) {
		// startX = x;
		// startY = y;
		// firstWall = false;
		// }
		// } else if (!firstWall) {
		// if (startX != -1 && startY != -1 && startX != x && startY != y - 1) {
		// wallList2.add(new Line2D.Double(startX, startY, x, y - 1));
		// startX = -1;
		// startY = -1;
		// firstWall = true;
		// }
		// }
		// }
		// firstWall = true;
		// }
		// // for creating the horizontal lines
		// for (int y = 0; y < Key.height; y++) {
		// for (int x = 0; x < Key.width; x++) {
		//
		// }
		// }

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

	public boolean isCornerWall(int x, int y) {
		int[][] cornerCheck = new int[3][3];

		return false;
	}

	public boolean createRoom(String roomType, int rx, int ry, int rwidth, int rheight) {
		Room tempRoom = new Room(roomType, rx, ry, rwidth, rheight);
		boolean spaceValid = true;
		// this checks if the space is valid
		for (int x = rx; x < rx + rwidth && spaceValid; x++) {
			for (int y = ry; y < ry + rheight && spaceValid; y++) {
				if (!isCell(x, y, Key.unused)) {
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
						// locked walls
						if (y <= ry + 1 && x <= rx + 1 || y <= ry + 1 && x >= rx + rwidth - 2 || y >= ry + rheight - 2 && x <= rx + 1 || y >= ry + rheight - 2
								&& x >= rx + rwidth - 2)
							setCell(x, y, Key.lockedWall);// cornerwall
						// this makes sure if its a corner wall it leaves it
						// alone
						else if (!isCell(x, y, Key.lockedWall))// cornerwall
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
		int key = checkCell(x, y);
		switch (key) {
		case Key.unused:
			map[x][y].setCost(5);
			break;
		case Key.floor:
			map[x][y].setCost(2);
			break;
		case Key.hallwayFloor:
			map[x][y].setCost(1);
			break;
		case Key.door:
			map[x][y].setCost(1);
			break;
		case Key.sideWall:
			map[x][y].setCost(10);
			break;
		case Key.lockedWall:
			// case Key.cornerWall:
			map[x][y].setCost(100);
			break;
		default:
			map[x][y].setCost(1);
		}
	}

	public boolean isWall(int x, int y) {
		return isWall(checkCell(x, y));
	}

	public boolean isWall(int key) {
		if (key == Key.sideWall || key == Key.lockedWall)
			return true;
		else
			return false;
	}

	@Override
	public boolean blocked(int type, int tx, int ty) {
		if (tx != 0 || tx != getWidthInTiles() - 1 || ty != 0 || ty != getHeightInTiles()) {
			if (type == Key.pathFinderRoomCheck) {
				if (isWall(checkCell(tx, ty)) || isCell(tx, ty, Key.unused))
					return true;
			} else if (type == Key.pathFinderRoomTunneler) {
				if (isCell(tx, ty, Key.lockedWall))
					return true;
			}
			return false;
		}
		return true;
	}

	@Override
	public float getCost(int type, int sx, int sy, int tx, int ty) {
		return (float) map[tx][ty].getCost();
	}
}