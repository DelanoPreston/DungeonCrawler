package Components;

import java.io.Serializable;

import Settings.ContentBank;

import com.artemis.Component;

public class TargetComp extends Component implements Serializable{
	private static final long serialVersionUID = 3679878873151179738L;
	float x;
	float y;

	public TargetComp() {
		this(0, 0);
	}

	public TargetComp(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getWindowX() {
		return x * ContentBank.tileSize;
	}

	public float getY() {
		return y;
	}

	public float getWindowY() {
		return y * ContentBank.tileSize;
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
