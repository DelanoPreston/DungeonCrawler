package Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WoodGame {

	public static Random random = new Random();

	public static void main(String[] args) {
		int level = 1;

		// monsters

		for (int i = 0; i < 10; i++) {
			System.out.println(newMonster(level).print());
		}
		System.out.println("\r");
		// weapons

		for (int i = 0; i < 10; i++) {
			System.out.println(newWeapon(level).print());
		}
		System.out.println("\r");
		// armor

		for (int i = 0; i < 10; i++) {
			System.out.println(newArmor(level).print());
		}
		// items
		System.out.println("\r");

		for (int i = 0; i < 10; i++) {
			System.out.println(newItem(level).print());
		}
	}

	public static Monster newMonster(int level) {
		List<Monster> monsters = new ArrayList<>();
		monsters.add(new Monster("Rat   ", Size.tiny, 2, mod(4, 2), mod(10, 2), 1));
		monsters.add(new Monster("Rat   ", Size.tiny, 2, mod(4, 2), mod(10, 2), 1));
		monsters.add(new Monster("Rat   ", Size.tiny, 2, mod(4, 2), mod(10, 2), 1));
		monsters.add(new Monster("Slime ", Size.normal, 1, mod(3, 2), mod(15, 4), 0));
		monsters.add(new Monster("Slime ", Size.normal, 1, mod(3, 2), mod(15, 4), 0));
		monsters.add(new Monster("Slime ", Size.normal, 1, mod(3, 2), mod(15, 4), 0));
		monsters.add(new Monster("Goblin", Size.small, 2, mod(8, 2), mod(12, 3), 3));
		monsters.add(new Monster("Goblin", Size.small, 2, mod(8, 2), mod(12, 3), 3));
		monsters.add(new Monster("Troll ", Size.large, 1, mod(10, 2), mod(25, 8), 8));
		monsters.add(new Monster("Spider", Size.large, 3, mod(7, 2), mod(15, 3), 5));
		monsters.add(new Monster("Spider", Size.large, 3, mod(7, 2), mod(15, 3), 5));
		return monsters.get(random.nextInt(monsters.size()));
	}

	public static Weapon newWeapon(int level) {
		List<Weapon> weapons = new ArrayList<>();
		weapons.add(new Weapon("Sword ", modMat(level), 1, mod(5, 2), 2));
		weapons.add(new Weapon("Knife ", modMat(level), 1, mod(2, 1), 1));
		weapons.add(new Weapon("Spear ", modMat(level), 2, mod(5, 2), 2));
		weapons.add(new Weapon("Bow   ", modMat(level), mod(4, 1), mod(2, 1), 1));
		weapons.add(new Weapon("C.Bow ", modMat(level), mod(3, 1), mod(3, 1), 2));
		weapons.add(new Weapon("Wand  ", modMat(level), mod(4, 1), mod(1, 1), 1));
		weapons.add(new Weapon("Staff ", modMat(level), mod(5, 1), mod(3, 2), 1));
		weapons.add(new Weapon("Mace  ", modMat(level), 1, mod(6, 2), 3));
		weapons.add(new Weapon("Hammer", modMat(level), 1, mod(7, 2), 4));
		return weapons.get(random.nextInt(weapons.size()));
	}

	public static Armor newArmor(int level) {
		List<Armor> armor = new ArrayList<>();
		armor.add(new Armor("Helmet    ", modMat(level), 1, mod(3, 1)));
		armor.add(new Armor("ChestPlate", modMat(level), 4, mod(6, 2)));
		armor.add(new Armor("Gloves    ", modMat(level), 1, mod(3, 1)));
		armor.add(new Armor("Leggings  ", modMat(level), 2, mod(4, 2)));
		armor.add(new Armor("Boots     ", modMat(level), 1, mod(3, 1)));
		return armor.get(random.nextInt(armor.size()));
	}

	public static Item newItem(int level) {
		List<Item> items = new ArrayList<>();
		items.add(new Item("Health Potion", 3));
		items.add(new Item("Mana Potion  ", 3));
		items.add(new Item("Rat Skin     ", 1));
		items.add(new Item("Troll Hide   ", 7));
		items.add(new Item("Spider Venom ", 5));
		return items.get(random.nextInt(items.size()));
	}

	public static Material modMat(int level) {
		int val = random.nextInt(30 * level);

		if (val < 20)

			return Material.wood;
		else if (val < 80)
			return Material.copper;
		else if (val < 140)
			return Material.iron;
		else
			// if(val < 250)
			return Material.steel;
	}

	public static int mod(int i, int j) {
		int val = i;

		val += getRandInRange(j);

		if (val < 1)
			return 1;
		else
			return val;
	}

	public static int getRandInRange(int i) {
		int val = 0;
		val = random.nextInt(i + 1);
		if (random.nextInt(2) == 0) {
			val *= -1;
		}
		return val;
	}
}

class Entity {
	public String name;

	public Entity(String n) {
		name = n;
	}
}

class Monster extends Entity {
	public Size size;
	public int movement;
	public int strength;
	public int health;
	public int armor;

	public Monster(String name, Size s, int m, int str, int h, int a) {
		super(name);
		size = s;
		movement = m;
		strength = str;
		health = h;
		armor = a;
	}

	public String print() {
		return "____ " + size + " " + name + "\tHealth: " + health + "\tMovement: " + movement + "\tStrength: " + strength + "\tArmor: " + armor;
	}
}

class Weapon extends Entity {
	public int range;
	public Material material;
	public int baseDamage;
	public int cost;
	public int weight;

	public Weapon(String n, Material m, int r, int bd, int w) {
		super(n);
		range = r + (m.getMod() / 2);
		material = m;
		baseDamage = bd+ (m.getMod() / 2);
		weight = w * m.getMod();
		cost = m.getMod() * bd + w;
	}

	public String print() {
		return "____ " + material + " " + name + "\tbaseDamage: " + baseDamage + "\tRange: " + range + "\tWeight: " + weight + "\tCost: " + cost;
	}
}

class Armor extends Entity {
	public Material material;
	public int weight;
	public int armor;
	public int cost;

	public Armor(String n, Material m, int w, int a) {
		super(n);
		material = m;
		weight = w * m.getMod();
		armor = a * m.getMod();
		cost = a * m.getMod() + w;
	}

	public String print() {
		return "____ " + material + " " + name + "\tArmor: " + armor + "\tWeight: " + weight + "\tCost: " + cost;
	}
}

class Item extends Entity {
	public int cost;

	public Item(String s, int c) {
		super(s);
		cost = c;
	}

	public String print() {
		return "____ " + name + "\tCost: " + cost;
	}
}

enum Material {
	wood(1), copper(2), iron(3), steel(4);
	int mod;

	Material(int m) {
		mod = m;
	}

	public int getMod() {
		return mod;
	}
}

enum Size {
	tiny, small, normal, large, huge
}
