package Systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import Components.Player;
import Components.Position;
import Components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class PlayerControlSystem extends EntityProcessingSystem implements KeyListener {
	@Mapper
	ComponentMapper<Position> pc;
	@Mapper
	ComponentMapper<Velocity> vc;

	private boolean moveDown;
	private boolean moveUp;
	private boolean moveLeft;
	private boolean moveRight;

	@SuppressWarnings("unchecked")
	public PlayerControlSystem(GameContainer container) {
		super(Aspect.getAspectForAll(Player.class, Position.class, Velocity.class));
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity entity) {
		// Position pos = pc.get(entity);
		Velocity vel = vc.get(entity);

		float xVal = 0f;
		float yVal = 0f;

		// if 2 direction keys are held, slow the movement in diagonal otherwise diagonal movement speed would be math.sqrt(2,2); ~1.21
		if (moveDown && moveLeft || moveDown && moveRight || moveUp && moveLeft || moveUp && moveRight) {
			if (moveDown) {
				yVal = (float) Math.sin(Math.toRadians(45));
			}
			if (moveUp) {
				yVal = -(float) Math.sin(Math.toRadians(45));
			}
			if (moveLeft) {
				xVal = -(float) Math.cos(Math.toRadians(45));
			}
			if (moveRight) {
				xVal = (float) Math.cos(Math.toRadians(45));
			}
		} else if (moveDown) {
			yVal = 1f;
		} else if (moveUp) {
			yVal = -1f;
		} else if (moveLeft) {
			xVal = -1f;
		} else if (moveRight) {
			xVal = 1f;
		}
		vel.setXVector(xVal);
		vel.setYVector(yVal);

		entity.changedInWorld();
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP)
			moveUp = true;
		if (key == Input.KEY_DOWN)
			moveDown = true;
		if (key == Input.KEY_RIGHT)
			moveRight = true;
		if (key == Input.KEY_LEFT)
			moveLeft = true;
	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_UP)
			moveUp = false;
		if (key == Input.KEY_DOWN)
			moveDown = false;
		if (key == Input.KEY_RIGHT)
			moveRight = false;
		if (key == Input.KEY_LEFT)
			moveLeft = false;
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}
}
