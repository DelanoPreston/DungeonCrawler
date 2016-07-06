package GameGui;

import java.awt.Color;
import java.awt.Graphics2D;

import DataStructures.Location;
import Item.Inventory;
import Settings.Key;

public class InventoryGui {
	Inventory inventory;
	int width, height;
	int spacing;

	public InventoryGui(Inventory i) {
		inventory = i;
		spacing = 3;
		width = i.getWidthInTiles() * (Key.tileSizeInventory + spacing) + spacing;
		height = i.getHeightInTiles() * (Key.tileSizeInventory + spacing) + spacing;
	}

	public void update() {

	}

	public void draw(Graphics2D g2D) {
		Location loc = new Location(10, 65);
		g2D.setColor(Color.BLACK);
		g2D.fillRect((int) loc.getX(), (int) loc.getY(), width, height);
		g2D.setColor(Color.WHITE);
		// g2D.setStroke(new BasicStroke(2));
		int tsi = Key.tileSizeInventory;
		for (int y = 0; y < inventory.getHeightInTiles(); y++) {
			for (int x = 0; x < inventory.getWidthInTiles(); x++) {
				g2D.drawRect(x * (tsi + spacing) + (int) loc.getX() + spacing, y * (tsi + spacing) + (int) loc.getY() + spacing, tsi, tsi);
				// System.out.println("drawing");
			}
		}

		// drawing items
		g2D.setColor(Color.BLUE);
		int s = 5;
		for (int y = 0; y < inventory.getHeightInTiles(); y++) {
			for (int x = 0; x < inventory.getWidthInTiles(); x++) {
				if (inventory.hasItemAt(x, y) != null) {
					g2D.fillRect(x * (tsi + spacing) + s + (int) loc.getX() + spacing, y * (tsi + spacing) + s + (int) loc.getY() + spacing, tsi - (2 * s), tsi
							- (2 * s));
				}
			}
		}
	}
}
