package Item;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = -3107986612368990684L;
	String name;
	int worth;// maybe if this is negative its a quest id???

	public Item(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
