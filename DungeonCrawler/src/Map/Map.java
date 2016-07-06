package Map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.List;

import DataStructures.Location;
import DataStructures.Path;
import DataStructures.Room;
import Pathfinding.AStarPathFinder;
import Player.Player;
import Settings.ChunkImage;
import Settings.ContentBank;
import Settings.Door;
import Settings.Key;
import Settings.Vision;

public class Map {
	// Tile[][] map;
	MapKey map;
	ChunkImage chunks[][];
	List<Line2D> visWallList = new ArrayList<>();
	List<Door> visDoorList = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();
	// for testing only
	List<Path> paths = new ArrayList<>();

	public MapKey getMapKey() {
		return map;
	}

	public Map(int width, int height) {

		map = new MapKey(width, height);
		// this initializes and creates the mapKey, pathMap, and the walls, and
		// rooms
		createDungeon(Key.numOfRooms);
		// this resets and initializes the visited map;
		map.resetVisitedMap();

		// this makes sure that there are enough chunks for each map generated
		int hei, wid;
		int chunkSize = Key.chunkTiles;
		if (height % chunkSize > 0)
			hei = height / chunkSize + 1;
		else
			hei = height / chunkSize;
		if (width % chunkSize > 0)
			wid = width / chunkSize + 1;
		else
			wid = width / chunkSize;

		// this initializes the chunks variable
		chunks = new ChunkImage[hei][wid];
		for (int y = 0; y < hei; y++) {
			for (int x = 0; x < wid; x++) {

				chunks[y][x] = new ChunkImage(new Location(x * chunkSize * Key.tileSize, y * chunkSize * Key.tileSize), (chunkSize / 2) * Key.tileSize);
			}
		}
		// TODO remove this if you find a better place to initialize the
		// dungeontiles
		createChunkImages();
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

	public List<Path> getPaths() {
		return paths;
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
				if (!map.isCell(x, y, Key.unused.getID()) && !map.getTile(x, y).getVisible()) {
					// seems a little not optimized... because it has to check 4
					// points to see if a wall is set to visible
					if (map.isWall(x, y) || map.isCell(x, y, Key.door)) {
						Point2D p1 = new Point2D.Float(x * ts + hts - 1, y * ts + hts - 1);
						Point2D p2 = new Point2D.Float(x * ts + hts + 1, y * ts + hts - 1);
						Point2D p3 = new Point2D.Float(x * ts + hts - 1, y * ts + hts + 1);
						Point2D p4 = new Point2D.Float(x * ts + hts + 1, y * ts + hts + 1);
						if (containsWall(vis.getShape(), p1, p2, p3, p4)) {
							map.getTile(x, y).setVisible(true);
						}
					} else {
						if (vis.getShape().contains(new Point2D.Float(x * ts + hts, y * ts + hts))) {
							map.getTile(x, y).setVisible(true);
						}
					}
				}
			}
		}
	}

	// i need to make this more efficient - this is brute force
	public boolean canMove(Location loc, int size) {
		int offset = size / 2;
		Rectangle entity = new Rectangle((int) loc.getX() - offset, (int) loc.getY() - offset, size, size);
		for (Line2D l : visWallList) {
			if (l.intersects(entity)) {
				return false;
			}
		}

		return true;
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
		int mmts = Key.mmtileSize;
		int sx = d.width - (w * mmts);
		int sy = 0;
		int xOffset = w / 2;
		int yOffset = h / 2;

		Color brown = new Color(187, 122, 80, 255);
		Color transparent = new Color(0, 0, 0, 0);

		for (int x = px - xOffset; x < px + xOffset; x++) {
			for (int y = py - yOffset; y < py + yOffset; y++) {
				g2D.setColor(transparent);// this is just in case no color is
											// selected
				if (map.inBounds(x, y)) {
					if (map.getTile(x, y).getVisible() || !Key.drawMMFogOfWar) {
						if (x == px && y == py) {
							g2D.setColor(Color.yellow);
						} else if (map.isCell(x, y, Key.floor) || map.isCell(x, y, Key.hallwayFloor)) {
							g2D.setColor(Color.green);
						} else if (map.isWall(map.checkCell(x, y))) {
							g2D.setColor(Color.gray);
						} else if (map.isCell(x, y, Key.door)) {
							g2D.setColor(brown);
						}
					}
				}
				g2D.fillRect(sx + ((x - (px - xOffset)) * mmts), sy + ((y - (py - yOffset)) * mmts), mmts, mmts);
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

					int key = map.checkCell(x, y).getID();
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
	}

	public void drawMapWithChunks(Graphics2D g2D, Player p) {
		// createChunkImages();
		for (int y = 0; y < chunks.length; y++) {
			for (int x = 0; x < chunks[0].length; x++) {
				// /the player is passed in here because eventually the ray cast
				// dist will be in player
				if (chunks[y][x].getCenterLocation().getDistance(p.getLoc()) < Key.renderChunkDist) {
					int y2 = (int) chunks[y][x].getLocation().getY();
					int x2 = (int) chunks[y][x].getLocation().getX();
					g2D.drawImage(chunks[y][x].getImage(), x2, y2, null);
				}
			}
		}
	}

	public void drawWholeMap(Graphics g2D) {
		int tS = Key.tileSize;
		if (Key.drawPathMap) {
			int tempVal = 0;
			for (int x = 0; x < map.getWidthInTiles(); x++) {
				for (int y = 0; y < map.getHeightInTiles(); y++) {

					if (map.getTile(x, y).getCost() > tempVal)
						map.getTile(x, y).setCost(tempVal);
				}
			}
			tempVal = 255 / tempVal;
			for (int x = 0; x < map.getWidthInTiles(); x++) {
				for (int y = 0; y < map.getHeightInTiles(); y++) {

					int value = 255 - (map.getTile(x, y).getCost() * tempVal);

					g2D.setColor(new Color(value, value, value, 255));

					g2D.fillRect(x * tS, y * tS, tS, tS);

					if (Key.drawGrid) {
						g2D.setColor(new Color(180, 180, 180, 255));
						g2D.drawRect(x * tS, y * tS, tS, tS);
					}
				}
			}
		} else {
			for (int x = 0; x < map.getWidthInTiles(); x++) {
				for (int y = 0; y < map.getHeightInTiles(); y++) {

					g2D.setColor(new Color(110, 110, 110, 255));
					if (map.isCell(x, y, Key.floor) || map.isCell(x, y, Key.hallwayFloor)) {
						g2D.setColor(new Color(32, 127, 32, 255));
					} else if (map.isCell(x, y, Key.sideWall)) {// ||map.isCell(x,
																// y,
						// Key.lockedWall))
						// {
						g2D.setColor(new Color(127, 127, 127, 255));
					} else if (map.isCell(x, y, Key.lockedWall)) {
						g2D.setColor(new Color(137, 137, 137, 255));
						// } else if (map.isCell(x, y, Key.cornerWall)) {
						// g2D.setColor(new Color(137, 137, 137, 255));
					} else if (map.isCell(x, y, Key.door)) {
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
	}

	public void drawRoomNumbers(Graphics2D g2D) {
		int tS = Key.tileSize;
		g2D.setColor(new Color(255, 255, 255, 255));
		for (int r = 0; r < rooms.size(); r++) {
			int x = (int) (rooms.get(r).getCenter().getX() * tS);
			int y = (int) (rooms.get(r).getCenter().getY() * tS);
			g2D.drawString(String.valueOf(r), x, y);
		}
	}

	public void drawWallLines(Graphics2D g2D) {
		g2D.setColor(new Color(195, 0, 0, 255));
		for (Line2D l : visWallList) {
			g2D.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
		}
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
			int tempX = Key.random.nextInt(map.getWidthInTiles() - (1 + tempWidth));
			int tempY = Key.random.nextInt(map.getHeightInTiles() - (1 + tempHeight));
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
				map.createPathMap();
				// AStarPathFinder pf = new AStarPathFinder(this,
				// Math.max(Key.width, Key.height), false);
				AStarPathFinder pf = new AStarPathFinder(map, Math.max(Key.width, Key.height) / 2, false);
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
						// AStarPathFinder tpf = new AStarPathFinder(this,
						// Math.max(Key.width, Key.height), false);
						AStarPathFinder tpf = new AStarPathFinder(map, Math.max(Key.width, Key.height) / 2, false);
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
								if (map.isCell(x, y, Key.sideWall)) {
									if (!(map.isCell(x + 1, y, Key.door) || map.isCell(x - 1, y, Key.door) || map.isCell(x, y + 1, Key.door) || map.isCell(x,
											y - 1, Key.door))) {
										map.setCell(x, y, Key.door);// Closed);
										Room tempR = getRoomAt(x, y);
										tempR.addDoor();
										if (tempR.getDoors() >= tempR.getMaxDoor()) {
											for (int roomX = tempR.getRoomX1(); roomX <= tempR.getRoomX2(); roomX++) {
												for (int roomY = tempR.getRoomY1(); roomY <= tempR.getRoomY2(); roomY++) {
													if (map.isCell(roomX, roomY, Key.sideWall))
														map.setCell(roomX, roomY, Key.lockedWall);
												}
											}
										}
									} else if (map.isCell(x, y, Key.lockedWall)) {
										System.out.println("what????????????????????????????????????????????????????????");
									} else {

										map.setCell(x, y, Key.hallwayFloor);
									}

								} else if (map.isCell(x, y, Key.unused)) {
									map.setCell(x, y, Key.hallwayFloor);
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
		for (int x = 1; x < map.getWidthInTiles() - 1; x++) {
			for (int y = 1; y < map.getHeightInTiles() - 1; y++) {
				if (map.isCell(x, y, Key.unused)) {
					for (int x2 = x - 1; x2 <= x + 1; x2++) {
						for (int y2 = y - 1; y2 <= y + 1; y2++) {
							if (map.isCell(x2, y2, Key.hallwayFloor)) {
								map.setCell(x, y, Key.sideWall);
								break;
							}
						}
					}
				}
			}
		}

		createVisionWalls();

		// ************************************************************************
		// for debugging purposes
		// ************************************************************************
		map.createPathMap();
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
				if (map.isCell(x, y, Key.lockedWall))
					map.setCell(x, y, Key.sideWall);
			}
		}
		// this sets all corners of the rooms to locked wall (helps make vision
		// walls
		for (int r = 0; r < rooms.size(); r++) {
			map.setCell(rooms.get(r).x, rooms.get(r).y, Key.lockedWall);
			map.setCell(rooms.get(r).x, rooms.get(r).getRoomY2(), Key.lockedWall);
			map.setCell(rooms.get(r).getRoomX2(), rooms.get(r).y, Key.lockedWall);
			map.setCell(rooms.get(r).getRoomX2(), rooms.get(r).getRoomY2(), Key.lockedWall);
		}
		// setting more walls to locked walls
		for (int x = 0; x < Key.width; x++) {
			for (int y = 0; y < Key.height; y++) {
				if (map.isCell(x, y, Key.sideWall)) {
					int horiz = 0;
					int verti = 0;

					if (map.isWall(x, y + 1))
						verti++;
					if (map.isWall(x, y - 1))
						verti++;
					if (map.isWall(x + 1, y))
						horiz++;
					if (map.isWall(x - 1, y))
						horiz++;

					if (horiz + verti == 1 || (horiz + verti == 2 && horiz != 0 && verti != 0))
						map.setCell(x, y, Key.lockedWall);
					else if (horiz + verti == 3 && horiz != 0 && verti != 0) {
						if (map.isCell(x + 1, y, Key.unused) || map.isCell(x - 1, y, Key.unused) || map.isCell(x, y + 1, Key.unused)
								|| map.isCell(x, y - 1, Key.unused))
							map.setCell(x, y, Key.lockedWall);
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
				// and or below it -------------- I think this is done
				if (map.isCell(x, y, Key.lockedWall)/* &&false// */) {
					// check above
					boolean temp = false;
					int tempY = y;
					do {
						tempY++;

						// if (map.isWall(x, tempY)) {
						if (map.isCell(x, tempY, Key.lockedWall)) {
							temp = true;
						} else if (map.isCell(x, tempY, Key.door) || map.isCell(x, tempY, Key.floor.getID()) || map.isCell(x, tempY, Key.unused)
								|| map.checkCell(x, tempY) == Key.nullID) {
							if (!(y == tempY - 1)) {
								map.setCell(x, tempY - 1, Key.lockedWall);
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
						// if (map.isWall(x, tempY)) {
						if (map.isCell(x, tempY, Key.lockedWall)) {
							temp = true;
						} else if (map.isCell(x, tempY, Key.door) || map.isCell(x, tempY, Key.floor.getID()) || map.isCell(x, tempY, Key.unused)
								|| map.checkCell(x, tempY) == Key.nullID) {
							if (!(y == tempY + 1)) {
								// if ((x == 35 && tempY + 1 == 12))
								map.setCell(x, tempY + 1, Key.lockedWall);
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
				// and or below it -------------- I think this is done
				if (map.isCell(x, y, Key.lockedWall)/* &&false// */) {
					// check above
					boolean temp = false;
					int tempX = x;
					do {
						tempX++;

						// if (map.isWall(x, tempY)) {
						if (map.isCell(tempX, y, Key.lockedWall)) {
							temp = true;
						} else if (map.isCell(tempX, y, Key.door) || map.isCell(tempX, y, Key.floor.getID()) || map.isCell(tempX, y, Key.unused)
								|| map.checkCell(tempX, y) == Key.nullID) {
							if (!(x == tempX - 1)) {
								map.setCell(tempX - 1, y, Key.lockedWall);
							}
							temp = true;
						}
					} while (!temp);
					temp = false;
					tempX = x;
					do {
						tempX--;
						if (map.isCell(tempX, y, Key.lockedWall)) {
							temp = true;
						} else if (map.isCell(tempX, y, Key.door) || map.isCell(tempX, y, Key.floor.getID()) || map.isCell(tempX, y, Key.unused)
								|| map.checkCell(tempX, y) == Key.nullID) {
							if (!(x == tempX + 1)) {
								// if ((x == 35 && tempY + 1 == 12))
								map.setCell(tempX + 1, y, Key.lockedWall);
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
				if (map.isWall(x, y) || map.isCell(x, y, Key.door)) {
					if (map.isCell(x, y, Key.lockedWall)) {
						if (!wallStart && startXLoc != -1 && startYLoc != -1) {
							visWallList.add(new Line2D.Double(startXLoc, startYLoc, (x * tS) + (tS / 2), (y * tS) + (tS / 2)));
							wallStart = true;
						}
						startXLoc = (x * tS) + (tS / 2);
						startYLoc = (y * tS) + (tS / 2);
						wallStart = false;
					} else if (map.isCell(x, y, Key.door)) {
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
				if (map.isWall(x, y) || map.isCell(x, y, Key.door)) {
					// for walls ending in a locked wall
					if (map.isCell(x, y, Key.lockedWall)) {
						if (!wallStart && startXLoc != -1 && startYLoc != -1) {
							visWallList.add(new Line2D.Double(startXLoc, startYLoc, (x * tS) + (tS / 2), (y * tS) + (tS / 2)));
							wallStart = true;
						}
						startXLoc = (x * tS) + (tS / 2);
						startYLoc = (y * tS) + (tS / 2);
						wallStart = false;
						// for walls ending in a door
					} else if (map.isCell(x, y, Key.door)) {
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
				if (map.isCell(x, y, Key.door)) {
					if (map.isCell(x + 1, y, Key.lockedWall) && map.isCell(x - 1, y, Key.lockedWall)) {
						visDoorList.add(new Door(new Line2D.Double((x * tS), (y * tS) + hTS, (x + 1) * tS, (y * tS) + hTS), new Location(x, y)));
					} else if (map.isCell(x, y + 1, Key.lockedWall) && map.isCell(x, y - 1, Key.lockedWall)) {
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
				if (!map.isCell(x, y, Key.unused)) {
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
							map.setCell(x, y, Key.lockedWall);// cornerwall
						// this makes sure if its a corner wall it leaves it
						// alone
						else if (!map.isCell(x, y, Key.lockedWall))// cornerwall
							map.setCell(x, y, Key.sideWall);
					} else {
						// everything inside the room is floor right now, or at
						// least initially
						map.setCell(x, y, Key.floor);
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

	private void createChunkImages() {
		for (int yStart = 0; yStart < chunks.length; yStart++) {
			for (int xStart = 0; xStart < chunks[0].length; xStart++) {
				if (chunks[yStart][xStart].getUpdate()) {
					chunks[yStart][xStart].setImage(toBufferedImage(xStart * Key.chunkTiles, yStart * Key.chunkTiles));
					// System.out.println("updated chunk image: {" + xStart +
					// ", " + yStart + "}");
				}
			}
		}
	}

	private BufferedImage toBufferedImage(int xLoc, int yLoc) {
		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage tempBImage = null;
		for (int y = yLoc; y < yLoc + Key.chunkTiles; y++) {
			for (int x = xLoc; x < xLoc + Key.chunkTiles; x++) {
				// System.out.println(x + ", " + y);
				int index = 2;
				if (map != null && y < map.getHeightInTiles() && x < map.getWidthInTiles()) {
					index = map.getTile(x, y).getID().getID();
					// System.out.println(x + " " + y + " " + index + " " +
					// map[x][y].getID().getID());
				}

				Image image = ContentBank.dungeonTiles[index];

				// This code ensures that all the pixels in the image are loaded
				// image = new ImageIcon(image).getImage();

				// Determine if the image has transparent pixels; for this
				// method's
				// implementation, see Determining If an Image Has Transparent
				// Pixels
				// TODO fix this (the problem is that in key, the dungeon tiles
				// are null when this is called
				boolean hasAlpha = hasAlpha(image);

				if (tempBImage == null) {
					// Create a buffered image using the default color model
					int type = BufferedImage.TYPE_INT_RGB;
					if (hasAlpha) {
						type = BufferedImage.TYPE_INT_ARGB;
					}
					tempBImage = new BufferedImage(Key.chunkTiles * Key.tileSize, Key.chunkTiles * Key.tileSize, type);
				}

				// Copy image to buffered image
				Graphics g = tempBImage.createGraphics();

				// Paint the image onto the buffered image
				g.drawImage(image, (x - xLoc) * Key.tileSize, (y - yLoc) * Key.tileSize, null);

				g.dispose();
			}
		}
		return tempBImage;
	}

	// This method returns true if the specified image has transparent pixels
	private boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}