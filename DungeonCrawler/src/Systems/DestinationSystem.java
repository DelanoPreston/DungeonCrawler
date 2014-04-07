package Systems;

import Components.AIComp;
import Components.PositionComp;
import Components.VelocityComp;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class DestinationSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<AIComp> aic;
	@Mapper
	ComponentMapper<VelocityComp> vc;
	@Mapper
	ComponentMapper<PositionComp> pc;

	@SuppressWarnings("unchecked")
	public DestinationSystem() {
		super(Aspect.getAspectForAll(AIComp.class, VelocityComp.class, PositionComp.class));
	}

	@Override
	protected void process(Entity entity) {
		AIComp aiC = aic.get(entity);
		VelocityComp vC = vc.get(entity);
		PositionComp pC = pc.get(entity);

		float xDiff = pC.getWindowX() - aiC.getWindowXPathAt(aiC.getIndex());
		float yDiff = pC.getWindowY() - aiC.getWindowYPathAt(aiC.getIndex());

		double angle = Math.atan2(yDiff, xDiff);

		vC.setXVector((float) Math.cos(angle));
		vC.setYVector((float) Math.sin(angle));
	}

}
