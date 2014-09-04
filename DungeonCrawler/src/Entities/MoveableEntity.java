package Entities;

public class MoveableEntity extends Entity{
	private static final long serialVersionUID = 7134108231669813902L;
	
	float rotation;
	float maxRotSpeed;
	float speed;
	float maxSpeed;
	float accel;
	float maxAcc;
	
	public MoveableEntity(){
		super();
	}
	
	public float getRot(){
		return rotation;
	}
	public void setRot(float rot){
		rotation = rot;
	}
	public float getRotSpeed(){
		return maxRotSpeed;
	}
	public void setRotSpeed(float rot){
		maxRotSpeed = rot;
	}
	public float getSpeed(){
		return speed;
	}
	public void setSpeed(float rot){
		speed = rot;
	}
	public float getMaxSpeed(){
		return maxSpeed;
	}
	public void setMaxSpeed(float rot){
		maxSpeed = rot;
	}
	public float getAccel(){
		return accel;
	}
	public void setAccel(float rot){
		accel = rot;
	}
	public float getMaxAcc(){
		return maxAcc;
	}
	public void setMaxAcc(float rot){
		maxAcc = rot;
	}
	
}
