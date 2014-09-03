package Settings;

import java.util.Random;

public class Key {
	public static Random random = new Random();

	// pathfinder types
	public static int pathFinderRoomCheck = -100;
	public static int pathFinderRoomTunneler = -101;
	public static int player = 1000;
	public static int monster1 = 1500;

	// tile keys
	public static int unused = 0;
	public static int floor = 1;
	public static int sideWall = 4;
	public static int cornerWall = 5;
	public static int door = 12;
	public static int stone = 20;

	// resolution of ray casting
	public static int rayCastResolution = 36;

	// size of map
	public static int width = 50;
	public static int height = 25;
	public static int tileSize = 10;
	
	// map settings
	public static int numOfRooms = 12;

	// draw settings
	// vision things
	public static boolean drawRays = true;
	public static boolean drawClosestIntersect = true;
	// map things
	public static boolean drawRoomNumbers = true;
	public static boolean drawPathMap = false;//throws error when true
	public static boolean drawGrid = false;
	public static boolean drawMap = true;
	// miniMap things
	public static boolean drawMiniMap = true;
	public static boolean drawGamePlay = false;//when true map does not work for now
	// fog of war things
	public static boolean drawFogOfWar = true;//not working as of now
	public static boolean drawMMFogOfWar = false;//true makes minimap not work for now

	// debug tools
//	public static boolean runSlickGame = true;
	public static boolean showDebug = false;
	public static boolean showHallMapping = false;//seems to do nothing
	public static boolean showPathDebug = false;//seems to do nothing
	public static boolean showErrors = false;

	// log enables
	public static int logSystemManagement = 3000;
	public static int logEntityManagement = 3001;
	public static int logComponentManagement = 3002;

	public static boolean isWall(int key) {
		if (key == sideWall || key == cornerWall)
			return true;
		else
			return false;
	}
}
