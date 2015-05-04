package Settings;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ContentBank {
	public static Image player;

	public static void loadImages() {
		//BufferedImage bigImg = null;
		//int index = 0;

		// try {
		// bigImg = ImageIO.read(new File("Images/SurvivorTileMap1.png"));
		// index = 0;
		// landTiles = new Image[5];
		//
		// for (int x = 0; x < 5; x++) {
		// landTiles[index] = (Image) bigImg.getSubimage(x * 64, 0, 64, 64);
		// index++;
		// }
		//
		// bigImg = ImageIO.read(new File("Images/oie_transparent.png"));
		//
		// survivorM1 = bigImg.getSubimage(0, 0, 16, 32);
		// survivorM2 = bigImg.getSubimage(0, 32, 16, 32);
		// survivorM3 = bigImg.getSubimage(0, 64, 16, 32);
		// survivorW1 = bigImg.getSubimage(0, 96, 16, 32);
		// survivorW2 = bigImg.getSubimage(0, 128, 16, 32);
		// survivorW3 = bigImg.getSubimage(0, 160, 16, 32);
		//
		// bigImg = ImageIO.read(new File("Images/Structures1.png"));
		// index = 0;
		// woodenAxe = bigImg.getSubimage(3 * 16, 0 * 16, 16, 16);
		//
		// woodenWalls = new Image[16];
		// stoneWalls = new Image[16];
		// for (int x = 0; x < 16; x++) {
		// woodenWalls[index] = bigImg.getSubimage(x * 64, 0, 64, 64);//entire
		// first row
		// stoneWalls[index] = bigImg.getSubimage(x * 64, 64, 64, 64);//entire
		// second row
		// index++;
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}
}
