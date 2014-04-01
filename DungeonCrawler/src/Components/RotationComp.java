package Components;

import com.artemis.Component;

public class RotationComp extends Component {
	float rotation;

	public RotationComp() {
		this(0f);
	}

	public RotationComp(float rotation) {
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
