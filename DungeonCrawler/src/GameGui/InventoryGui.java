package GameGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import DataStructures.Location;
import Item.Inventory;
import Settings.Key;

public class InventoryGui {
	Inventory inventory;
	Rectangle rec;
	int spacing;

	public InventoryGui(Inventory i, Location l) {
		inventory = i;
		spacing = 3;
		int width = i.getWidthInTiles() * (Key.tileSizeInventory + spacing) + spacing;
		int height = i.getHeightInTiles() * (Key.tileSizeInventory + spacing) + spacing;
		rec = new Rectangle((int) l.getX(), (int) l.getY(), width, height);
	}

	public void update() {

	}

	public void draw(Graphics2D g2D) {
		// Location loc = new Location(10, 65);
		g2D.setColor(Color.BLACK);
		g2D.fillRect((int) rec.getLocation().getX(), (int) rec.getLocation().getY(), (int) rec.getWidth(), (int) rec.getHeight());
		g2D.setColor(Color.WHITE);
		// g2D.setStroke(new BasicStroke(2));
		int tsi = Key.tileSizeInventory;
		for (int y = 0; y < inventory.getHeightInTiles(); y++) {
			for (int x = 0; x < inventory.getWidthInTiles(); x++) {
				g2D.drawRect(x * (tsi + spacing) + (int) rec.getLocation().getX() + spacing, y * (tsi + spacing) + (int) rec.getLocation().getY() + spacing,
						tsi, tsi);
				// System.out.println("drawing");
			}
		}

		// drawing items
		g2D.setColor(Color.BLUE);
		int s = 5;
		for (int y = 0; y < inventory.getHeightInTiles(); y++) {
			for (int x = 0; x < inventory.getWidthInTiles(); x++) {
				if (inventory.hasItemAt(x, y) != null) {
					g2D.fillRect(x * (tsi + spacing) + s + (int) rec.getLocation().getX() + spacing, y * (tsi + spacing) + s + (int) rec.getLocation().getY()
							+ spacing, tsi - (2 * s), tsi - (2 * s));
				}
			}
		}
	}
	public Rectangle getRectangle(){
		return rec;
	}
}
