package Settings;

import java.util.Random;

import DataStructures.ID;

public class Key {
	public static Random random = new Random();

	// input
	public static float sensitivity = 1f;
	public static boolean someMovementType = true;

	//
	public static boolean minimapFollowPlayer = true;

	// pathfinder types
	public static int pathFinderRoomCheck = -100;
	public static int pathFinderRoomTunneler = -101;
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

	// tile keys
	public static final ID nullID = new ID();
	public static final ID unused = new ID(0);
	// floors
	public static final ID floor = new ID(1, 0);
	public static final ID hallwayFloor = new ID(1, 1);
	// walls
	public static final ID sideWall = new ID(5, 0);
	public static final ID lockedWall = new ID(5, 1);
	// doors
	public static final ID door = new ID(12, 0);
	public static final ID doorClosed = new ID(12, 1);

	// resolution of ray casting
	public static int rayCastResolution = 360;
	public static int rayCastingDistance = 75;

	// size of map
	public static int width = 90;
	public static int height = 75;
	public static int tileSize = 8;

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
	public static boolean drawGamePlay = false;

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
