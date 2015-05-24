package Entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataStructures.Location;
import Entities.Monsters.Monster;
import Event.InventoryEvent;
import Event.InventoryEventClassListener;
import Map.Map;
import Player.Player;
import Settings.Key;
import Settings.WindowController;

public class EntityManager implements InventoryEventClassListener {
	Map mapRef;
	WindowController wc;
	Player player;
	List<Monster> entities = new ArrayList<>();
	public List<Monster> monstersToUpdate = new ArrayList<>();
	private int turn = 1;
	private boolean monsterTurn = false;
	private boolean updatingMonsters = false;

	public EntityManager(Map ref, Player player, WindowController wc) {
		mapRef = ref;
		this.player = player;// new Player(new Point2D.Double(Key.resWidth / 2,
								// Key.resHeight / 2));
		this.wc = wc;
		addMonsters(5);
	}

	private void addMonsters(int num) {
		for (int i = 0; i < num; i++) {
			HashMap<String, Double> stats = new HashMap<>();
			stats.put(Key.statVision, Key.random.nextDouble() * 10.0 + 120.0);
			stats.put(Key.statMemory, Key.random.nextInt(2) + 3.0);
			stats.put(Key.statMovement, Key.random.nextInt(3) + 3.0);
			entities.add(new Monster(this, "one", new Location(mapRef.getRoom(i).getWindowLocation()), stats));
		}
	}

	public void update() {
		// this first if is just where the monsters are updated
		if (monsterTurn) {
			// if monsters are to be updated, then it goes here
			if (updatingMonsters) {
				for (int i = 0; i < monstersToUpdate.size(); i++) {
					monstersToUpdate.get(i).update();
					// this removes monsters from update list if they are done
					if (monstersToUpdate.get(i).isTurnDone()) {
						monstersToUpdate.remove(i);
						// because a monster in the ldwist was removed
						i--;
					}

				}
				// otherwise, the monsters turns are calculated, and they are
				// added to be updated
			} else {
				// add all monsters needing to be updated
				for (int i = 0; i < entities.size(); i++) {
					entities.get(i).takeTurn();
					if (!entities.get(i).isTurnDone()) {
						monstersToUpdate.add(entities.get(i));
					}
				}
				updatingMonsters = true;
			}
			if (monstersToUpdate.size() == 0) {
				monsterTurn = false;
				updatingMonsters = false;
				turn++;
			}
			// this is just where the player is updated, though the monsters
			// keep tabs on where the player is during the players turns because
			// monsters don't close their eyes while the players move
		} else { // player turn
			Location pLoc = new Location(player.getLoc());
			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i).getVision().canSee(pLoc)) {
					entities.get(i).updateTargetLoc(pLoc);
				}
			}
			// do player stuff
			player.update(mapRef);
			if (player.getMovement() <= 0) {
				playersDone();
				player.resetMovement();
			}
			// monsterTurn = true;
		}

	}

	public void playersDone() {
		monsterTurn = true;
	}

	public int getTurn() {
		return turn;
	}

	public void draw(Graphics2D g2D) {
		for (Entity m : entities) {
			if (m.location.getDistance(player.getLoc()) < Key.renderChunkDist)
				m.draw(g2D);
		}
	}

	public boolean sendAttack(Entity source, Entity target) {
		// ??
		return true;
	}

	public Map getMapRef() {
		return mapRef;
	}

	public Player getPlayerRef() {
		return player;
	}

	@Override
	public void handleNewInventory(InventoryEvent s) {
		wc.newInventoryWindow(s.item.getName() + " Inventory", s.inv);
	}

	@Override
	public void handleRemoveInventory(InventoryEvent s) {

	}
}
