package Settings;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import DataStructures.ID;

public class Key {
	public static Random random = new Random();

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

	// input
	public static float sensitivity = 1f;
	public static boolean lockedMovementType = false;
	
	//
	public static int resWidth = 800;
	public static int resHeight = 600;
	
	//
	public static boolean minimapFollowPlayer = true;

	// pathfinder types
	public static int pathFinderRoomCheck = -100;
	public static int pathFinderRoomTunneler = -101;
	public static int pathFinderGamePlay = -102;
	// public static int player = 1000;
	// public static int monster1 = 1500;

	// damage string keys
	// physical
	public static final String Eslashing = "Slashing";
	public static final String Eblugeoning = "Blugeoning";
	public static final String Epiercing = "Piercing";
	// magical
	public static final String Efire = "Fire";
	public static final String Ewater = "Water";
	public static final String Eearth = "Earth";
	public static final String Eair = "Air";
	// damage over time physical
	public static final String Ebleeding = "bleeding";
	public static final String EbrokenBone = "broken_bone";
	// damage over time magical
	public static final String Ecold = "Cold";
	public static final String Eburning = "burning";

	// Quality values
	public static final int QShoddy = 15;
	public static final int QPoor = 25;
	public static final int QGood = 40;
	public static final int QFair = 60;
	public static final int QExcellent = 80;
	public static final int QPerfect = 100;
	public static final int QLegendary = 125;

	// stat names
	public static final String statVision = "Vision";

	// tile keys
	public static final ID nullID = new ID();
	public static final ID unused = new ID(0, 0, 0);
	// floors
	public static final ID floor = new ID(1, 0, 1);
	public static final ID hallwayFloor = new ID(1, 1, 1);
	// walls
	public static final ID sideWall = new ID(2, 0, 2);
	public static final ID lockedWall = new ID(2, 1, 2);
	// doors
	public static final ID door = new ID(3, 0, 3);
	public static final ID doorClosed = new ID(3, 1, 3);

	// resolution of ray casting
	public static int rayCastResolution = 360;
	public static int rayCastingDistance = 100;// 75;
	public static int monsterVisionResoultion = 32;

	// size of map
	public static int width = 50;
	public static int height = 50;
	public static int tileSize = 16;
	public static int chunkTiles = 12;
	public static int renderChunkDist = rayCastingDistance * 5 /2;
	
	//inventory size stuff
	public static int tileSizeInventory = 64;
	

	// size of MiniMap
	public static int mmWidth = 24;
	public static int mmHeight = 24;
	public static int mmtileSize = 6;

	// map settings
	public static int numOfRooms = 320;

	// draw settings
	// vision things
	public static boolean drawRays = false;

	// map things
	public static boolean drawRoomNumbers = false;
	public static boolean drawRoomCenters = false;
	public static boolean drawTunnelingPaths = false;
	public static boolean drawWallLines = false;
	public static boolean drawDoorLines = false;
	public static boolean drawPathMap = false;
	public static boolean drawGrid = false;
	public static boolean drawMap = true;

	// Vision things
	public static boolean useWallRectangles = false;

	// miniMap things
	public static boolean drawMiniMap = true;
	public static boolean drawGamePlay = true;

	// fog of war things
	public static boolean drawFogOfWar = false;
	public static boolean drawMMFogOfWar = true;

	// debug tools
	public static boolean showDebug = true;
	public static boolean showHallMapping = true;
	public static boolean showPathDebug = false;
	public static boolean showErrors = false;

	// log enables
	public static int logSystemManagement = 3000;
	public static int logEntityManagement = 3001;
	public static int logComponentManagement = 3002;
}
