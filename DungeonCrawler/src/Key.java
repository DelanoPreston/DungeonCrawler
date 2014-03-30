public class Key {
	//pathfinder types
	public static int pathFinderRoomCheck = -100;
	public static int pathFinderRoomTunneler = -101;

	//entity keys - possibly more for other entity types
	public static int player = 1000;
	public static int monster1 = 1500;

	//tile keys
	public static int unused = 0;
	public static int floor = 1;
	public static int sideWall = 4;
	public static int cornerWall = 5;
	public static int door = 12;
	public static int stone = 20;
	
	//debug tools
	public static boolean drawRays = false;
	public static boolean showPathMap = false;
	public static boolean showDebug = true;
	public static boolean showHallMapping = true;
	public static boolean showPathDebug = false;
	
	public static boolean isWall(int key){
		if(key == sideWall || key == cornerWall)
			return true;
		else
			return false;
	}
}
