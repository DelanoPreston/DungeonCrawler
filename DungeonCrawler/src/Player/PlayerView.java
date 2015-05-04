package Player;

import DataStructures.Location;

public class PlayerView {
	float translateX;
	float prevTranslateX;
	float translateY;
	float prevTranslateY;
	float scale;
	float prevScale;

	public PlayerView(){
		translateX = prevTranslateX = 0;
		translateY = prevTranslateY = 0;
		scale = prevScale = 2;
	}
	
	public PlayerView(float x, float y, float inScale) {
		translateX = prevTranslateX = x;
		translateY = prevTranslateY = y;
		scale = prevScale = inScale;
	}

	public PlayerView(Location loc, float inScale) {
		translateX = prevTranslateX = loc.getX();
		translateY = prevTranslateY = loc.getY();
		scale = prevScale = inScale;
	}
	
	public float getTraslateX(){
		return translateX;
	}
	
	public float getTraslateY(){
		return translateY;
	}
	
	public float getScale(){
		return scale;
	}
	
//	public void draw(Graphics2D g2D, Map map) {
//		
//	}
}
