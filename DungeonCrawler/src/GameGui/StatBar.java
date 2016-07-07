package GameGui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import DataStructures.Location;
import Entities.Entity;

public class StatBar {
	Entity entity;
	private int val;
	private int maxVal;
	private Color barColor;
	private int outlineWidth;
	private Rectangle rec;

	public StatBar(Entity ent, Location l, int inMaxVal, int wid, int hei, Color c) {
		entity = ent;
		val = inMaxVal;
		maxVal = inMaxVal;
		barColor = c;
		outlineWidth = 4;
		rec = new Rectangle((int)l.getX(), (int)l.getY(), wid, hei);
	}

	public void update() {
		val = entity.getHealth();
	}

	public void draw(Graphics2D g2D) {
		Stroke temp = g2D.getStroke();
		g2D.setColor(Color.GRAY);
		g2D.setStroke(new BasicStroke(outlineWidth));
		g2D.draw(rec);// ((int) loc.getX(), (int) loc.getY(), width, height);
		// need some background color for where you health isn't
		g2D.setColor(barColor);
		g2D.fillRect((int) rec.getLocation().getX() + (outlineWidth / 2), (int) rec.getLocation().getY() + (outlineWidth / 2),
				(int) (((double) val / (double) maxVal) * ((int)rec.getWidth() - (outlineWidth))), (int)rec.getHeight() - (outlineWidth));
		g2D.setStroke(temp);
	}
	
	public Rectangle getRectangle(){
		return rec;
	}
}
