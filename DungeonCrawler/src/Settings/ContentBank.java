package Settings;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ContentBank {
	public static Random random;
	public static int tileSize = 8;
	// public static Image survivorM1;
	// public static Image survivorM2;
	// public static Image survivorM3;
	// public static Image survivorW1;
	// public static Image survivorW2;
	// public static Image survivorW3;
	//
	// public static Image[] woodenWalls;
	// public static Image[] stoneWalls;
	//
	// public static Image[] landTiles;
	// public static Image[] buttonIcons;
	//
	// public static Image woodenAxe;

	public static List<String> firstNameSyllables = new ArrayList<>();

	public static HashMap<String, Image> images = new HashMap<>();

	// public static HashMap<String, Item> items = new HashMap<>();
	// public static HashMap<String, Item> tools = new HashMap<>();
	// public static HashMap<String, Item> food = new HashMap<>();
	// public static HashMap<String, Item> furniture = new HashMap<>();

	public static void ContentLoader() {
		random = new Random();
		loadImages();
		// loadItems();
		loadNames();
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
		Image bigImg = null;

		try {
			bigImg = new Image("Images/tempDude.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		images.put("dude", bigImg);

	}

	public static String createSurvivorName() {
		// creates a first name with 2-3 syllables
		String firstName = "";
		int numOfSyl = random.nextInt(2) + 2;
		for (int i = 0; i < numOfSyl; i++) {
			firstName += firstNameSyllables.get(random.nextInt(firstNameSyllables.size()));
		}
		return firstName;
	}

	public static org.newdawn.slick.Image getImage(String key) {
		if (images.containsKey(key)) {
			return (Image) images.get(key);
		} else
			return null;
	}
}
