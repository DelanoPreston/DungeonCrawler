package Settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import DataStructures.ID;
import DataStructures.Location;
import DataStructures.Path;
import DataStructures.Room;
import DataStructures.Tile;
import Pathfinding.AStarPathFinder;
import Pathfinding.TileBasedMap;

public class Map implements TileBasedMap {
	Tile[][] map;
	ChunkImage chunks[][];
	List<Line2D> visWallList = new ArrayList<>();
	List<Door> visDoorList = new ArrayList<>();
	// List<MapTile> wallList = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();
	// for testing only
	List<Path> paths = new ArrayList<>();

	// float translateX = 0;
	// float translateY = 0;
	// float scale = 1.0f;

	public Map(int width, int height) {

		// mapKey = new int[height][width];
		initializeTiles(width, height);
		// this initializes and creates the mapKey, pathMap, and the walls, and
		// rooms
		createDungeon(Key.numOfRooms);
		// this resets and initializes the visited map;
		resetVisitedMap();

		int wid = (Key.width / 16) + 1;
		int hei = (Key.height / 16) + 1;

		chunks = new ChunkImage[wid][hei];
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

	public List<Line2D> getWalls() {
		return visWallList;
	}

	public List<Door> getDoors() {
		return visDoorList;
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

								// if (tx >= 0 && ty >= 0 && tx <
								// getWidthInTiles() && ty < getHeightInTiles())
								// {
								if (inBounds(x, y)) {
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

	public void updateMinimapVisibility(Vision vis) {
		int ts = Key.tileSize;
		int hts = ts / 2;
		// only checks the map in the vision range
		Rectangle r = vis.getShape().getBounds();
		int minX = (int) Math.floor(Math.max(r.getMinX(), 0) / ts);
		int minY = (int) Math.floor(Math.max(r.getMinY(), 0) / ts);
		int maxX = (int) Math.ceil(Math.min(r.getMaxX(), Key.width * ts) / ts);
		int maxY = (int) Math.ceil(Math.min(r.getMaxY(), Key.height * ts) / ts);
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				if (!isCell(x, y, Key.unused.getID()) && !map[x][y].getVisible()) {
					// seems a little not optimized... because it has to check 4
					// points to see if a wall is set to visible
					if (isWall(x, y) || isCell(x, y, Key.door)) {
						Point2D p1 = new Point2D.Float(x * ts + hts - 1, y * ts + hts - 1);
						Point2D p2 = new Point2D.Float(x * ts + hts + 1, y * ts + hts - 1);
						Point2D p3 = new Point2D.Float(x * ts + hts - 1, y * ts + hts + 1);
						Point2D p4 = new Point2D.Float(x * ts + hts + 1, y * ts + hts + 1);
						if (containsWall(vis.getShape(), p1, p2, p3, p4)) {
							map[x][y].setVisible(true);
						}
					} else {
						if (vis.getShape().contains(new Point2D.Float(x * ts + hts, y * ts + hts))) {
							map[x][y].setVisible(true);
						}
					}
				}
			}
		}
	}

	private boolean containsWall(Shape s, Point2D p1, Point2D p2, Point2D p3, Point2D p4) {

		if (s.contains(p1) || s.contains(p2) || s.contains(p3) || s.contains(p4)) {
			return true;
		}
		return false;
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
	 */
	public void drawMiniMap(Graphics g2D, Dimension d, int px, int py) {
		// Start location
		int w = Key.mmWidth, h = Key.mmHeight;
		int mmTileSize = Key.mmtileSize;
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
				if (inBounds(x, y)) {
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
							// Redundant?
							g2D.setColor(transparent);
						}
					} else {
						// Redundant?
						g2D.setColor(transparent);
					}
				} else {
					// Redundant?
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

	public void drawGameMap(Graphics2D g2D, Dimension screen) {

		if (map != null) {
			for (int x = 0; x < Key.width; x++) {
				for (int y = 0; y < Key.height; y++) {
					int xLoc = x * Key.tileSize;
					int yLoc = y * Key.tileSize;
					g2D.setColor(new Color(110, 110, 110, 255));

					int key = checkCell(x, y).getID();
					// any floor
					if (key == Key.floor.getID())
						g2D.setColor(new Color(32, 127, 32, 255));
					else if (key == Key.hallwayFloor.getID())
						g2D.setColor(new Color(127, 127, 127, 255));
					else if (key == Key.lockedWall.getID())
						g2D.setColor(new Color(137, 137, 137, 255));
					else if (key == Key.door.getID())
						g2D.setColor(new Color(32, 32, 32, 255));
					g2D.fillRect(xLoc, yLoc, Key.tileSize, Key.tileSize);

					if (Key.drawGrid) {
						g2D.setColor(new Color(180, 180, 180, 255));
						g2D.drawRect(xLoc, yLoc, Key.tileSize, Key.tileSize);
					}
				}
			}
		}

		// this resets the at for the j components to draw normally
		// g2D.setTransform(new AffineTransform());
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
		if (Key.drawWallLines) {
			g2D.setColor(new Color(195, 0, 0, 255));
			for (Line2D l : visWallList) {
				g2D.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
				// g2D.drawRect((int) l.getX1(), (int) l.getY1(), 5, 5);
				// TODO working on drawing
			}
		}
	}

	public boolean isCell(int x, int y, ID cellType) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height)
			return false;
		return map[x][y].getID().equals(cellType);
	}

