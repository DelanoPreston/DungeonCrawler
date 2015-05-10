package Entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataStructures.Location;
import Entities.Monsters.Monster;
import Map.Map;
import Player.Player;
import Settings.Key;

public class EntityManager {
	Map mapRef;
	Player player;
	List<Monster> monsters = new ArrayList<>();

	public EntityManager(Map ref, Player p) {
		mapRef = ref;
		player = p;
		addMonsters(5);
	}

	private void addMonsters(int num) {
		for (int i = 0; i < num; i++) {
			HashMap<String, Double> stats = new HashMap<>();
			stats.put(Key.statVision, Key.random.nextDouble() * 20 + 30);
			monsters.add(new Monster(this, "one", new Location(mapRef.getRoom(i).getWindowLocation()), stats));
		}
	}

	public void update() {
		for (Monster m : monsters) {
			m.update();
		}
	}

	public void draw(Graphics2D g2D) {
		for (Monster m : monsters) {
			m.draw(g2D);
		}
	}

	public Map getMapRef() {
		return mapRef;
	}

	public Player getPlayerRef() {
		return player;
	}
}
