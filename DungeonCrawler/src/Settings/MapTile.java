package Settings;


import java.awt.Rectangle;

public class MapTile {
	Rectangle positionSize;
	boolean solid;
	
	public int getX(){
		return positionSize.x;
	}
	
	public int getY(){
		return positionSize.y;
	}
	
	public int getSize(){
		return positionSize.height;
	}
	
	public MapTile(Rectangle positionSize, boolean solid){
		this.positionSize = positionSize;
		this.solid = solid;
	}
}
