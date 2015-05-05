import java.util.HashMap;

import Item.Armor;

public class EquipmentHumanoid {
	HashMap<String, Armor> armor;

	public EquipmentHumanoid() {
		armor = new HashMap<>();
	}

	/**
	 * returns the armor that was just replaced with what is passed in
	 * 
	 * @param a
	 *            - the armor to be passed in
	 * @return - returns the armor that was in the place of what was put in, or
	 *         null if there was no armor there
	 */
	public Armor equipArmor(Armor a) {
		if (armor.containsKey(a.getArmorType())) {
			Armor retArmor = armor.get(a.getArmorType());
			armor.put(a.getArmorType(), a);
			return retArmor;
		} else {
			armor.put(a.getArmorType(), a);
			return null;
		}
	}

	/**
	 * returns the armor that is removed from
	 * 
	 * @param ap
	 *            - the armor piece that is to be removed
	 * @return - the removed armor, or null if there was no armor there
	 */
	public Armor unEquipArmor(String ap) {
		if (armor.containsKey(ap)) {
			return armor.get(ap);
		} else {
			return null;
		}
	}
}
