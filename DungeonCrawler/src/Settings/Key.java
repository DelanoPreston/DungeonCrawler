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
	public static final int unused = 0;
	public static final int floor = 1;
	public static final int hallwayFloor = 2;
	public static final int sideWall = 4;
//	public static final int cornerWall = 5;
	public static final int lockedWall = 5;// for map making pathfinding
											// purposes - a
	// wall that cannot become a door
	public static final int door = 12;
	public static final int stone = 20;

	// resolution of ray casting
	public static int rayCastResolution = 360;
	public static int rayCastingDistance = 75;
	
	// size of map
	public static int width = 64;
	public static int height = 64;
	public static int tileSize = 6;

	// map settings
	public static int numOfRooms = 320;

	// draw settings
	// vision things
	public static boolean drawRays = false;
	public static boolean drawClosestIntersect = true;
	// map things
	public static boolean drawRoomNumbers = false;
	public static boolean drawRoomCenters = false;
	public static boolean drawTunnelingPaths = false;
	public static boolean drawPathMap = true;// throws error when true
	public static boolean drawGrid = false;
	public static boolean drawMap = true;
	// miniMap things
	public static boolean drawMiniMap = true;
	public static boolean drawGamePlay = true;
	
	// fog of war things
	public static boolean drawFogOfWar = true;
	public static boolean drawMMFogOfWar = false;// true makes minimap not work
													// for now

	// debug tools
	// public static boolean runSlickGame = true;
	public static boolean showDebug = true;
	public static boolean showHallMapping = true;// seems to do nothing - i
													// think it just prints to
													// the console, info about
													// making halls
	public static boolean showPathDebug = false;
	public static boolean showErrors = false;

	// log enables
	public static int logSystemManagement = 3000;
	public static int logEntityManagement = 3001;
	public static int logComponentManagement = 3002;

	// public static boolean isWall(int key) {
	// if (key == sideWall || key == cornerWall || key == lockedWall)
	// return true;
	// else
	// return false;
	// }
}
