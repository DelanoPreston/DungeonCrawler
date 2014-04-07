package Settings;

import java.awt.Rectangle;

public class MapTile {
	Rectangle positionSize;
	boolean solid;

	public float getX() {
		return (float) positionSize.x;
	}

	public float getY() {
		return (float) positionSize.y;
	}

	public float getSize() {
		return (float) positionSize.height;
	}

	public MapTile(Rectangle rectangle, boolean solid) {
		this.positionSize = rectangle;
		this.solid = solid;
	}
}
