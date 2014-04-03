package Components;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import DataStructures.Room;
import DataStructures.Tile;
import Settings.Key;
import Settings.MapTile;

import com.artemis.Component;

public class MapComp extends Component implements TileBasedMap {
	Tile[][] map;
	List<MapTile> wallList = new ArrayList<>();
	List<Room> rooms = new ArrayList<>();

	public MapComp() {
		this(128, 128);
	}

	public MapComp(int width, int height) {
		map = new Tile[height][width];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				map[y][x] = new Tile();
			}
		}
	}

	public MapComp(Tile[][] map) {
		this.map = map;
	}

	public MapComp(MapComp mc) {
		this.map = mc.getMap();
		this.wallList = mc.getWalls();
		this.rooms = mc.getRooms();
	}

	public void resetVisited() {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				map[y][x].setVisited(false);
			}
		}
	}

	public Tile[][] getMap() {
		return map;
	}

	public List<MapTile> getWalls() {
		return wallList;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void addWall(MapTile mt) {
		wallList.add(mt);
	}

	public void addRoom(Room r) {
		rooms.add(r);
	}

	public boolean isCell(int x, int y, int cellType) {
		return map[y][x].getKey() == cellType;
	}

	public int checkCell(int x, int y) {
		return map[y][x].getKey();
	}

	public void setCell(int x, int y, int cellType) {
		map[y][x].setKey(cellType);
	}

	public int getCost(int x, int y) {
		return map[y][x].getCost();
	}

	public void setCost(int x, int y, int cost) {
		map[y][x].setCost(cost);
	}

	@Override
	public boolean blocked(PathFindingContext mover, int x, int y) {
		int type = ((MoverComp) mover.getMover()).getKey();
		if (x != 0 || x != map[0].length - 1 || y != 0 || y != map.length) {
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
	public float getCost(PathFindingContext mover, int x, int y) {
		return (float) map[y][x].getCost();
	}

	@Override
	public int getHeightInTiles() {
		return map.length;
	}

	@Override
	public int getWidthInTiles() {
		return map[0].length;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		map[y][x].setVisited(true);
	}
}
