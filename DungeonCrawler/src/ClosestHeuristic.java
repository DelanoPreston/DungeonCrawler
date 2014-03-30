


public class ClosestHeuristic implements AStarHeuristic {
	/**
	 * @see AStarHeuristic#getCost(TileBasedMap, UnitMover, int, int, int, int)
	 */
	public float getCost(TileBasedMap map, int type, int x, int y, int tx, int ty) {
		float dx = tx - x;
		float dy = ty - y;

		float result = (float) (Math.sqrt((dx * dx) + (dy * dy)));

		return result;
	}

//	public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
//		float dx = tx - x;
//		float dy = ty - y;
//
//		return dx * dx + dy * dy;
//	}

//	private int minimumCost;
//
//	public ManhattanHeuristic(int minimumCost) {
//		this.minimumCost = minimumCost;
//	}
//
//	public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
//		return this.minimumCost * (Math.abs(x - tx) + Math.abs(y - ty));
//	}
}
