package Entities;

import java.util.ArrayList;
import java.util.List;

public class AIManager {
	List<MoveableEntity> monsters = new ArrayList<>();
	
	public AIManager(){
		
	}
	
	public void update(){
//		Player.location - get the player location so I can check it against monster location
		for(MoveableEntity mon : monsters){
//			if(mon.location.getDistance(loc))
//			mon.update();
		}
	}
}
