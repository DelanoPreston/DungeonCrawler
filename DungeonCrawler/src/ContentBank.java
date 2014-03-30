

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class ContentBank {
	public static Random random;
	public static int tileSize = 8;
	public static Image survivorM1;
	public static Image survivorM2;
	public static Image survivorM3;
	public static Image survivorW1;
	public static Image survivorW2;
	public static Image survivorW3;

	public static Image[] woodenWalls;
	public static Image[] stoneWalls;

	public static Image[] landTiles;
	public static Image[] buttonIcons;

	public static Image woodenAxe;

	public static List<String> firstNameSyllables = new ArrayList<>();

//	public static HashMap<String, Item> items = new HashMap<>();
//	public static HashMap<String, Item> tools = new HashMap<>();
//	public static HashMap<String, Item> food = new HashMap<>();
//	public static HashMap<String, Item> furniture = new HashMap<>();

	public static void ContentLoader() {
		random = new Random();
//		loadImages();
//		loadItems();
//		loadNames();
	}

	private static void loadNames() {
		firstNameSyllables.add("mon");
		firstNameSyllables.add("fay");
		firstNameSyllables.add("shi");
		firstNameSyllables.add("zag");
		firstNameSyllables.add("blarg");
		firstNameSyllables.add("rash");
		firstNameSyllables.add("izen");
		firstNameSyllables.add("wha");
		firstNameSyllables.add("she");
		firstNameSyllables.add("gui");
		firstNameSyllables.add("des");
		firstNameSyllables.add("ond");
		firstNameSyllables.add("sha");

		// // animals
		// Animal pig1 = new Animal("Pig", 20, 80.0, true);
		// TameAnimal pig = new TameAnimal("Pig", 20, 80.0, true);
		// TameAnimal sheep = new TameAnimal("Sheep", 20, 50.0, true);
		// WildAnimal deer = new WildAnimal("Deer", 20, 60.0, true);
		// WildAnimal elk = new WildAnimal("Elk", 30, 90.0, true);
		// WildAnimal moose = new WildAnimal("Moose", 50, 150.0, true);
		// WildAnimal bear = new WildAnimal("Bear", 75, 140.0, true);
	}

	private static void loadImages() {
		BufferedImage bigImg = null;
		int index = 0;

		try {
			bigImg = ImageIO.read(new File("Images/SurvivorTileMap1.png"));
			index = 0;
			landTiles = new Image[5];

			for (int x = 0; x < 5; x++) {
				landTiles[index] = (Image) bigImg.getSubimage(x * 64, 0, 64, 64);
				index++;
			}

			// bigImg = ImageIO.read(new File("Images/ButtonIcons1.png"));
			// index = 0;
			// buttonIcons = new Image[3];
			//
			// for (int y = 0; y < 1; y++) {
			// for (int x = 0; x < 3; x++) {
			// buttonIcons[index] = (Image) bigImg.getSubimage(x * 64, y * 64, 64, 64);
			// index++;
			// }
			// }

			bigImg = ImageIO.read(new File("Images/ButtonIcons1.png"));
			index = 0;
			buttonIcons = new Image[64];

			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					buttonIcons[index] = (Image) bigImg.getSubimage(x * 64, y * 64, 64, 64);
					index++;
				}
			}

			bigImg = ImageIO.read(new File("Images/oie_transparent.png"));

			survivorM1 = bigImg.getSubimage(0, 0, 16, 32);
			survivorM2 = bigImg.getSubimage(0, 32, 16, 32);
			survivorM3 = bigImg.getSubimage(0, 64, 16, 32);
			survivorW1 = bigImg.getSubimage(0, 96, 16, 32);
			survivorW2 = bigImg.getSubimage(0, 128, 16, 32);
			survivorW3 = bigImg.getSubimage(0, 160, 16, 32);

			bigImg = ImageIO.read(new File("Images/Structures1.png"));
			index = 0;
			woodenAxe = bigImg.getSubimage(3 * 16, 0 * 16, 16, 16);

			woodenWalls = new Image[16];
			stoneWalls = new Image[16];
			for (int x = 0; x < 16; x++) {
				woodenWalls[index] = bigImg.getSubimage(x * 64, 0, 64, 64);// entire first row
				stoneWalls[index] = bigImg.getSubimage(x * 64, 64, 64, 64);// entire second row
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	private static void loadItems() {
//		IOClass io = new IOClass();
//		items = io.loadItems(new File(System.getProperty("user.dir") + "/Items/Item"), "Item");
//		tools = io.loadItems(new File(System.getProperty("user.dir") + "/Items/Tool"), "Tool");
//		food = io.loadItems(new File(System.getProperty("user.dir") + "/Items/Food"), "Food");
//		furniture = io.loadItems(new File(System.getProperty("user.dir") + "/Items/Furniture"), "Furniture");
//	}

	public static String createSurvivorName() {
		// creates a first name with 2-3 syllables
		String firstName = "";
		int numOfSyl = random.nextInt(2) + 2;
		for (int i = 0; i < numOfSyl; i++) {
			firstName += firstNameSyllables.get(random.nextInt(firstNameSyllables.size()));
		}
		return firstName;
	}
}
