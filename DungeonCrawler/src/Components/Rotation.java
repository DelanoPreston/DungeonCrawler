package Components;

import java.io.Serializable;

import com.artemis.Component;

public class Rotation extends Component implements Serializable{
	private static final long serialVersionUID = 6925229444615272336L;
	float rotation;

	public Rotation() {
		this(0f);
	}

	public Rotation(float rotation) {
		this.rotation = rotation;
	}

	public float getRot() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void addRotation(float addRot) {
		this.rotation += addRot;
	}
}
