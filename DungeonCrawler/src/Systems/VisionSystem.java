package Systems;

import Components.VisionAreaComp;
import Components.VisionDataComp;
import Components.VisionShapeComp;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class VisionSystem extends EntitySystem {
	@Mapper
	ComponentMapper<VisionAreaComp> visionArea;
	@Mapper
	ComponentMapper<VisionDataComp> visionData;
	@Mapper
	ComponentMapper<VisionShapeComp> visionShape;

	@SuppressWarnings("unchecked")
	public VisionSystem() {
		super(Aspect.getAspectForAll(VisionAreaComp.class, VisionDataComp.class, VisionShapeComp.class));
	}

	// @Override
	// protected void process(Entity e) {
	// // Get the components from the entity using component mappers.
	// VisionAreaComp area = visionArea.get(e);
	// VisionDataComp data = visionData.get(e);
	// VisionShapeComp shape = visionShape.get(e);
	//
	// // Update the position.
	// position.addX(velocity.getXVector() * world.getDelta());
	// position.addY(velocity.getYVector() * world.getDelta());
	// }

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		// ImmutableBag<Entity> bullets = world.getGroupManager().getEntities("BULLETS");
		// ImmutableBag<Entity> rocks = world.getGroupManager().getEntities("ASTEROIDS");
		// Entity player = world.getTagManager().getEntity("PLAYER");
		int i = 1;
		if (i == 1) {
			i = 2;
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}
