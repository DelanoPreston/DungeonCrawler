package States;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Components.Image;
import Components.Player;
import Components.Position;
import Components.Velocity;
import Factory.ItemFactory;
import Factory.NPCFactory;
import Settings.ContentBank;
import Settings.DungeonCrawler;
import Settings.Map;
import Systems.AISystem;
import Systems.MapDrawSystem;
import Systems.MovementSystem;
import Systems.PlayerControlSystem;
import Systems.RenderingSystem;

import com.artemis.Entity;
import com.artemis.World;

public class GamePlayState extends BasicGameState {
	int stateID = -1;

	public static HashMap<Integer, Map> mapKey = new HashMap<>();
	public static int currentMap = 0;

	float dt = 1.0f / 60.0f;

	private boolean paused = false;

	private World world;
	private GameContainer container;
	private StateBasedGame sbg;

	private MapDrawSystem mapDrawSystem;
	private RenderingSystem renderingSystem;
	private AISystem aiSystem;
	private MovementSystem movementSystem;

	private PlayerControlSystem pcSystem;

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
		aiSystem = world.setSystem(new AISystem());
		movementSystem = world.setSystem(new MovementSystem());

		pcSystem = world.setSystem(new PlayerControlSystem(gc));

		// destinationSystem = world.setSystem(new DestinationSystem());

		world.initialize();
		world.setDelta(dt);

		Map map = new Map(64, 64);
		mapKey.put(0, map);

		Entity red;

		for (int i = 0; i < map.getRooms().size(); i++) {
			red = ItemFactory.createPlainSword(world, 5, (int) map.getRoom(i).getCenter().getX(), (int) map.getRoom(i).getCenter().getY());
			red.addToWorld();
		}

		Entity player = world.createEntity();
		player.addComponent(new Player());
		player.addComponent(new Position(32, 32));
		player.addComponent(new Velocity(0, 0));
		player.addComponent(new Image("dude"));
		player.addToWorld();

		// createWanderers();

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

		world.process();
	}

	public void createWanderers() {
		Entity red;
		for (int i = 0; i < 500; i++) {
			red = NPCFactory.createRed(world, 32, 32);
			red.addToWorld();
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!paused) {
			aiSystem.process();
			movementSystem.process();
			pcSystem.process();
			// destinationSystem.process();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		mapDrawSystem.process();
		renderingSystem.process();
	}

	public void pauseGame() {
		paused = true;
	}

	public void resumeGame() {
		paused = false;
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);

		if (key == Keyboard.KEY_ESCAPE) {
			// if (paused)
			// paused = false;
			// else
			// paused = true;
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
