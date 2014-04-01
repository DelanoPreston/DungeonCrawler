package Components;

public class VisionComp {
	float radius;
	float fieldOfFocus;
	//Possibly a num from 0 to 1 to describe rear radius of view 

	public VisionComp() {
		this(25f, (float) Math.PI);
	}

	public VisionComp(float radius, float fof) {
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
