package Item;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

import javax.swing.JComponent;

import DataStructures.Location;
import GameGui.InventoryGui;
import Settings.Key;

public class Inventory extends JComponent {
	private static final long serialVersionUID = -39174767373259435L;
	HashMap<Integer, Item> items = new HashMap<>();
	// size of the container
	int invHeight, invWidth;
	InventoryGui ig;

	public Inventory(Location l, int width, int height) {
		this.invWidth = width;
		this.invHeight = height;
		this.setPreferredSize(new Dimension(Key.tileSize * 16, Key.tileSize * 16));
		InventoryMouseListener iml = new InventoryMouseListener(this);
		this.addMouseListener(iml);
		this.addMouseMotionListener(iml);
		items.put(7, new Item("one"));
		items.put(4, new Item("two"));
		items.put(2, new Item("three"));
		items.put(0, new Item("four"));
		
		//this needs to be the last thing initialized in inventory
		ig = new InventoryGui(this, l);
	}

	public int getWidthInTiles() {
		return invWidth;
	}

	public int getHeightInTiles() {
		return invHeight;
	}

	/**
	 * this will add an item in the next open loction
	 * 
	 * @param item
	 *            - the item to be put into the inventory
	 * @return - true if it is added, false if it is not
	 */
	public boolean addItem(Item item) {
		for (int y = 0; invHeight - 1 > y; y++) {
			for (int x = 0; invWidth - 1 > x; x++) {
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
			int val = (y * invWidth) + x;
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
			int val = (y * invWidth) + x;
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
			int val = (y * invWidth) + x;
			if (items.containsKey(val)) {
				return items.get(val);
			}
		}
		return null;
	}

	public void draw(Graphics2D g2D) {
		ig.draw(g2D);
	}

	private boolean inBounds(int x, int y) {
		if (x >= 0 && y >= 0 && x < invWidth && y < invHeight) {
			return true;
		}
		return false;
	}
	
	public Rectangle getRectangle(){
		return ig.getRectangle();
	}
}
