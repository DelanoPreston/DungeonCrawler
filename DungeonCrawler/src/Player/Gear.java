package Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Item.Inventory;
import Item.Interfaces.IItem;
import Settings.DungeonPanel;

public class Gear{
	HashMap<String, IItem> gear = new HashMap<>();

	public Gear() {

	}

	/**
	 * returns null if the gear was put in, and there was no other gear already
	 * in that position, it will return the gear that was in the place that this
	 * gear went
	 * 
	 * @param location
	 *            - a string location of where the gear goes
	 * @param item
	 *            - the item to be worn
	 * @return - null or item
	 */
	public IItem addGear(String location, IItem item) {

		if (!gear.containsKey(location)) {
			gear.put(location, item);
			DungeonPanel.source.NewInventory(item.getInventory(), item);
			return null;
		} else {
			IItem temp = gear.get(location);
			gear.put(location, item);
			return temp;
		}
	}

	/**
	 * returns the item that was in the location passed in, or null if there was
	 * no item there
	 * 
	 * @param location
	 *            - string location
	 * @return - null or iitem
	 */
	public IItem removeGear(String location) {
		if (!gear.containsKey(location)) {
			// there was not item at that location
			return null;
		} else {
			// I think this removes and returns the iitem at the location
			return gear.remove(location);
		}
	}

	/**
	 * goes through the gear you have on you and returns all the inventories you
	 * have on you (that are worn)
	 * 
	 * @return - returns the inventories you have worn
	 */
	public List<Inventory> getInventories() {
		List<Inventory> temp = new ArrayList<>();
		for (Entry<String, IItem> i : gear.entrySet()) {
			if (i.getValue().hasInventory()) {
				temp.add(i.getValue().getInventory());
			}
		}
		return temp;
	}

	
}
