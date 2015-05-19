package Event;

import java.util.EventObject;

import Item.Inventory;
import Item.Interfaces.IItem;

public class InventoryEvent extends EventObject {
	private static final long serialVersionUID = -3948500090586701139L;
	public IItem item;
	public Inventory inv;

	public InventoryEvent(Object source, IItem item, Inventory i) {
		super(source);
		this.item = item;
		this.inv = i;
	}

}
