package Systems;

import Components.Position;
import Components.Velocity;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class RotationSystem extends EntityProcessingSystem{

	public RotationSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class));
	}

	@Override
	protected void process(Entity arg0) {
		// TODO Auto-generated method stub
		
	}

}
