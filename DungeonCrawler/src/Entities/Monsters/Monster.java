package Entities.Monsters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import DataStructures.Location;
import DataStructures.Path;
import Entities.EntityManager;
import Entities.MoveableEntity;
import Pathfinding.AStarPathFinder;
import Settings.Key;
import Settings.Vision;

public class Monster extends MoveableEntity {
	private static final long serialVersionUID = 6025468896032999254L;
	private EntityManager emRef;
	private Location targetLoc;
	private double memoryCounter;
	private HashMap<String, Double> stats;
	private Path path;
	private int pathCounter = 0;
	private int movement = 0;
	private boolean statChange = true;// to initially set the vision
	private boolean turnDone = true;

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
		int size = 10;
		g2D.fillOval((int) location.getX() - (size / 2), (int) location.getY() - (size / 2), size, size);
		if (Key.drawVision) {
			g2D.setColor(Color.RED);
			g2D.draw(vision.getShape());
			if (targetLoc != null) {
				g2D.setColor(Color.YELLOW);
				int targetSize = 10;
				g2D.drawRect((int) targetLoc.getX() - (targetSize / 2), (int) targetLoc.getY() - (targetSize / 2), targetSize, targetSize);
			}
		}
		if (Key.drawMonsterAI) {
			g2D.setColor(Color.BLACK);
			g2D.drawString("m: " + memoryCounter, (int) location.getX(), (int) location.getY());
		}
		// g2D.setColor(c);//don't really need to do this
		if(targetLoc != null && Key.drawTargetAttack){
			g2D.drawLine((int)targetLoc.getX(), (int)targetLoc.getY(), (int)location.getX(), (int)location.getY());
			g2D.setColor(Color.BLACK);
			g2D.drawString("" + location.getDistance(targetLoc), (int)location.getX() + 10, (int)location.getY() + 10);
				
			
		}
	}

	public void takeTurn() {
		// figure out turn stuff
		if (statChange) {
			// for some reason I cannot convert radius2 directly into an
			// Integer.....
			double r = stats.get(Key.statVision);
			vision.setVisionRange((float) r);
		}
		if (vision != null) {
			vision.update(emRef.getPlayerRef().getPlayerView());
			if (vision.getShape().contains(emRef.getPlayerRef().getLoc().getPoint())) {
				targetLoc = new Location(emRef.getPlayerRef().getLoc().getPoint());
				seesPlayer = true;
				memoryCounter = stats.get(Key.statMemory);
			} else {
				seesPlayer = false;
			}
			if (memoryCounter > 0) {
				memoryCounter--;
				if (targetLoc != null) {
					double distance = stats.get(Key.statVision);
					AStarPathFinder aspf = new AStarPathFinder(emRef.getMapRef().getMapKey(), (int) distance / 8, false);
					path = aspf.findPath(Key.pathFinderGamePlay, location, targetLoc);
					double temp = stats.get(Key.statMovement);
					movement = (int) temp;
				}
			} else {
				targetLoc = null;
			}

		}
		turnDone = false;
	}

	public void update() {
		// not sure if this is needed in the final game
		vision.update(emRef.getPlayerRef().getPlayerView());

		// if they have a path to follow, and still have movement left this turn
		if (path != null && pathCounter < movement) {
			// mve towards the current path location
			location.addMovement(path.getStep(pathCounter).getScreenLoc(), .5);
			float dist = path.getStep(pathCounter).getScreenLoc().getDistance(location);
			// System.out.println("dist: " + dist);
			// if the monster is close enough to the path location then it
			// increments it
			if (dist < .5) {
				pathCounter++;
			}
			// if the monster is at the end of the path, then its done
			if (pathCounter + 1 == path.getLength()) {
				turnDone = true;

			}
		} else {
			turnDone = true;
		}
		if (turnDone && targetLoc != null) {
			double temp = location.getDistance(emRef.getPlayerRef().getLoc());
			if (temp < Math.sqrt(Math.pow(Key.tileSize, 2) * 2)) {
				emRef.sendAttack(this, emRef.getPlayerRef(), 20);
			}
		}
	}

	public boolean isTurnDone() {
		if (turnDone) {
			path = null;
			pathCounter = 0;
			return true;
		}
		return false;
	}

	public void addStat(String name, double val) {
		if (!stats.containsKey(name)) {
			stats.put(name, val);
			statChange = true;
		} else {
			System.out.println("has stat (Monster - addStat)");
		}
	}

	public void updateTargetLoc(Location inLoc) {
		targetLoc = inLoc;
		memoryCounter = stats.get(Key.statMemory);
	}
}
