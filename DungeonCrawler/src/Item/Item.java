package Item;

import java.awt.Graphics2D;
import java.io.Serializable;

import Item.Interfaces.IItem;
import Settings.ContentBank;

public class Item implements Serializable, IItem {
	private static final long serialVersionUID = -3107986612368990684L;
	String name;
	int worth;// maybe if this is negative its a quest id???

	public Item(String name) {
		this.name = name;
	}
	
	public void draw(Graphics2D g2D, int x, int y){
		g2D.drawImage(ContentBank.shield, x, y, null);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public Inventory getInventory() {
		return null;//new Inventory(4, 4);
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
