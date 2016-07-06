package Item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JComponent;

import Settings.Key;

public class Inventory extends JComponent {
	private static final long serialVersionUID = -39174767373259435L;
	HashMap<Integer, Item> items = new HashMap<>();
	// size of the container
	int height, width;

	public Inventory(int width, int height) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(Key.tileSize * 16, Key.tileSize * 16));
		InventoryMouseListener iml = new InventoryMouseListener(this);
		this.addMouseListener(iml);
		this.addMouseMotionListener(iml);
		items.put(7, new Item("one"));
		items.put(10, new Item("two"));
		items.put(2, new Item("three"));
		items.put(0, new Item("four"));
	}

	public int getWidthInTiles() {
		return width;
	}

	public int getHeightInTiles() {
		return height;
	}

	/**
	 * this will add an item in the next open loction
	 * 
	 * @param item
	 *            - the item to be put into the inventory
	 * @return - true if it is added, false if it is not
	 */
	public boolean addItem(Item item) {
		for (int y = 0; height - 1 > y; y++) {
			for (int x = 0; width - 1 > x; x++) {
				if (putItemIn(item, x, y))
					return true;
			}
		}
		return false;
	}

	/**
	 * puts an item in this inventory at the location you pass in
	 * 
	 * @param item
	 *            - the item to be placed in
	 * @param x
	 *            - the x location in inventory
	 * @param y
	 *            - the y location in inventory
	 * @return - true if it is put in, false if not
	 */
	public boolean putItemIn(Item item, int x, int y) {
		if (inBounds(x, y)) {
			int val = (y * width) + x;
			if (!items.containsKey(val)) {
				items.put(val, item);
				return true;
			}
		}
		return false;
	}

	/**
	 * gets the item at the location, and removes it from this inventory
	 * 
	 * @param x
	 *            - x location in inventory
	 * @param y
	 *            - y location in inventory
	 * @return - the item at that location or null
	 */
	public Item removeItemFrom(int x, int y) {
		if (inBounds(x, y)) {
			int val = (y * width) + x;
			if (items.containsKey(val)) {
				return items.remove(val);
			}
		}
		return null;
	}
	
	/**
	 * gets the item at the location
	 * 
	 * @param x
	 *            - x location in inventory
	 * @param y
	 *            - y location in inventory
	 * @return - the item at that location or null
	 */
	public Item hasItemAt(int x, int y) {
		if (inBounds(x, y)) {
			int val = (y * width) + x;
			if (items.containsKey(val)) {
				return items.get(val);
			}
		}
		return null;
	}

	public void draw(Graphics2D g) {
//		int tsi = Key.tileSizeInventory;
//		Graphics2D g2D = (Graphics2D) g;
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				g2D.setColor(Color.LIGHT_GRAY);
//				g2D.fillRect(x * tsi, y * tsi, tsi, tsi);
//				g2D.setColor(Color.DARK_GRAY);
//				g2D.drawRect(x * tsi, y * tsi, tsi, tsi);
//			}
//		}
//
//		for (int key : items.keySet()) {
//			g2D.setColor(Color.BLUE);
//			g2D.fillRect((int) ((key % width) * tsi + 5), (int) (Math.floor(key / width) * tsi + 5), 54, 54);
//		}
	}

	private boolean inBounds(int x, int y) {
		if (x >= 0 && y >= 0 && x < width && y < height) {
			return true;
		}
		return false;
	}

}
