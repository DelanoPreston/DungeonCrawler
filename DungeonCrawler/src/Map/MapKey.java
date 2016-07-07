package Map;

import java.awt.Shape;

import DataStructures.ID;
import DataStructures.Tile;
import Pathfinding.TileBasedMap;
import Settings.Key;

public class MapKey implements TileBasedMap {
	Tile[][] map;

	public MapKey(int width, int height) {
		map = new Tile[width][height];
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				map[x][y] = new Tile();
			}
		}
	}

	public ID checkCell(int x, int y) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height)
			return Key.nullID;
		else
			return map[x][y].getID();
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
	public float getCost(int type, int sx, int sy, int tx, int ty) {
		return (float) map[tx][ty].getCost();
	}

	public Tile getTile(int x, int y) {
		return map[x][y];
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		map[x][y].setVisited(true);
	}

	public void resetVisited() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {

				map[x][y].setVisited(false);
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

	public void createPathMap() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				updateTileCost(x, y);
			}
		}
	}

	public void resetVisitedMap() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				map[x][y].setVisible(false);
			}
		}
	}

	public void setCell(int x, int y, ID cellType) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height) {
		} else {
			map[x][y].setKey(cellType);
			updateTileCost(x, y);
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
		else if (Key.doorOpened.getID() == key)
			map[x][y].setCost(1);
		else if (Key.sideWall.getID() == key)
			map[x][y].setCost(10);
		else if (Key.lockedWall.getID() == key)
			map[x][y].setCost(100);
		else
			map[x][y].setCost(100);
	}

	public boolean inBounds(int x, int y) {
		if (x >= 0 && x < getWidthInTiles() && y >= 0 && y < getHeightInTiles())
			return true;
		return false;
	}

	// i think this can be done away with
	public boolean isWall(int x, int y) {
		return isWall(checkCell(x, y));
	}

	// i think this can be done away with
	public boolean isWall(ID key) {
		if (key.equals(Key.sideWall) || key.equals(Key.lockedWall))
			return true;
		else
			return false;
	}

	public boolean isCell(int x, int y, int cellType) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height)
			return false;
		if (map[x][y].getID().getID() == cellType)
			return true;

		return false;
	}

	public boolean isCell(int x, int y, ID cellType) {
		if (x < 0 || y < 0 || x >= Key.width || y >= Key.height)
			return false;
		if (map[x][y].getID().equals(cellType))
			return true;
		return false;
	}

	@Override
	public boolean blocked(int type, int tx, int ty) {
		if (tx != 0 || tx != getWidthInTiles() - 1 || ty != 0
				|| ty != getHeightInTiles()) {
			if (type == Key.pathFinderRoomCheck) {
				if (isWall(checkCell(tx, ty)) || isCell(tx, ty, Key.unused))
					return true;
				if (isCell(tx, ty, Key.doorClosed))
					return true;
			} else if (type == Key.pathFinderRoomTunneler) {
				if (isCell(tx, ty, Key.lockedWall))
					return true;
			}
			return false;
		}
		return true;
	}
}
