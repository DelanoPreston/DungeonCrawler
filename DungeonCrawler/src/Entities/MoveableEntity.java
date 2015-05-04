package Entities;

import DataStructures.Location;

public class MoveableEntity extends Entity {
	private static final long serialVersionUID = 7134108231669813902L;

	protected float rotation;
	protected float maxRotSpeed;
	protected float rotAccel;
	protected float maxRotAcc;
	protected float speed;
	protected float maxSpeed;
	protected float accel;
	protected float maxAcc;

	public MoveableEntity(String name, Location loc) {
		super(name, loc);
	}

	public MoveableEntity() {
		super();
		maxSpeed = .65f;
		maxRotSpeed = .01f;
		maxAcc = .1f;
		maxRotAcc = .1f;
	}

	public void update() {
		rotation = (rotation + rotAccel) % 360;// Math.min(rotation + rotAccel,
												// 360);
		speed += accel;
		if (speed > maxSpeed)
			speed = maxSpeed;
		else if (speed < -maxSpeed)
			speed = -maxSpeed;
		// speed = Math.min(speed + accel, maxSpeed);
		// if (rotAccel == 0f)
		// rotation -= maxRotAcc;
		// if (maxAcc == 0f)
		// accel -= maxAcc;
		location.addMovement(speed, rotation);
	}

	public float getRot() {
		return rotation;
	}

	public void setRot(float rot) {
		rotation = rot;
	}

	public float getRotSpeed() {
		return maxRotSpeed;
	}

	public void setRotSpeed(float rot) {
		maxRotSpeed = rot;
	}

	public float getRotAccel() {
		return rotAccel;
	}

	public float getMaxRotAcc() {
		return maxRotAcc;
	}

	public void setRotAcc(float acc) {
		rotAccel = acc;
	}

	public void setMaxRotAcc(float acc) {
		maxRotAcc = acc;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float rot) {
		speed = rot;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float rot) {
		maxSpeed = rot;
	}

	public float getAccel() {
		return accel;
	}

	public void setAccel(float rot) {
		accel = rot;
	}

	public float getMaxAcc() {
		return maxAcc;
	}

	public void setMaxAcc(float rot) {
		maxAcc = rot;
	}

}
