package States;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Factory.NPCFactory;
import Settings.ContentBank;
import Settings.DungeonCrawler;
import Settings.Map;
import Systems.MapDrawSystem;
import Systems.MovementSystem;
import Systems.RenderingSystem;

import com.artemis.Entity;
import com.artemis.World;

public class GamePlayState extends BasicGameState {
	int stateID = -1;

	public static HashMap<Integer, Map> mapKey = new HashMap<>();
	public static int currentMap = 0;

	float dt = 1.0f / 45.0f;

	private World world;
	private GameContainer container;
	private StateBasedGame sbg;

	private MapDrawSystem mapDrawSystem;
	private RenderingSystem renderingSystem;
	private MovementSystem movementSystem;

	// private DestinationSystem destinationSystem;

	public GamePlayState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		ContentBank.loadImages();
		this.container = gc;
		this.sbg = sbg;

		world = new World();

		mapDrawSystem = world.setSystem(new MapDrawSystem(gc));
		renderingSystem = world.setSystem(new RenderingSystem(gc));
		movementSystem = world.setSystem(new MovementSystem());
		// destinationSystem = world.setSystem(new DestinationSystem());

		world.initialize();
		world.setDelta(dt);

		Map map = new Map(64, 64);
		mapKey.put(0, map);

		Entity red;
		for (int i = 0; i < 500; i++) {
			red = NPCFactory.createRed(world, 32, 32);
			red.addToWorld();
		}

		world.process();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		movementSystem.process();
		// destinationSystem.process();
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
		} else if (key == Keyboard.KEY_0) {
			if (mapKey.containsKey(0))
				currentMap = 0;
		} else if (key == Keyboard.KEY_1) {
			if (mapKey.containsKey(1))
				currentMap = 1;
		} else if (key == Keyboard.KEY_2) {
			if (mapKey.containsKey(2))
				currentMap = 2;
		} else if (key == Keyboard.KEY_3) {
			if (mapKey.containsKey(3))
				currentMap = 3;
		}
	}
}
