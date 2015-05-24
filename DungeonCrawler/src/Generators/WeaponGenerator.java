package Generators;

import Emuns.Quality;
import Item.Weapon;
import Settings.Key;

public class WeaponGenerator {
	Weapon w;

	public WeaponGenerator(int level, Quality minCond, Quality maxCond) {
		w = new Weapon(new NameGenerator().createName(), chooseWeaponType(), chooseQuality(minCond, maxCond), 100.0);

	}

	public void printWeapon() {
		if (w.getName().length() <= 4)
			System.out.println(w.getName() + "'s \t\t" + w.getQuality().toString() + " " + w.getWeaponType());
		else
			System.out.println(w.getName() + "'s \t" + w.getQuality().toString() + " " + w.getWeaponType());
	}

	public String chooseWeaponType() {
		String[] weaponTypes = { "Axe", "Sword", "Knife", "Bow", "CrossBow" };

		return weaponTypes[Key.random.nextInt(weaponTypes.length)];
	}

	public Quality chooseQuality(Quality minCond, Quality maxCond) {
		int i;
		Quality retQual = null;
		do {
			i = Key.random.nextInt(100);
			if (i < 15) {
				retQual = Quality.Shoddy; // 15%
			} else if (i < 40) {
				retQual = Quality.Poor; // 25%
			} else if (i < 70) {
				retQual = Quality.Good; // 30%
			} else if (i < 85) {
				retQual = Quality.Fair; // 15%
			} else if (i < 93) {
				retQual = Quality.Excellent; // 8%
			} else if (i < 98) {
				retQual = Quality.Perfect; // 5%
			} else {
				retQual = Quality.Legendary; // 3%
			}
			// this while just makes sure that the conditions passed in are
			// within the bounds declared
		} while (retQual.compareTo(minCond) <= 0 && retQual.compareTo(maxCond) >= 0);
		return retQual;
	}
}
