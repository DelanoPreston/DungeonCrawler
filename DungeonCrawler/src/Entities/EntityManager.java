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
	List<Entity> entities = new ArrayList<>();

	public EntityManager(Map ref, Player p, WindowController wc) {
		mapRef = ref;
		player = p;
		this.wc = wc;
		addMonsters(5);
	}

	private void addMonsters(int num) {
		for (int i = 0; i < num; i++) {
			HashMap<String, Double> stats = new HashMap<>();
			stats.put(Key.statVision, Key.random.nextDouble() * 20 + 30);
			entities.add(new Monster(this, "one", new Location(mapRef.getRoom(i).getWindowLocation()), stats));
		}
	}

	public void update() {
		for (Entity m : entities) {
			m.update();
		}
	}

	public void draw(Graphics2D g2D) {
		for (Entity m : entities) {
			m.draw(g2D);
		}
	}

	private boolean sendAttack(Entity source, Entity target) {
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
