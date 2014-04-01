package Components;

import com.artemis.Component;

public class PositionComp extends Component {
	float x;
	float y;

	public PositionComp() {
		this(0, 0);
	}

	public PositionComp(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void addX(float xVal) {
		this.x += xVal;
	}

	public void addY(float yVal) {
		this.y += yVal;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
}
