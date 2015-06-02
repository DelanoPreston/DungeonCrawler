package Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WoodGame {

	public static Random random = new Random();

	public static void main(String[] args) {
		int level = 1;
		boolean continueGame = true;
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
		} catch (Exception e) {

		}
		String input = "";
		System.out.println("welcome to dungeon game");
		do {
			System.out.println("1 (chest)");
			System.out.println("2 (monster)");
			System.out.println("3 (weapon)");
			System.out.println("4 (armor)");
			System.out.println("5 (item)");
			System.out.println("6 (damage - calculation)");
			input = scanner.nextLine();
			switch (input) {
			case "1":
				int val = random.nextInt(5) + 1;
				for (int i = 0; i < val; i++) {
					System.out.println(newItem(level).print());
				}
				if (random.nextInt(3) == 0) {
					System.out.println(newWeapon(level).print());
				}
				if (random.nextInt(2) == 0) {
					System.out.println(newArmor(level).print());
				}
				break;
			case "2":
				System.out.println(newMonster(level).print());
				break;
			case "3":
				System.out.println(newWeapon(level).print());
				break;
			case "4":
				System.out.println(newArmor(level).print());
				break;
			case "5":
				int val2 = random.nextInt(10);
				switch (val2) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
					System.out.println(newItem(level).print());
					break;
				case 8:
					System.out.println(newArmor(level).print());
					break;
				case 9:
					System.out.println(newWeapon(level).print());
					break;
				default:
					System.out.println(newItem(level).print());
					break;
				}

				break;
			case "6":
				System.out.println("enter strength");
				int str = scanner.nextInt();
				System.out.println("weapon damage");
				int weapon = scanner.nextInt() + 1;
				System.out.println("damage: " + (random.nextInt(weapon) + (str /2)));
				break;
			case "7":
				
				break;
			default:
				System.out.println("dough");
				break;
			}
		} while (continueGame);

	}

	public static Monster newMonster(int level) {
		List<Monster> monsters = new ArrayList<>();
		monsters.add(new Monster("Rat   ", Size.tiny, 2, mod(4, 2), mod(12, 2), 1));
		monsters.add(new Monster("Rat   ", Size.tiny, 2, mod(4, 2), mod(12, 2), 1));
		monsters.add(new Monster("Rat   ", Size.tiny, 2, mod(4, 2), mod(12, 2), 1));
		monsters.add(new Monster("Slime ", Size.normal, 1, mod(3, 2), mod(18, 4), 0));
		monsters.add(new Monster("Slime ", Size.normal, 1, mod(3, 2), mod(16, 4), 0));
		monsters.add(new Monster("Slime ", Size.normal, 1, mod(3, 2), mod(15, 4), 0));
		monsters.add(new Monster("Goblin", Size.small, 2, mod(8, 2), mod(16, 3), 3));
		monsters.add(new Monster("Goblin", Size.small, 2, mod(8, 2), mod(18, 3), 3));
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
		items.add(new Item("Health Potion", 3));
		items.add(new Item("Mana Potion  ", 3));
		items.add(new Item("Rat Skin     ", 1));
		items.add(new Item("Troll Hide   ", 7));
		items.add(new Item("Spider Venom ", 5));
		items.add(new Item("Gold Piece x1", 5));
		items.add(new Item("Gold Piece x3", 5));
		items.add(new Item("Gold Piece x5", 5));
		items.add(new Item("Gold Piece x10", 5));
		items.add(new Item("Food         ", 5));
		items.add(new Item("Food         ", 5));
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
		baseDamage = bd + (m.getMod() / 2);
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
