package Components;

import java.io.Serializable;

import org.newdawn.slick.util.pathfinding.Path;

import Settings.ContentBank;

import com.artemis.Component;

public class AIComp extends Component implements Serializable{
	private static final long serialVersionUID = 6989569163578390599L;
	Path path;
	int index;

	public AIComp() {
		path = null;
		index = -1;
	}

	public float getWindowXPathAt(int inIndex) {
		return (float) path.getX(inIndex) * ContentBank.tileSize;
	}

	public float getWindowYPathAt(int inIndex) {
		return (float) path.getY(inIndex) * ContentBank.tileSize;
	}

	public int getIndex() {
		return index;
	}

	public Path getPath() {
		return path;
	}

	public void incrementIndex() {
		index++;
	}

	public void setPath(Path path) {
		this.path = path;
		this.index = 0;
	}
}
