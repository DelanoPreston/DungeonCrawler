package Item;


public class Armor extends Item {
	private static final long serialVersionUID = -1349122247265174882L;
	private String armorPiece;
//	private HashMap<String, Stat> types = new HashMap<>();

	public Armor(String name, String ap) {
		super(name);
		armorPiece = ap;
	}

	public String getArmorType() {
		return armorPiece;
	}
}
