package Item;

import java.awt.Graphics2D;

import DataStructures.Location;
import Entities.Entity;
import Player.Player;
import Settings.ContentBank;

public class ItemOnMap extends Entity {
	private static final long serialVersionUID = -8453507784050632720L;

	Item item;
	Location location;

	public ItemOnMap(Item i, Location loc) {
		item = i;
		location = loc;
	}

	public Location getLoc() {
		return location;
	}

	public Item getItem() {
		return item;
	}

	@Override
	public void draw(Graphics2D g2D) {
		if (item != null && location != null) {
			item.draw(g2D, (int) location.getX(), (int) location.getY());
		}
	}

	public void pickUpItem(Player player) {
		if (player.inventory.addItem(item)) {
			item = null;
			location = null;
		}
	}
}
