public class Key {
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

	// draw settings
	public static boolean drawRays = false;
	public static boolean drawRoomNumbers = false;
	public static boolean drawPathMap = false;
	public static boolean drawGrid = false;

	// debug tools
	public static boolean showDebug = false;
	public static boolean showHallMapping = false;
	public static boolean showPathDebug = false;
	public static boolean showErrors = false;

	public static boolean isWall(int key) {
		if (key == sideWall || key == cornerWall)
			return true;
		else
			return false;
	}
}
