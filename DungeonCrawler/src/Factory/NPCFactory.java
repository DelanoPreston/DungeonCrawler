package Factory;

import Components.HealthComp;
import Components.ImageComp;
import Components.PositionComp;
import Components.VelocityComp;
import Components.VisionAreaComp;
import Components.VisionDataComp;
import Components.VisionShapeComp;

import com.artemis.Entity;
import com.artemis.World;

public class NPCFactory {

	public static Entity createRed(World world, float x, float y) {
		Entity red = world.createEntity();

		red.addComponent(new HealthComp(10));
		red.addComponent(new PositionComp(x, y));
		red.addComponent(new VelocityComp(0, 0));
		red.addComponent(new ImageComp("dude"));
		red.addComponent(new VisionAreaComp(25, (float) Math.PI));
		red.addComponent(new VisionDataComp(36));
		red.addComponent(new VisionShapeComp());

		return red;
	}
}
