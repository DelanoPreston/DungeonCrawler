package States;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Settings.DungeonCrawler;
import Systems.MapDrawSystem;
import Systems.MapUpdateSystem;
import Systems.VisionSystem;

import com.artemis.EntitySystem;
import com.artemis.World;

public class GamePlayState extends BasicGameState {
	int stateID = -1;

	private World world;
	private GameContainer container;
	private StateBasedGame sbg;

	private EntitySystem controlSystem;
	private EntitySystem movementSystem;
	private EntitySystem asteroidMovementSystem;
	private EntitySystem playerShipMovementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem expirationSystem;
	private EntitySystem playerLifeSystem;
	private EntitySystem respawnSystem;

	private EntitySystem renderSystem;
	private EntitySystem hudRenderSystem;

	private EntitySystem MapUpdateSystem;
	private EntitySystem MapDrawSystem;

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

		world.setSystem(new VisionSystem());
		//
//		SystemManager systemManager = world.getSystemManager();
		// controlSystem = world.setSystem(new PlayerShipControlSystem(gc));
		// movementSystem = world.setSystem(new MovementSystem(gc));
		// asteroidMovementSystem = world.setSystem(new AsteroidMovementSystem(gc));
		// playerShipMovementSystem = world.setSystem(new PlayerShipMovementSystem(gc));
		// collisionSystem = world.setSystem(new CollisionSystem());
		// expirationSystem = world.setSystem(new ExpirationSystem());
		// respawnSystem = world.setSystem(new RespawnSystem());
		// playerLifeSystem = world.setSystem(new PlayerLifeSystem());
		//
		// renderSystem = world.setSystem(new RenderSystem(gc));
		// hudRenderSystem = world.setSystem(new HudRenderSystem(gc));
		
	MapUpdateSystem = world.setSystem(new MapUpdateSystem());
		MapDrawSystem = world.setSystem(new MapDrawSystem());
		
		world.initialize();
//		systemManager.initializeAll();

		initPlayerShip();
		initAsteroids();
	}

	private void initAsteroids() {
		Random r = new Random();
		int w3 = container.getWidth() / 3;
		int h3 = container.getHeight() / 3;
		int startX, startY;

		for (int i = 0; 10 > i; i++) {
			// Start somewhere not in the middle third of both axes
			// TODO Handle this more efficiently
			do {
				startX = r.nextInt(container.getWidth());
				startY = r.nextInt(container.getHeight());
			} while (startX > w3 && startX < 2 * w3 && startY > h3 && startY < 2 * h3);

			// Entity e = EntityFactory.createAsteroid(world, startX, startY, 5);
			//
			// e.getComponent(Velocity.class).setVelocity(0.05f);
			// e.getComponent(Velocity.class).setAngle(r.nextInt(360));
			//
			// e.refresh();
		}
	}

	private void initPlayerShip() {
		// Entity player = EntityFactory.createPlayerShip(world);
		//
		// player.getComponent(Transform.class).setLocation(container.getWidth() / 2, container.getHeight() / 2);
		// player.getComponent(Respawn.class).setRespawnLocation(container.getWidth() / 2, container.getHeight() / 2);
		//
		// player.refresh();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// world.loopStart();
		//
		world.setDelta(delta);
		//
		controlSystem.process();
		movementSystem.process();
		asteroidMovementSystem.process();
		playerShipMovementSystem.process();
		collisionSystem.process();
		expirationSystem.process();
		playerLifeSystem.process();
		respawnSystem.process();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// renderSystem.process();
		// hudRenderSystem.process();
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);

		if (key == Keyboard.KEY_ESCAPE) {
			sbg.enterState(DungeonCrawler.MAINMENUSTATE);
		}
	}
}
