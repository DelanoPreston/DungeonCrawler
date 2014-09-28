package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import DataStructures.Location;
import Settings.Key;

public class Player extends MoveableEntity {
	private static final long serialVersionUID = 7134283731669813902L;
	boolean movingRight = false, movingUp = false, movingLeft = false, movingDown = false;

	public Player(Point2D loc) {
		location = new Location(loc);
	}

	public Player(Location loc) {
		location = loc;
	}

	public void draw(Graphics2D g2D) {
		g2D.setColor(Color.RED);
		int offset = Key.tileSize / 2;
		g2D.fillOval((int) location.getX() - (offset / 2), (int) location.getY() - (offset / 2), offset, offset);
	}

	public void update() {
		super.update();
		
		//update location
		if (movingRight)
			location.addLinearMovement(1 * Key.sensitivity, 0);
		if (movingUp)
			location.addLinearMovement(0, -1 * Key.sensitivity);
		if (movingLeft)
			location.addLinearMovement(-1 * Key.sensitivity, 0);
		if (movingDown)
			location.addLinearMovement(0, 1 * Key.sensitivity);
	}
	
	public void pressed(KeyEvent input){
		int key = input.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			movingUp = true;
		}
		if (key == KeyEvent.VK_DOWN) {
			movingDown = true;
		}
		if (key == KeyEvent.VK_LEFT) {
			movingLeft = true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			movingRight = true;
		}
	}
	public void released(KeyEvent input){
		int key = input.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			movingUp = false;
		}
		if (key == KeyEvent.VK_DOWN) {
			movingDown = false;
		}
		if (key == KeyEvent.VK_LEFT) {
			movingLeft = false;
		}
		if (key == KeyEvent.VK_RIGHT) {
			movingRight = false;
		}
	}
}
