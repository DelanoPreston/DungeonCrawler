package Systems;

import java.awt.geom.Point2D;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;

import Components.AIComp;
import Components.MoverComp;
import Components.Position;
import Components.Velocity;
import DataStructures.Room;
import ItemComponents.Armor;
import Settings.ContentBank;
import Settings.Key;
import Settings.Map;
import States.GamePlayState;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<AIComp> aic;
	@Mapper
	ComponentMapper<Position> pc;
	@Mapper
	ComponentMapper<Velocity> vc;

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.getAspectForAll(AIComp.class, Position.class, Velocity.class));
	}

	protected void process(Entity entity) {
		// Get the components from the entity using component mappers.
		AIComp ai = aic.get(entity);
		Position pos = pc.get(entity);
		Velocity vel = vc.get(entity);

		// if entity reached end of their path
		if (ai.getPath() == null || ai.getIndex() == ai.getPath().getLength() - 1) {

			Map map = GamePlayState.mapKey.get(GamePlayState.currentMap);
			Room r = map.getRoom(ContentBank.random.nextInt(map.getRooms().size() - 1));

			AStarPathFinder as = new AStarPathFinder(map, 100, false);
			MoverComp mc = new MoverComp(Key.pathFinderRoomCheck);
			ai.setPath(as.findPath(mc, (int) pos.getX(), (int) pos.getY(), (int) r.getCenter().getX(), (int) r.getCenter().getY()));
			entity.changedInWorld();

		} else if (Point2D.distance(pos.getWindowX(), pos.getWindowY(), ai.getWindowXPathAt(ai.getIndex()), ai.getWindowYPathAt(ai.getIndex())) < 1) {
			ai.incrementIndex();
		} else {
			float xDiff = pos.getWindowX() - ai.getWindowXPathAt(ai.getIndex());
			float yDiff = pos.getWindowY() - ai.getWindowYPathAt(ai.getIndex());

			double angle = Math.atan2(yDiff, xDiff);

			vel.setXVector((float) Math.cos(angle + Math.PI));
			vel.setYVector((float) Math.sin(angle + Math.PI));

			// Update the position.
			pos.addX(vel.getXVector() * world.getDelta());
			pos.addY(vel.getYVector() * world.getDelta());
			entity.changedInWorld();
		}

	}
}
