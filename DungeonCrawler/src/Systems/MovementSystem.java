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
	ComponentMapper<Position> pc;
	@Mapper
	ComponentMapper<Velocity> vc;

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class));
	}

	protected void process(Entity entity) {
		// Get the components from the entity using component mappers.
		Position pos = pc.get(entity);
		Velocity vel = vc.get(entity);

		// Update the position.
		pos.addX(vel.getXVector() * world.getDelta());
		pos.addY(vel.getYVector() * world.getDelta());
		entity.changedInWorld();

	}
}
