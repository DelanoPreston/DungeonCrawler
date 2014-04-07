package Systems;

import Components.AIComp;
import Components.PositionComp;
import Components.VelocityComp;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;

public class AISystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<AIComp> aic;
	@Mapper
	ComponentMapper<PositionComp> pc;

	@SuppressWarnings("unchecked")
	public AISystem() {
		super(Aspect.getAspectForAll(PositionComp.class, AIComp.class));
	}

	@Override
	protected void process(Entity entity) {

	}

}
