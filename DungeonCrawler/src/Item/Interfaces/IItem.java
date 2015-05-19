package Item.Interfaces;

import Item.Inventory;

public interface IItem {
	boolean hasInventory();

	Inventory getInventory();

	float getCost();

	float getWeight();

	String getName();
}
