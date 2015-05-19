package Item;

import java.io.Serializable;
import java.util.List;

import Item.Interfaces.IItem;

public class Item implements Serializable, IItem {
	private static final long serialVersionUID = -3107986612368990684L;
	String name;
	int worth;// maybe if this is negative its a quest id???

	public Item(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasInventory() {
		return true;
	}

	@Override
	public Inventory getInventory() {
		return new Inventory(4, 4);
	}

	@Override
	public float getCost() {
		return 10f;
	}

	@Override
	public float getWeight() {
		// TODO Auto-generated method stub
		return 5f;
	}
}
