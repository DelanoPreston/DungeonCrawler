package Factory;


public class MapFactory {
	// static MapComp mapComp;
	//
	// public static Entity createDungeon(World world, int width, int height, int roomNum) {
	// mapComp = new MapComp(width, height);
	// // ************************************************************************
	// // initialize the map - already initialized
	// // ************************************************************************
	// // for (int y = 0; y < mapKey.length; y++) {
	// // for (int x = 0; x < mapKey[0].length; x++) {
	// // setCell(x, y, Key.unused);
	// // }
	// // }
	// // ************************************************************************
	// // this creates the rooms
	// // ************************************************************************
	// for (int r = 0; mapComp.getRooms().size() < roomNum && r < 2500; r++) {
	// // random value that decides default room size
	// int randVal = ContentBank.random.nextInt(100);
	// String roomType = "none";
	// int startRoomWidth = 0;
	// int startRoomHeight = 0;
	// // these if cases, determine if the room is Large, medium, or small
	// if (randVal < 40) {
	// roomType = "Large";
	// startRoomWidth = 10;
	// startRoomHeight = 10;
	// } else if (randVal < 70) {
	// roomType = "Medium";
	// startRoomWidth = 8;
	// startRoomHeight = 8;
	// } else {
	// roomType = "Small";
	// startRoomWidth = 6;
	// startRoomHeight = 6;
	// }
	// // gets random room
	//
	// int tempWidth = ContentBank.random.nextInt(startRoomWidth) + startRoomWidth;
	// int tempHeight = ContentBank.random.nextInt(startRoomHeight) + startRoomHeight;
	// int tempX = ContentBank.random.nextInt(width - (1 + tempWidth));
	// int tempY = ContentBank.random.nextInt(height - (1 + tempHeight));
	// createRoom(roomType, tempX, tempY, tempWidth, tempHeight);
	// }
	//
	// // ************************************************************************
	// // connect rooms - with halls, for every room
	// // ************************************************************************
	// for (int r = 0; r < mapComp.getRooms().size(); r++) {
	// // -check that you can get to each other room
	// for (int tr = r + 1; tr < mapComp.getRooms().size(); tr++) {
	// // redraw the pathmap, so the pathfinder will use already existing halls and rooms
	// createPathMap();
	// AStarPathFinder pf = new AStarPathFinder(mapComp, 10000, false);
	// if (r != tr) {
	// // start and end coords
	// int sx = (int) mapComp.getRooms().get(r).getCenter().getX();
	// int sy = (int) mapComp.getRooms().get(r).getCenter().getY();
	// int ex = (int) mapComp.getRooms().get(tr).getCenter().getX();
	// int ey = (int) mapComp.getRooms().get(tr).getCenter().getY();
	// Path pCheck = pf.findPath(new MoverComp(Key.pathFinderRoomCheck), sx, sy, ex, ey);
	// // if there was no path from room r to room tr
	// if (pCheck == null) {
	// // tunnel
	// Path p = null;
	// // redraw the pathmap, so the pathfinder will use already existing halls and rooms
	// createPathMap();
	// AStarPathFinder tpf = new AStarPathFinder(mapComp, (width * height) / ((mapComp.getRooms().size() * 2)), false);// 4
	// p = tpf.findPath(new MoverComp(Key.pathFinderRoomTunneler), sx, sy, ex, ey);
	// if (p != null) {
	// if (Key.showDebug && Key.showHallMapping) {
	// System.out.println("from room: " + r + " to " + tr);
	// }
	// // follow the path to get to the room, creating a hall as you go
	// for (int pathKey = 0; pathKey < p.getLength(); pathKey++) {
	// if (mapComp.isCell(p.getX(pathKey), p.getY(pathKey), Key.sideWall)) {
	// mapComp.setCell(p.getX(pathKey), p.getY(pathKey), Key.door);
	// } else if (mapComp.isCell(p.getX(pathKey), p.getY(pathKey), Key.unused)) {
	// mapComp.setCell(p.getX(pathKey), p.getY(pathKey), Key.floor);
	// }
	// }
	// }
	// } else {
	// // awesome
	// if (Key.showDebug && Key.showHallMapping) {
	// System.out.println("path from room: " + r + " to " + tr + " is valid");
	// }
	// }
	// }
	// }
	// }
	//
	// // ************************************************************************
	// // this should wrap all the halls with walls - and removes double doors
	// // this loop goes through the entire mapKey, except row 0, column 0, maxheight - 1, and maxwidth - 1
	// // ************************************************************************
	// for (int y = 1; y < height - 1; y++) {
	// for (int x = 1; x < width - 1; x++) {
	// if (mapComp.isCell(x, y, Key.unused)) {
	// if (mapComp.isCell(x + 1, y, Key.floor) || mapComp.isCell(x - 1, y, Key.floor) || mapComp.isCell(x, y + 1, Key.floor)
	// || mapComp.isCell(x, y - 1, Key.floor)) {
	// mapComp.setCell(x, y, Key.sideWall);
	// }
	//
	// } else if (mapComp.isCell(x, y, Key.door)) {
	// // this removes double doors
	// if (mapComp.isCell(x + 1, y, Key.door) || mapComp.isCell(x - 1, y, Key.door) || mapComp.isCell(x, y + 1, Key.door)
	// || mapComp.isCell(x, y - 1, Key.door)) {
	// if (mapComp.isCell(x + 1, y, Key.floor) && mapComp.isCell(x - 1, y, Key.floor) || mapComp.isCell(x, y + 1, Key.floor)
	// && mapComp.isCell(x, y - 1, Key.floor)) {
	// mapComp.setCell(x, y, Key.sideWall);
	// } else {
	// mapComp.setCell(x, y, Key.floor);
	// }
	// }
	// }
	// }
	// }
	//
	// // ************************************************************************
	// // this removes awkward walls (surrounded by 3 floor tiles
	// // ************************************************************************
	// for (int y = 1; y < height - 1; y++) {
	// for (int x = 1; x < width - 1; x++) {
	// if (mapComp.isCell(x, y, Key.sideWall)) {
	// if (mapComp.isCell(x + 1, y, Key.floor) && mapComp.isCell(x - 1, y, Key.floor)) {
	// if (mapComp.isCell(x, y + 1, Key.floor) || mapComp.isCell(x, y - 1, Key.floor))
	// mapComp.setCell(x, y, Key.floor);
	// } else if (mapComp.isCell(x, y + 1, Key.floor) && mapComp.isCell(x, y - 1, Key.floor)) {
	// if (mapComp.isCell(x + 1, y, Key.floor) || mapComp.isCell(x - 1, y, Key.floor))
	// mapComp.setCell(x, y, Key.floor);
	// }
	// }
	// }
	// }
	//
	// // ************************************************************************
	// // this adds the walls to the walllist
	// // ************************************************************************
	// int tS = ContentBank.tileSize;
	// for (int y = 0; y < height; y++) {
	// for (int x = 0; x < width; x++) {
	// if (Key.isWall(mapComp.checkCell(x, y))) {// || tempKey[y][x] == 1 || tempKey[y][x] == 2) {
	// mapComp.addWall(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), true));
	// } else if (mapComp.isCell(x, y, Key.door)) {
	// mapComp.addWall(new MapTile(new Rectangle(x * tS, y * tS, tS, tS), false));
	// }
	// }
	// }
	//
	// // ************************************************************************
	// // for debugging purposes
	// // ************************************************************************
	// createPathMap();
	//
	// // ************************************************************************
	// // create the entitiy to return
	// // ************************************************************************
	// Entity e = world.createEntity();
	//
	// e.addComponent(mapComp);
	//
	// return e;
	// }
	//
	// public static boolean createRoom(String roomType, int rx, int ry, int rwidth, int rheight) {
	// Room tempRoom = new Room(roomType, rx, ry, rwidth, rheight);
	// boolean spaceValid = true;
	// // this checks if the space is valid
	// for (int y = ry; y < ry + rheight; y++) {
	// for (int x = rx; x < rx + rwidth; x++) {
	// if (!mapComp.isCell(x, y, Key.unused) && !Key.isWall(mapComp.checkCell(x, y))) {// this used to be just side walls
	// spaceValid = false;
	// }
	// }
	// }
	// // if the space is valid then it creates the room
	// if (spaceValid) {
	// for (int y = ry; y < ry + rheight; y++) {
	// for (int x = rx; x < rx + rwidth; x++) {
	// // this if gets the border of the room and sets them to side walls
	// if (y == ry || y == ry + rheight - 1 || x == rx || x == rx + rwidth - 1) {
	// // this gets the corners of the walls and sets them to corner walls
	// if (y <= ry && x <= rx || y <= ry && x >= rx + rwidth - 1 || y >= ry + rheight - 1 && x <= rx || y >= ry + rheight - 1
	// && x >= rx + rwidth - 1)
	// mapComp.setCell(x, y, Key.cornerWall);
	// // this makes sure if its a corner wall it leaves it alone
	// else if (!mapComp.isCell(x, y, Key.cornerWall))
	// mapComp.setCell(x, y, Key.sideWall);
	// } else {
	// // everything inside the room is floor right now, or at least initially
	// mapComp.setCell(x, y, Key.floor);
	// }
	// }
	// }
	// // the room was added to the map, so it is added to the list of rooms
	// mapComp.addRoom(tempRoom);
	// // returns true for placing the room
	// return true;
	// }
	// // returns false because there is no space for the room
	// return false;
	// }
	//
	// public static void createPathMap() {
	// for (int y = 0; y < mapComp.getHeightInTiles(); y++) {
	// for (int x = 0; x < mapComp.getWidthInTiles(); x++) {
	// if (mapComp.isCell(x, y, Key.unused) || mapComp.isCell(x, y, Key.floor)) {
	// mapComp.setCost(x, y, 4);
	// // } else if (isCell(x, y, Key.floor)) {
	// // map[y][x].setCost(4);//2
	// } else if (mapComp.isCell(x, y, Key.door)) {
	// mapComp.setCost(x, y, 1);
	// } else if (Key.isWall(mapComp.checkCell(x, y))) {
	// mapComp.setCost(x, y, calcWallCost(x, y));
	// // map[y][x].setCost(calcWallCost(x, y));
	// if (mapComp.getCost(x, y) < 5)
	// mapComp.setCost(x, y, 5);
	// }
	// }
	// }
	// }
	//
	// private static int calcWallCost(int x, int y) {
	// int tempCost = 0;
	// // makes sure its in the bound of the array
	// int startX = Math.min(Math.max(x - 2, 0), mapComp.getHeightInTiles() - 1);
	// int startY = Math.min(Math.max(y - 2, 0), mapComp.getWidthInTiles() - 1);
	// int endX = Math.min(Math.max(x + 2, 0), mapComp.getHeightInTiles() - 1);
	// int endY = Math.min(Math.max(y + 2, 0), mapComp.getWidthInTiles() - 1);
	// // the t is for temp
	// for (int ty = startY; ty < endY; ty++) {
	// for (int tx = startX; tx < endX; tx++) {
	// if (mapComp.isCell(tx, ty, Key.sideWall))
	// // if (Key.isWall(checkCell(x, y)))
	// tempCost++;
	// if (mapComp.isCell(tx, ty, Key.cornerWall))
	// tempCost += 2;
	// if (mapComp.isCell(x, y, Key.door))
	// tempCost += 5;
	// }
	// }
	// return tempCost;
	// }
}
