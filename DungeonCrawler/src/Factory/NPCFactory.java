package Factory;

import Components.Health;
import Components.Image;
import Components.Position;
import Components.TargetComp;
import Components.Velocity;
import Components.VisionArea;
import Components.VisionData;
import Components.VisionShape;

import com.artemis.Entity;
import com.artemis.World;

public class NPCFactory {

	public static Entity createRed(World world, float x, float y) {
		Entity red = world.createEntity();

		red.addComponent(new Health(10));
		red.addComponent(new Position(x, y));
		red.addComponent(new Velocity(1, 1));
		red.addComponent(new Image("dude"));
		red.addComponent(new TargetComp());
		red.addComponent(new VisionArea(25, (float) Math.PI));
		red.addComponent(new VisionData(36));
		red.addComponent(new VisionShape());

		return red;
	}
}
