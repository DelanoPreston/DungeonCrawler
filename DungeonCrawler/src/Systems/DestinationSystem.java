package Systems;

import Components.AIComp;
import Components.Position;
import Components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class DestinationSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<AIComp> aic;
	@Mapper
	ComponentMapper<Velocity> vc;
	@Mapper
	ComponentMapper<Position> pc;

	@SuppressWarnings("unchecked")
	public DestinationSystem() {
		super(Aspect.getAspectForAll(AIComp.class, Velocity.class, Position.class));
	}

	@Override
	protected void process(Entity entity) {
		AIComp aiC = aic.get(entity);
		Velocity vC = vc.get(entity);
		Position pC = pc.get(entity);

		float xDiff = pC.getWindowX() - aiC.getWindowXPathAt(aiC.getIndex());
		float yDiff = pC.getWindowY() - aiC.getWindowYPathAt(aiC.getIndex());

		double angle = Math.atan2(yDiff, xDiff);

		vC.setXVector((float) Math.cos(angle));
		vC.setYVector((float) Math.sin(angle));
	}

}
