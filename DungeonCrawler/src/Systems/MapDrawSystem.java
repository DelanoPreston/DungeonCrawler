package Systems;

import Components.MapComp;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class MapDrawSystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	public MapDrawSystem() {
		super(Aspect.getAspectForAll(MapComp.class));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> arg0) {
		
	}

}
