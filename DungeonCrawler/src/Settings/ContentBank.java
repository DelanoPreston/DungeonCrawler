package Settings;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ContentBank {
	public static int tileSize = 64;
	public static Image sword;
	public static Image shield;
	public static Image axe;

	public static Image[] woodenWalls;
	public static Image[] stoneWalls;
	public static Image[] dungeonTiles = new Image[4];
	public static Image[] items = new Image[4];
	
	public static void setDungeonTiles() {
		dungeonTiles = new Image[4];

		BufferedImage bigImg = null;
		int index = 0;

		try {
			bigImg = ImageIO.read(new File("Images/SurvivorTileMap.png"));
			index = 0;
			dungeonTiles = new Image[4];

			for (int y = 0; y <= 1; y++) {
				for (int x = 0; x <= 1; x++) {
					dungeonTiles[index] = (Image) bigImg.getSubimage(x * Key.tileSize, y * Key.tileSize, Key.tileSize, Key.tileSize);
					index++;
				}
			}
			System.out.println("got tiles done");

			bigImg = ImageIO.read(new File("Images/Items.png"));
			index = 0;
			items = new Image[4];

			for (int y = 0; y <= 1; y++) {
				for (int x = 0; x <= 1; x++) {
					int tileSize = 32;
					items[index] = (Image) bigImg.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
					index++;
				}
			}
			System.out.println("got items done");

		} catch (Exception e) {

		}
	}
	
	public static void loadImages() {
		BufferedImage bigImg = null;

		try {
			bigImg = ImageIO.read(new File("Images/WoodenSword.png"));
			sword = (Image) bigImg;

			bigImg = ImageIO.read(new File("Images/IronShield.png"));
			shield = (Image) bigImg;
			
			bigImg = ImageIO.read(new File("Images/IronAxe.png"));
			axe = (Image) bigImg;

//			for (int y = 0; y < 8; y++) {
//				for (int x = 0; x < 8; x++) {
//					buttonIcons[index] = (Image) bigImg.getSubimage(x * 64, y * 64, 64, 64);
//					index++;
//				}
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
