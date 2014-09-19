package Entities;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import DataStructures.Location;

public class Player extends MoveableEntity {
	private static final long serialVersionUID = 7134283731669813902L;

	public Player(Point2D loc) {
		location = new Location(loc);
	}

	public Player(Location loc) {
		location = loc;
	}

	public void update() {
		super.update();

	}

	public void input(KeyEvent input) {
		int key = input.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			location.addLinearMovement(0, -1);
		}
		// map.translateY--;
		if (key == KeyEvent.VK_DOWN) {
			location.addLinearMovement(0, 1);
		}
		// map.translateY++;
		if (key == KeyEvent.VK_LEFT) {
			location.addLinearMovement(-1, 0);
		}
		// map.translateX--;
		if (key == KeyEvent.VK_RIGHT) {
			location.addLinearMovement(1, 0);
		}
		// map.translateX++;
	}
}
