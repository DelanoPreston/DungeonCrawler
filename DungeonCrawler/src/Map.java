
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Map {
	public static List<MapTile> wallList = new ArrayList<>();
	int tileHeight;
	int tileWidth;
	MapTile[][] map;

	public int getWidth() {
		return tileWidth * ContentBank.tileSize;
	}

	public int getHeight() {
		return tileHeight * ContentBank.tileSize;
	}

	public Map(int height, int width) {
		
		createMap(height, width);
		tileWidth = width;
		tileHeight = height;
	}

	public void paint(Graphics g) {
		g.setColor(new Color(155, 112, 105, 255));
//		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		for (int i = 0; i < wallList.size(); i++) {
			
			g.setColor(new Color(127, 127, 127, 255));
			g.fillRect(wallList.get(i).getX(), wallList.get(i).getY(), wallList.get(i).getSize(), wallList.get(i).getSize());
			g.setColor(new Color(180, 180, 180, 255));
			g.drawRect(wallList.get(i).getX(), wallList.get(i).getY(), wallList.get(i).getSize(), wallList.get(i).getSize());
		}

		// for (int y = 0; y < map.length; y++) {
		// for (int x = 0; x < map[0].length; x++) {
		// if (map[y][x].solid) {
		// g.setColor(new Color(127, 127, 127, 255));
		// g.fillRect(map[y][x].getX(), map[y][x].getY(), map[y][x].getSize(), map[y][x].getSize());
		// }
		// else{
		// g.setColor(new Color(155, 112, 105, 255));
		// g.fillRect(map[y][x].getX(), map[y][x].getY(), map[y][x].getSize(), map[y][x].getSize());
		// }
		//
		// }
		// }
	}

	public void createMap(int height, int width) {
		int tileSize = ContentBank.tileSize;
		map = new MapTile[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
					wallList.add(new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), true));
				// map[y][x] = new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), true);
				// else
				// map[y][x] = new MapTile(new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize), false);

			}
		}
		wallList.add(new MapTile(new Rectangle(5 * tileSize, 5 * tileSize, tileSize, tileSize), true));
		wallList.add(new MapTile(new Rectangle(17 * tileSize, 20 * tileSize, tileSize, tileSize), true));
		wallList.add(new MapTile(new Rectangle(17 * tileSize, 21 * tileSize, tileSize, tileSize), true));
		wallList.add(new MapTile(new Rectangle(17 * tileSize, 22 * tileSize, tileSize, tileSize), true));
		wallList.add(new MapTile(new Rectangle(17 * tileSize, 23 * tileSize, tileSize, tileSize), true));
		wallList.add(new MapTile(new Rectangle(17 * tileSize, 24 * tileSize, tileSize, tileSize), true));
		wallList.add(new MapTile(new Rectangle(5 * tileSize, 20 * tileSize, tileSize, tileSize), true));
	}
}
