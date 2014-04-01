package Components;

import com.artemis.Component;

public class VelocityComp extends Component {
	float xVal;
	float yVal;

	/**
	 * 
	 * @param mag
	 *        - magnitude of velocity
	 * @param dir
	 *        - 0 being the right side of x(right of center)
	 */
	public VelocityComp(float mag, double dir) {
		xVal = (float) Math.cos(dir % (2 * (Math.PI)));
		yVal = (float) Math.sin(dir % (2 * (Math.PI)));
	}

	public VelocityComp(float xVal, float yVal) {
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
