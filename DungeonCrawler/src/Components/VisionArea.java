package Components;

import java.io.Serializable;

import com.artemis.Component;

public class VisionArea extends Component implements Serializable{
	private static final long serialVersionUID = -5675923558377793004L;
	float radius;
	float fieldOfFocus;

	// Possibly a num from 0 to 1 to describe rear radius of view

	public VisionArea() {
		this(25f, (float) Math.PI);
	}

	public VisionArea(float radius, float fof) {
		this.radius = radius;
		this.fieldOfFocus = fof;
	}

	public float getRadius() {
		return radius;
	}

	public float getFOF() {
		return fieldOfFocus;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void setFOF(float fof) {
		this.fieldOfFocus = fof;
	}
}
