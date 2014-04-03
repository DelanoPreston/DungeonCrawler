package Systems;

import Components.PositionComp;
import Components.VelocityComp;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class MapUpdateSystem extends EntitySystem {

	@SuppressWarnings("unchecked")
	public MapUpdateSystem() {
		super(Aspect.getAspectForAll(PositionComp.class, VelocityComp.class));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> arg0) {
		// TODO Auto-generated method stub

	}

}