	public boolean isCell(int x, int y, int cellType) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height)
			return false;
		return map[x][y].getID().getID() == cellType;
	}

	public ID checkCell(int x, int y) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height)
			return Key.nullID;
		else
			return map[x][y].getID();
	}

	public void setCell(int x, int y, ID cellType) {
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
										setCell(x, y, Key.door);// Closed);
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
		// int tS = Key.tileSize;
		// for (int x = 0; x < getWidthInTiles(); x++) {
		// for (int y = 0; y < getHeightInTiles(); y++) {
		// // the pathFinderRoomTunneler key returns all walls
		// if (isWall(checkCell(x, y))) {// ||
		// // tempKey[y][x]
		// // ==
		// // 1
		// // ||
		// // tempKey[y][x] == 2) {
		// wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS),
		// true));
		// } else if (map[x][y].isKey(Key.door)) {
		// wallList.add(new MapTile(new Rectangle(x * tS, y * tS, tS, tS),
		// false));
		// }
		// }
		// }

		createVisionWalls();

		// ************************************************************************
		// for debugging purposes
		// ************************************************************************
		createPathMap();
	}

	public void createVisionWalls() {
		// ************************************************************************
		// testing more line creation - sets 'corner' points for where the
		// lines
		// are supposed to be(start and stop) ---Working very well
		//
		// ************************************************************************
		// set all walls to side walls
		for (int x = 0; x < Key.width; x++) {
			for (int y = 0; y < Key.height; y++) {
				if (isCell(x, y, Key.lockedWall))
					setCell(x, y, Key.sideWall);
			}
		}
		// this sets all corners of the rooms to locked wall (helps make vision
		// walls
		for (int r = 0; r < rooms.size(); r++) {
			setCell(rooms.get(r).x, rooms.get(r).y, Key.lockedWall);
			setCell(rooms.get(r).x, rooms.get(r).getRoomY2(), Key.lockedWall);
			setCell(rooms.get(r).getRoomX2(), rooms.get(r).y, Key.lockedWall);
			setCell(rooms.get(r).getRoomX2(), rooms.get(r).getRoomY2(), Key.lockedWall);
		}
		// setting more walls to locked walls
		for (int x = 0; x < Key.width; x++) {
			for (int y = 0; y < Key.height; y++) {
				if (isCell(x, y, Key.sideWall)) {
					int horiz = 0;
					int verti = 0;

					if (isWall(x, y + 1))
						verti++;
					if (isWall(x, y - 1))
						verti++;
					if (isWall(x + 1, y))
						horiz++;
					if (isWall(x - 1, y))
						horiz++;

					if (horiz + verti == 1 || (horiz + verti == 2 && horiz != 0 && verti != 0))
						setCell(x, y, Key.lockedWall);
					else if (horiz + verti == 3 && horiz != 0 && verti != 0) {
						if (isCell(x + 1, y, Key.unused) || isCell(x - 1, y, Key.unused) || isCell(x, y + 1, Key.unused) || isCell(x, y - 1, Key.unused))
							setCell(x, y, Key.lockedWall);
					}
				}
			}
			// before the x changes, I am going to check all the vertical locked
			// walls'for vision walls'
		}
		// for checking and creating the locked walls in relation to vertical
		// vision lines
		for (int x = 0; x < Key.width; x++) {
			for (int y = 0; y < Key.height; y++) {
				// TODO if you find a locked wall, make sure there is one above
				// and or below it
				if (isCell(x, y, Key.lockedWall)/* &&false// */) {
					// check above
					boolean temp = false;
					int tempY = y;
					do {
						tempY++;

						// if (isWall(x, tempY)) {
						if (isCell(x, tempY, Key.lockedWall)) {
							temp = true;
						} else if (isCell(x, tempY, Key.door) || isCell(x, tempY, Key.floor.getID()) || isCell(x, tempY, Key.unused)
								|| checkCell(x, tempY) == Key.nullID) {
							if (!(y == tempY - 1)) {
								setCell(x, tempY - 1, Key.lockedWall);
							}
							temp = true;
						}
					} while (!temp);
					temp = false;
					tempY = y;
					do {
						tempY--;
						// if (x == 35 && y == 14)
						// temp = false;
						// if (isWall(x, tempY)) {
						if (isCell(x, tempY, Key.lockedWall)) {
							temp = true;
						} else if (isCell(x, tempY, Key.door) || isCell(x, tempY, Key.floor.getID()) || isCell(x, tempY, Key.unused)
								|| checkCell(x, tempY) == Key.nullID) {
							if (!(y == tempY + 1)) {
								// if ((x == 35 && tempY + 1 == 12))
								setCell(x, tempY + 1, Key.lockedWall);
							}
							temp = true;
						}
					} while (!temp);

				}
			}
		}
		// for checking and creating the locked walls in relation to horizontal
		// vision lines
		for (int y = 0; y < Key.height; y++) {
			for (int x = 0; x < Key.width; x++) {
				// TODO if you find a locked wall, make sure there is one above
				// and or below it
				if (isCell(x, y, Key.lockedWall)/* &&false// */) {
					// check above
					boolean temp = false;
					int tempX = x;
					do {
						tempX++;

						// if (isWall(x, tempY)) {
						if (isCell(tempX, y, Key.lockedWall)) {
							temp = true;
						} else if (isCell(tempX, y, Key.door) || isCell(tempX, y, Key.floor.getID()) || isCell(tempX, y, Key.unused)
								|| checkCell(tempX, y) == Key.nullID) {
							if (!(x == tempX - 1)) {
								setCell(tempX - 1, y, Key.lockedWall);
							}
							temp = true;
						}
					} while (!temp);
					temp = false;
					tempX = x;
					do {
						tempX--;
						if (isCell(tempX, y, Key.lockedWall)) {
							temp = true;
						} else if (isCell(tempX, y, Key.door) || isCell(tempX, y, Key.floor.getID()) || isCell(tempX, y, Key.unused)
								|| checkCell(tempX, y) == Key.nullID) {
							if (!(x == tempX + 1)) {
								// if ((x == 35 && tempY + 1 == 12))
								setCell(tempX + 1, y, Key.lockedWall);
							}
							temp = true;
						}
					} while (!temp);

				}
			}
		}
		//
		// ************************************************************************
		// testing line creation for vision interrupts instead of walls -
		// working very well
		//
		// ************************************************************************
		// this is for the vertical walls
		int tS = Key.tileSize;
		boolean wallStart = true;
		int startXLoc = -1;
		int startYLoc = -1;
		// for vertical lines
		for (int x = 0; x < Key.width; x++) {
			for (int y = 0; y < Key.height; y++) {
				if (isWall(x, y) || isCell(x, y, Key.door)) {
					if (isCell(x, y, Key.lockedWall)) {
						if (!wallStart && startXLoc != -1 && startYLoc != -1) {
							visWallList.add(new Line2D.Double(startXLoc, startYLoc, (x * tS) + (tS / 2), (y * tS) + (tS / 2)));
							wallStart = true;
						}
						startXLoc = (x * tS) + (tS / 2);
						startYLoc = (y * tS) + (tS / 2);
						wallStart = false;
					} else if (isCell(x, y, Key.door)) {
						if (!wallStart && startXLoc != -1 && startYLoc != -1) {
							visWallList.add(new Line2D.Double(startXLoc, startYLoc, (x * tS) + (tS / 2), (y * tS)));
							wallStart = true;
						}
						startXLoc = (x * tS) + (tS / 2);
						startYLoc = (y * tS) + tS;
						wallStart = false;
					}
				} else {
					// end of wall/door 'chain' so resetting variables
					startXLoc = -1;
					startYLoc = -1;
					wallStart = true;
				}
			}
		}

		// for creating the horizontal lines - only horizontal lines
		for (int y = 0; y < Key.height; y++) {
			for (int x = 0; x < Key.width; x++) {
				if (isWall(x, y) || isCell(x, y, Key.door)) {
					// for walls ending in a locked wall
					if (isCell(x, y, Key.lockedWall)) {
						if (!wallStart && startXLoc != -1 && startYLoc != -1) {
							visWallList.add(new Line2D.Double(startXLoc, startYLoc, (x * tS) + (tS / 2), (y * tS) + (tS / 2)));
							wallStart = true;
						}
						startXLoc = (x * tS) + (tS / 2);
						startYLoc = (y * tS) + (tS / 2);
						wallStart = false;
						// for walls ending in a door
					} else if (isCell(x, y, Key.door)) {
						if (!wallStart && startXLoc != -1 && startYLoc != -1) {
							visWallList.add(new Line2D.Double(startXLoc, startYLoc, (x * tS), (y * tS) + (tS / 2)));
							wallStart = true;
						}
						startXLoc = (x * tS) + tS;
						startYLoc = (y * tS) + (tS / 2);
						wallStart = false;

					}
				} else {
					// end of wall/door 'chain' so resetting variables
					startXLoc = -1;
					startYLoc = -1;
					wallStart = true;
				}

			}
			// changing row, so resetting starting variables
			startXLoc = -1;
			startYLoc = -1;
			wallStart = true;
		}

		double hTS = tS / 2;
		// this adds the lines for the doors
		for (int y = 1; y < Key.height - 1; y++) {
			for (int x = 1; x < Key.width - 1; x++) {
				if (isCell(x, y, Key.door)) {
					if (isCell(x + 1, y, Key.lockedWall) && isCell(x - 1, y, Key.lockedWall)) {
						visDoorList.add(new Door(new Line2D.Double((x * tS), (y * tS) + hTS, (x + 1) * tS, (y * tS) + hTS), new Location(x, y)));
					} else if (isCell(x, y + 1, Key.lockedWall) && isCell(x, y - 1, Key.lockedWall)) {
						visDoorList.add(new Door(new Line2D.Double((x * tS) + hTS, (y * tS), (x * tS) + hTS, (y + 1) * tS), new Location(x, y)));
					}
				}
			}
		}
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

	private boolean inBounds(int x, int y) {
		if (x >= 0 && x < getWidthInTiles() && y >= 0 && y < getHeightInTiles())
			return true;
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
		int key = checkCell(x, y).getID();
		if (Key.unused.getID() == key)
			map[x][y].setCost(5);
		else if (Key.floor.getID() == key)
			map[x][y].setCost(2);
		else if (Key.hallwayFloor.getID() == key)
			map[x][y].setCost(1);
		else if (Key.door.getID() == key)
			map[x][y].setCost(1);
		else if (Key.sideWall.getID() == key)
			map[x][y].setCost(10);
		else if (Key.lockedWall.getID() == key)
			map[x][y].setCost(100);
		else
			map[x][y].setCost(100);
	}

	public boolean isWall(int x, int y) {
		return isWall(checkCell(x, y));
	}

	public boolean isWall(ID key) {
		if (key.equals(Key.sideWall) || key.equals(Key.lockedWall))
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

	// public BufferedImage toBufferedImage() {
	// // Create a buffered image with a format that's compatible with the
	// // screen
	// BufferedImage tempBImage = null;
	//
	// // boolean hasAlpha = hasAlpha(image);
	// //
	// // if (tempBImage == null) {
	// // // Create a buffered image using the default color model
	// // int type = BufferedImage.TYPE_INT_RGB;
	// // if (hasAlpha) {
	// // type = BufferedImage.TYPE_INT_ARGB;
	// // }
	// // tempBImage = new BufferedImage(tileWidth * ContentBank.tileSize,
	// // tileHeight * ContentBank.tileSize, type);
	// // }
	//
	// tempBImage = new BufferedImage(Key.width * Key.tileSize, Key.height *
	// Key.tileSize, BufferedImage.TYPE_INT_RGB);
	//
	// for (int y = 0; y < chunks; y++) {
	// for (int x = xLoc; x < xLoc + tileWidth; x++) {
	// int index = map[y][x].imageKey;
	// Image image = ContentBank.landTiles[index];
	//
	// // This code ensures that all the pixels in the image are loaded
	// image = new ImageIcon(image).getImage();
	//
	// // Determine if the image has transparent pixels; for this
	// // method's
	// // implementation, see Determining If an Image Has Transparent
	// // Pixels
	//
	// // Copy image to buffered image
	// Graphics g = tempBImage.createGraphics();
	//
	// // Paint the image onto the buffered image
	// g.drawImage(image, (x - xLoc) * ContentBank.tileSize, (y - yLoc) *
	// ContentBank.tileSize, null);
	//
	// // g.drawRect((x - xLoc) * ContentBank.tileSize, (y - yLoc) *
	// // ContentBank.tileSize, (x - xLoc + 1) * ContentBank.tileSize,
	// // (y - yLoc + 1) *
	// // ContentBank.tileSize);
	//
	// if (map[y][x].getEntity() != null) {// tempEntImg != null){
	// g.drawImage(ContentBank.woodenWalls[map[y][x].getEntity().getImageKey()],
	// (x - xLoc) * ContentBank.tileSize, (y - yLoc)
	// * ContentBank.tileSize, null);
	// }
	//
	// g.dispose();
	// }
	// }
	//
	// System.out.println(tempBImage.getHeight() + "," + tempBImage.getWidth());
	//
	// return tempBImage;
	// }
}