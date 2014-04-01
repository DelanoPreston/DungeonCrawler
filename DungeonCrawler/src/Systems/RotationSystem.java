package Systems;

import Components.PositionComp;
import Components.VelocityComp;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class RotationSystem extends EntityProcessingSystem{

	public RotationSystem() {
		super(Aspect.getAspectForAll(PositionComp.class, VelocityComp.class));
	}

	@Override
	protected void process(Entity arg0) {
		// TODO Auto-generated method stub
		
	}

}
