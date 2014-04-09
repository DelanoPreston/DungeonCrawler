package Factory;

import Components.Image;
import Components.Position;
import ItemComponents.Item;
import ItemComponents.Sword;
import ItemComponents.Weapon;
import Settings.ContentBank;

import com.artemis.Entity;
import com.artemis.World;

public class ItemFactory {

	public static Entity createPlainSword(World world, int level, int x, int y) {
		Entity e = world.createEntity();

		float weight = (float) ((ContentBank.random.nextDouble() * 10) + 5);

		float blunt = 0f;
		float peirce = 0f;// still 0 for now, not sure
		float slash = 0f;

		if (weight >= 10f)
			blunt = (2.5f * weight) - 10f + (level / 2);
		if (weight <= 10f)
			slash = (2.5f * weight) - 10f + (level / 2);

		e.addComponent(new Weapon(level, peirce, slash, blunt));

		String name = "Sword";// ContentBank.createSwordName();
		float value = (blunt + peirce + slash) * 2.5f;
		float condition = (weight * 1.2f) + 100f;
		e.addComponent(new Item(name, weight, value, condition));
		e.addComponent(new Sword());

		e.addComponent(new Position(x, y));
		e.addComponent(new Image("item"));

		return e;
	}

	// public static Entity createRed(World world, float x, float y) {
	// Entity red = world.createEntity();
	//
	// red.addComponent(new Health(10));
	// red.addComponent(new Position(x, y));
	// red.addComponent(new Velocity(0, 0));
	// red.addComponent(new Image("dude"));
	// red.addComponent(new AIComp());
	// red.addComponent(new VisionArea(25, (float) Math.PI));
	// red.addComponent(new VisionData(36));
	// red.addComponent(new VisionShape());
	//
	// return red;
	// }
}
