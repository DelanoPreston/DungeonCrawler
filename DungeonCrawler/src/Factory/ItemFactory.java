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
		e.addComponent(new Image("dude"));

		return e;
	}
}
