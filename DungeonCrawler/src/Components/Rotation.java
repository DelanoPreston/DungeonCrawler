package Components;

import com.artemis.Component;

public class Rotation extends Component {
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
