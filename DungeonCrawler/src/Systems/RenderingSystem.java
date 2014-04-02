package Systems;

import Components.ImageComp;
import Components.PositionComp;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class RenderingSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public RenderingSystem() {
		super(Aspect.getAspectForAll(PositionComp.class, ImageComp.class));
	}

	@Override
	protected void process(Entity arg0) {
		
	}

}
