package Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import DataStructures.Location;
import Entities.MoveableEntity;
import Item.Inventory;
import Item.Item;
import Settings.DungeonPanel;
import Settings.Key;

public class Player extends MoveableEntity {
	private static final long serialVersionUID = 7134283731669813902L;
	PlayerView pv;
	//testing purposes
	int counter = 1;

	// this is for the not locked moving version
	boolean movingRight = false, movingUp = false, movingLeft = false, movingDown = false;
	boolean accFor = false, accBac = false, accRotRig = false, accRotLef = false;

	public Player(Point2D loc) {
		location = new Location(loc);
		pv = new PlayerView();
		
	}

	public Player(Location loc) {
		location = loc;
	}

	public void draw(Graphics2D g2D) {
		g2D.setColor(Color.BLACK);
		int offset = Key.tileSize / 2;
		// TODO this will eventually be a pixel image...
		g2D.fillOval((int) location.getX() - (offset / 2), (int) location.getY() - (offset / 2), offset, offset);
	}

	public void update() {
		counter++;
		if(counter == 200){
			addInventory();
		}
		// location.addLinearMovement(1, 0);
		if (Key.lockedMovementType) {
			// TODO make the player stick to the grid system
		} else {
			// update location
			if (movingRight)
				location.addLinearMovement(1 * Key.sensitivity, 0);
			if (movingUp)
				location.addLinearMovement(0, -1 * Key.sensitivity);
			if (movingLeft)
				location.addLinearMovement(-1 * Key.sensitivity, 0);
			if (movingDown)
				location.addLinearMovement(0, 1 * Key.sensitivity);
		}
		super.update();
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
	}

	public void released(KeyEvent input) {
		if (Key.lockedMovementType) {
			// TODO make the player stick to the grid system
		} else {
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
	
	public void addInventory(){
		gear.addGear("back", new Item("backpack"));
		System.out.println("new inventory (Player)");
//		Inventory temp = new Inventory(4,4);
//		
//		Item tempI = new Item("belt");
//		DungeonPanel.source.NewInventory(temp, tempI);
	}
}
