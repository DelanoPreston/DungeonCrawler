package Systems;

import Components.PositionComp;
import Components.VelocityComp;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<PositionComp> pm;
	@Mapper ComponentMapper<VelocityComp> vm;
	
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.getAspectForAll(PositionComp.class, VelocityComp.class));
	}
	
	protected void process(Entity e) {
		// Get the components from the entity using component mappers.
		PositionComp position = pm.get(e);
		VelocityComp velocity = vm.get(e);
		
		// Update the position.
		position.addX(velocity.getXVector() * world.getDelta());
		position.addY(velocity.getYVector() * world.getDelta());
	}
}
