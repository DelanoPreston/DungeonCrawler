package Components;

import org.newdawn.slick.util.pathfinding.Mover;

import com.artemis.Component;

/**
 * 
 * @author Preston Delano - 0 will be a normal pathfinder 1 will be a tunneler
 */
public class MoverComp extends Component implements Mover {
	int moverKey;

	public MoverComp() {
		this(0);
	}

	public MoverComp(int moverKey) {
		this.moverKey = moverKey;
	}

	public int getKey() {
		return moverKey;
	}

	public void setKey(int key) {
		this.moverKey = key;
	}
}
