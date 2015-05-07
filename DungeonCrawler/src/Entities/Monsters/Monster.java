package Entities.Monsters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import DataStructures.Location;
import Entities.Entity;
import Entities.EntityManager;
import Entities.MoveableEntity;
import Settings.Key;
import Settings.Vision;

public class Monster extends MoveableEntity {
	private static final long serialVersionUID = 6025468896032999254L;
	private EntityManager emRef;
	private Entity target;
	private HashMap<String, Double> stats;
	private Vision vision;
	// private Vision hearing;
	// private Vision smell;
	private boolean statChange = true;// to initially set the vision

	// temp Variables for testing
	private boolean seesPlayer = false;

	public Monster(EntityManager em, String name, Location loc, HashMap<String, Double> sta) {
		super(name, loc);
		stats = sta;
		statChange = true;
		emRef = em;

		double radius = stats.get(Key.statVision);
		vision = new Vision(emRef.getMapRef(), Key.monsterVisionResoultion, (int) radius, this);
	}

	public void draw(Graphics2D g2D) {
		Color c = g2D.getColor();
		if (seesPlayer) {
			g2D.setColor(Color.RED);
		} else {
			g2D.setColor(Color.BLUE);
		}
		g2D.fillOval((int) location.getX(), (int) location.getY(), 10, 10);
		g2D.setColor(Color.RED);
		g2D.draw(vision.getShape());
		g2D.setColor(c);
	}

	public void update() {
		if (statChange) {
			// for some reason I cannot convert radius2 directly into an
			// Integer.....
			double r = stats.get(Key.statVision);
			vision.setVisionRange((float) r);
		}
		if (vision != null) {
			vision.update(emRef.getPlayerRef().getPlayerView());
			if (vision.getShape().contains(emRef.getPlayerRef().getLoc().getPoint())) {
				target = emRef.getPlayerRef();
				seesPlayer = true;
			} else {
				seesPlayer = false;
			}
		}

	}

	public void addStat(String name, double val) {
		if (!stats.containsKey(name)) {
			stats.put(name, val);
			statChange = true;
		} else {
			System.out.println("has stat (Monster - addStat)");
		}
	}
}
