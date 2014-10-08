package Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import DataStructures.Location;
import Entities.MoveableEntity;
import Settings.Key;

public class Player extends MoveableEntity {
	private static final long serialVersionUID = 7134283731669813902L;
	boolean movingRight = false, movingUp = false, movingLeft = false, movingDown = false;
	boolean accFor = false, accBac = false, accRotRig = false, accRotLef = false;

	public Player(Point2D loc) {
		location = new Location(loc);
	}

	public Player(Location loc) {
		location = loc;
	}

	public void draw(Graphics2D g2D) {
		g2D.setColor(Color.BLACK);
		int offset = Key.tileSize / 2;
		g2D.fillOval((int) location.getX() - (offset / 2), (int) location.getY() - (offset / 2), offset, offset);
	}

	public void update() {
		
		if (Key.someMovementType) {
			// update location
			if (movingRight)
				location.addLinearMovement(1 * Key.sensitivity, 0);
			if (movingUp)
				location.addLinearMovement(0, -1 * Key.sensitivity);
			if (movingLeft)
				location.addLinearMovement(-1 * Key.sensitivity, 0);
			if (movingDown)
				location.addLinearMovement(0, 1 * Key.sensitivity);
		} else {
			if (accFor)
				accel = maxAcc;
			else if (accBac)
				accel = -maxAcc;
			else
				accel = 0f;
			if (accRotRig)
				rotAccel = maxRotAcc;
			else if (accRotLef)
				rotAccel = -maxRotAcc;
			else
				rotAccel = 0f;
		}
		super.update();
	}

	public void pressed(KeyEvent input) {
		int key = input.getKeyCode();
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			movingUp = true;
			accFor = true;
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			movingDown = true;
			accBac = true;
		}
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			movingLeft = true;
			accRotLef = true;
		}
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			movingRight = true;
			accRotRig = true;
		}
	}

	public void released(KeyEvent input) {
		int key = input.getKeyCode();
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			movingUp = false;
			accFor = false;
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			movingDown = false;
			accBac = false;
		}
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			movingLeft = false;
			accRotLef = false;
		}
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			movingRight = false;
			accRotRig = false;
		}
	}
}
