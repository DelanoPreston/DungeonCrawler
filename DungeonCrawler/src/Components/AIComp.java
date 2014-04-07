package Components;

import org.newdawn.slick.util.pathfinding.Path;

import com.artemis.Component;

public class AIComp extends Component {
	Path path;
	int index;

	public float getWindowXPathAt(int inIndex) {
		return (float) path.getX(inIndex);
	}

	public float getWindowYPathAt(int inIndex) {
		return (float) path.getY(inIndex);
	}

	public int getIndex() {
		return index;
	}

	public void setPath(Path path) {
		this.path = path;
		this.index = 0;
	}
}
