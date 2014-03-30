import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Map implements TileBasedMap {
	int[][] pathMap;
	private boolean[][] visited;
	int[][] mapKey;
	List<MapTile> wallList = new ArrayList<>();

	public Map(int[][] mapKey) {
		this.mapKey = mapKey;
		pathMap = createPathMap();
		resetVisitedMap();
		int tileSize = ContentBank.tileSize;
		for (int y = 0; y < mapKey.length; y++) {
			for (int x = 0; x < mapKey[0].length; x++) {
				if (Key.isWall(checkCell(x, y))) {// || tempKey[y][x] == 1 || tempKey[y][x] == 2) {
					wallList.add(new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), true));
				} else if (mapKey[y][x] == Key.door) {
					wallList.add(new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), false));
				}
			}
		}
	}

	public List<MapTile> getWalls() {
		return wallList;
	}

	public void paint(Graphics2D g2D) {
		int tS = ContentBank.tileSize;
		if (Key.showPathMap) {
			for (int y = 0; y < pathMap.length; y++) {
				for (int x = 0; x < pathMap[0].length; x++) {
					int value = 255 - (pathMap[y][x] * 10);
					g2D.setColor(new Color(value, value, value, 255));

					g2D.fillRect(x * tS, y * tS, tS, tS);

					g2D.setColor(new Color(180, 180, 180, 255));
					g2D.drawRect(x * tS, y * tS, tS, tS);
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

					// g2D.setColor(new Color(180, 180, 180, 255));
					// g2D.drawRect(x * tS, y * tS, tS, tS);
				}
			}

			// g2D.setColor(new Color(155, 112, 105, 255));
			// // g.fillRect(0, 0, getWidth(), getHeight());
			//
			// for (int i = 0; i < wallList.size(); i++) {
			// if (wallList.get(i).solid)
			// g2D.setColor(new Color(127, 127, 127, 255));
			// else
			// g2D.setColor(new Color(32, 127, 32, 255));
			//
			// g2D.fillRect(wallList.get(i).getX(), wallList.get(i).getY(), wallList.get(i).getSize(), wallList.get(i).getSize());
			//
			// g2D.setColor(new Color(180, 180, 180, 255));
			// g2D.drawRect(wallList.get(i).getX(), wallList.get(i).getY(), wallList.get(i).getSize(), wallList.get(i).getSize());
			// }
		}
	}

	public void connectRooms() {
		AStarPathFinder pf = new AStarPathFinder(this, 1000, false);

	}

	public int[][] createPathMap() {
		int[][] temp = new int[mapKey.length][mapKey[0].length];

		for (int y = 0; y < temp.length; y++) {
			for (int x = 0; x < temp[0].length; x++) {
				if (isCell(x, y, Key.unused)) {
					temp[y][x] = 5;
				} else if (isCell(x, y, Key.floor)) {
					temp[y][x] = 1;
				} else if (isCell(x, y, Key.door)) {
					temp[y][x] = 0;
				} else if (Key.isWall(checkCell(x, y))) {
					temp[y][x] = calcWallCost(x, y);
					if (temp[y][x] < 5)
						temp[y][x] = 5;
				}
			}
		}

		return temp;
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
				// if (isCell(tx, ty, Key.sideWall))
				if (Key.isWall(checkCell(x, y)))
					tempCost++;
				if (isCell(x, y, Key.door))
					tempCost += 5;
			}
		}

		return tempCost;
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
		return pathMap[0].length;
	}

	@Override
	public int getHeightInTiles() {
		return pathMap.length;
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
				if (isCell(x, y, Key.cornerWall))
					return true;
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

}
