package Systems;

import Components.Position;
import Components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Velocity> vm;
	
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class));
	}
	
	protected void process(Entity e) {
		// Get the components from the entity using component mappers.
		Position position = pm.get(e);
		Velocity velocity = vm.get(e);
		
		// Update the position.
		position.addX(velocity.getXVector() * world.getDelta());
		position.addY(velocity.getYVector() * world.getDelta());
	}
}
