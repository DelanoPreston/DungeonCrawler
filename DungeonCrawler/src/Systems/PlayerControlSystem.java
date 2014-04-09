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

		// if 2 direction keys are held, slow the movement in diagonal otherwise diagonal movement speed would be math.sqrt(2,2); ~1.21
		if (moveDown && moveLeft || moveDown && moveRight || moveUp && moveLeft || moveUp && moveRight) {
			float value = (float) Math.sin(Math.toRadians(45));
			if (moveDown) {
				vel.setYVector(value);
			} else if (moveUp) {
				vel.setYVector(-value);
			} else if (moveLeft) {
				vel.setXVector(-value);
			} else if (moveRight) {
				vel.setXVector(value);
			}
		} else if (moveDown) {
			vel.setYVector(1f);
		} else if (moveUp) {
			vel.setYVector(-1f);
		} else if (moveLeft) {
			vel.setXVector(-1f);
		} else if (moveRight) {
			vel.setXVector(1f);
		} else {
			vel.setXVector(0f);
			vel.setYVector(0f);
		}
		entity.changedInWorld();
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP) {
			moveDown = false;
			moveUp = true;
		} else if (key == Input.KEY_DOWN) {
			moveUp = false;
			moveDown = true;
		}

		if (key == Input.KEY_RIGHT) {
			moveLeft = false;
			moveRight = true;
		} else if (key == Input.KEY_LEFT) {
			moveRight = false;
			moveLeft = true;
		}

	}

	@Override
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_UP) {
			moveUp = false;
		} else if (key == Input.KEY_DOWN) {
			moveDown = false;
		}

		if (key == Input.KEY_RIGHT) {
			moveRight = false;
		} else if (key == Input.KEY_LEFT) {
			moveLeft = false;
		}
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
