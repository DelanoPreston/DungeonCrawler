package Settings;

import java.awt.image.BufferedImage;

import DataStructures.Location;

public class ChunkImage {
	BufferedImage chunkImage;
	Location topLeftCornerLocation;
	boolean update;
	int offsetToCenter;

	public ChunkImage(Location cl, int off) {
		update = true;
		topLeftCornerLocation = cl;
		offsetToCenter = off;
	}

	public boolean getUpdate() {
		return update;
	}

	/**
	 * returns the center location of the chunk
	 */
	public Location getLocation() {
		return topLeftCornerLocation;
	}

	public Location getCenterLocation() {
		return new Location(topLeftCornerLocation.getX() + offsetToCenter, topLeftCornerLocation.getY() + offsetToCenter);
	}

	public void setUpdate(boolean in) {
		update = in;
	}

	public BufferedImage getImage() {
		return chunkImage;
	}

	public void setImage(BufferedImage img) {
		update = false;
		chunkImage = img;
	}
}
