package States;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Factory.MapFactory;
import Factory.NPCFactory;
import Settings.DungeonCrawler;
import Systems.MapDrawSystem;
import Systems.RenderingSystem;

import com.artemis.Entity;
import com.artemis.World;

public class GamePlayState extends BasicGameState {
	int stateID = -1;

	float dt = 1.0f / 45.0f;

	private World world;
	private GameContainer container;
	private StateBasedGame sbg;

	private MapDrawSystem mapDrawSystem;
	private RenderingSystem renderingSystem;

	public GamePlayState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.container = gc;
		this.sbg = sbg;

		world = new World();

		mapDrawSystem = world.setSystem(new MapDrawSystem(gc));
		renderingSystem = world.setSystem(new RenderingSystem(gc));

		world.initialize();
		world.setDelta(dt);

		Entity map = MapFactory.createDungeon(world, 64, 64, 50);
		mapDrawSystem.addMapKey(1, map.getUuid());
		map.addToWorld();

		map = MapFactory.createDungeon(world, 64, 64, 50);
		mapDrawSystem.addMapKey(2, map.getUuid());
		map.addToWorld();

		Entity red = NPCFactory.createRed(world, 64, 64);
		red.addToWorld();

		world.process();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		mapDrawSystem.process();
		renderingSystem.process();
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);

		if (key == Keyboard.KEY_ESCAPE) {
			sbg.enterState(DungeonCrawler.MAINMENUSTATE);
		} else if (key == Keyboard.KEY_1) {
			mapDrawSystem.setMap(1);
		} else if (key == Keyboard.KEY_2) {
			mapDrawSystem.setMap(2);
		} else if (key == Keyboard.KEY_3) {
			mapDrawSystem.setMap(3);
		}
	}
}
