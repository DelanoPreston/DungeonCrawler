package Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import DataStructures.Location;
import Entities.MoveableEntity;
import GameGui.StatBar;
import Item.Inventory;
import Map.Map;
import Settings.Key;

public class Player extends MoveableEntity {
	private static final long serialVersionUID = 7134283731669813902L;
	PlayerView pv;
	// testing purposes
	int counter = 1;
	float movement = 80;
	StatBar healthBar;
	StatBar manaBar;
	public Inventory inventory;

	// this is for the not locked moving version
	boolean movingRight = false, movingUp = false, movingLeft = false, movingDown = false;

	// boolean accFor = false, accBac = false, accRotRig = false, accRotLef =
	// false;

	public Player(Point2D loc, Map mapRef) {
		super("Player 1", new Location(loc), mapRef);
		pv = new PlayerView();
		inventory = new Inventory(new Location(10, 65), 3, 3);
		healthBar = new StatBar(this, new Location(10, 10), 100, 100, 20, Color.GREEN);
		manaBar = new StatBar(this, new Location(10, 35), 100, 100, 20, Color.RED);
	}

	public Player(Location loc) {// , Map map) {
		location = loc;
		pv = new PlayerView();
		// vision = new Vision(map, Key.rayCastResolution,
		// Key.rayCastingDistance, this);
		System.out.println("I don't think this constructor is being used (player)");
	}

	public void draw(Graphics2D g2D) {
		g2D.setColor(Color.BLACK);
		int playerSize = 8;
		int offset = playerSize / 2;
		// TODO this will eventually be a pixel image...
		// draw the player
		g2D.fillOval((int) location.getX() - offset, (int) location.getY() - offset, playerSize, playerSize);
		// this is the movement area
		g2D.setColor(new Color(0, 0, 255, 48));
		g2D.fillOval((int) (location.getX() - movement), (int) (location.getY() - movement), (int) movement * 2, (int) movement * 2);
	}

	public void drawGui(Graphics2D g2D) {
		// this is the health bar
		healthBar.draw(g2D);
		manaBar.draw(g2D);
		if (inventory != null) {
			inventory.draw(g2D);
		}
	}

	public boolean clickedGui(Location p) {
		if (healthBar.getRectangle().contains(p.getPoint())) {
			System.out.println("clicked health bar");
			return true;
		} else if (manaBar.getRectangle().contains(p.getPoint())) {
			System.out.println("clicked mana bar");
			return true;
			//TODO do other mana stuff??????
		} else if (inventory.getRectangle().contains(p.getPoint())) {
			System.out.println("clicked inventory");
			inventory.clicked(p);
			return true;
			//TODO do other inventory stuff
		}
		return false;
	}

	public void update(Map map) {

		healthBar.update();
		manaBar.update();
		// super.update();
		// counter++;
		vision.update(pv);
		// location.addLinearMovement(1, 0);
		if (Key.lockedMovementType) {
			// TODO make the player stick to the grid system
			// or not
		} else {
			// TODO make this more efficient TODO TODO TODO
			int val = 0;// this is to see if the player is moving diagonally
			// update location
			if (movingRight) {
				Location temp = new Location(location);
				temp.addLinearMovement(1 * Key.sensitivity, 0);
				if (map.canMove(temp, 8)) {
					location.addLinearMovement(1 * Key.sensitivity, 0);
					// movement--;
					val++;
				}
			}
			if (movingUp) {
				Location temp = new Location(location);
				temp.addLinearMovement(0, -1 * Key.sensitivity);
				if (map.canMove(temp, 8)) {
					location.addLinearMovement(0, -1 * Key.sensitivity);
					// movement--;
					val++;
				}
			}
			if (movingLeft) {
				Location temp = new Location(location);
				temp.addLinearMovement(-1 * Key.sensitivity, 0);
				if (map.canMove(temp, 8)) {
					location.addLinearMovement(-1 * Key.sensitivity, 0);
					// movement--;
					val++;
				}
			}
			if (movingDown) {
				Location temp = new Location(location);
				temp.addLinearMovement(0, 1 * Key.sensitivity);
				if (map.canMove(temp, 8)) {
					location.addLinearMovement(0, 1 * Key.sensitivity);
					// movement--;
					val++;
				}
			}
			movement -= Math.sqrt(val);
		}

	}

	public void resetMovement() {
		movement = 80;
	}

	public float getMovement() {
		return movement;
	}

	public PlayerView getPlayerView() {
		return pv;
	}

	public void pressed(KeyEvent input) {
		if (Key.lockedMovementType) {
			// TODO make the player stick to the grid system
		} else {
			int key = input.getKeyCode();
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				movingUp = true;
			}
			if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				movingDown = true;
			}
			if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				movingLeft = true;
			}
			if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				movingRight = true;
			}
		}
	}

	public void released(KeyEvent input) {
		if (Key.lockedMovementType) {
			// TODO make the player stick to the grid system
		} else {
			int key = input.getKeyCode();
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				movingUp = false;
			}
			if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				movingDown = false;
			}
			if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				movingLeft = false;
			}
			if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				movingRight = false;
			}
		}
	}

	public void addInventory1234() {
		inventory = new Inventory(new Location(10, 65), 3, 3);
		// gear.addGear("back", new Item("backpack"));
		// System.out.println("new inventory (Player)");

		// Inventory temp = new Inventory(4,4);
		//
		// Item tempI = new Item("belt");
		// DungeonPanel.source.NewInventory(temp, tempI);
	}
}
