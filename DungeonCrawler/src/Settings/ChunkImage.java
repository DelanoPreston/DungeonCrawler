package Settings;

import java.awt.image.BufferedImage;

public class ChunkImage {
	BufferedImage chunkImage;
	boolean update;

	public ChunkImage() {
		update = false;
	}

	public boolean getUpdate() {
		return update;
	}

	public void setUpdate(boolean in) {
		update = in;
	}

	public BufferedImage getImage() {
		return chunkImage;
	}

	public void setImage(BufferedImage img) {
		update = false;
	}
}
