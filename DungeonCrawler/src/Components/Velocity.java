package Components;

import java.io.Serializable;

import com.artemis.Component;

public class Velocity extends Component implements Serializable{
	private static final long serialVersionUID = -4755955322683156700L;
	float xVal;
	float yVal;

	/**
	 * 
	 * @param mag
	 *        - magnitude of velocity
	 * @param dir
	 *        - 0 being the right side of x(right of center)
	 */
	public Velocity(float mag, double dir) {
		xVal = (float) Math.cos(dir % (2 * (Math.PI)));
		yVal = (float) Math.sin(dir % (2 * (Math.PI)));
	}

	public Velocity(float xVal, float yVal) {
		this.xVal = xVal;
		this.yVal = yVal;
	}

	public float getXVector() {
		return xVal;
	}

	public float getYVector() {
		return yVal;
	}

	public void setVelocity(float xVal, float yVal) {
		this.xVal = xVal;
		this.yVal = yVal;
	}

	public void setXVector(float xVal) {
		this.xVal = xVal;
	}

	public void setYVector(float yVal) {
		this.yVal = yVal;
	}

}
