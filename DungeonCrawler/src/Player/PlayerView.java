package Player;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;

import DataStructures.Location;
import Settings.Key;

public class PlayerView {
	float translateX;
	float prevTranslateX;
	float translateY;
	float prevTranslateY;
	float scale;
	float prevScale;

	public PlayerView() {
		translateX = prevTranslateX = 0;
		translateY = prevTranslateY = 0;
		scale = prevScale = 2;
	}

	public PlayerView(float x, float y, float inScale) {
		translateX = prevTranslateX = x;
		translateY = prevTranslateY = y;
		scale = prevScale = inScale;
	}

	public PlayerView(Location loc, float inScale) {
		translateX = prevTranslateX = loc.getX();
		translateY = prevTranslateY = loc.getY();
		scale = prevScale = inScale;
	}

	public void update(Player p) {
		prevTranslateX = translateX;
		prevTranslateY = translateY;
		translateX = (Key.resWidth / 2) - p.getLoc().getX();
		translateY = (Key.resHeight / 2) - p.getLoc().getY();
	}

	public AffineTransform draw(Dimension screen) {
		AffineTransform temp = new AffineTransform();

		// centers translation so scale is about the center
		// temp.translate(screen.getWidth() / 2, screen.getHeight() / 2);
		// temp.scale(scale, scale);
		// temp.translate(-screen.getWidth() / 2, -screen.getHeight() / 2);

		temp.translate(Key.resWidth / 2, Key.resHeight / 2);
		temp.scale(scale, scale);
		temp.translate(-Key.resWidth / 2, -Key.resHeight / 2);

		// translates the map so the player is in the center
		temp.translate(translateX, translateY);

		return temp;
	}

	public float getTraslateX() {
		return translateX;
	}

	public float getTraslateY() {
		return translateY;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float val) {
		scale = val;
	}

	public boolean samePlayerView() {
		if (translateX == prevTranslateX && translateY == prevTranslateY && scale == prevScale)
			return true;
		return false;
	}
}
