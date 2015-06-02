package GameGui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import DataStructures.Location;
import Entities.Entity;

public class StatBar {
	Entity entity;
	private int val;
	private int maxVal;
	private Color barColor;
	private int outlineWidth;
	private int width;
	private int height;

	public StatBar(Entity ent, int inMaxVal, int wid, int hei, Color c) {
		entity = ent;
		val = inMaxVal;
		maxVal = inMaxVal;
		barColor = c;
		outlineWidth = 4;
		width = wid;
		height = hei;
	}

	public void update() {
		val = entity.getHealth();
	}

	public void draw(Graphics2D g2D, Location loc) {
		Stroke temp = g2D.getStroke();
		g2D.setColor(Color.GRAY);
		g2D.setStroke(new BasicStroke(outlineWidth));
		g2D.drawRect((int) loc.getX(), (int) loc.getY(), width, height);
		// need some background color for where you health isn't
		g2D.setColor(barColor);
		g2D.fillRect((int) loc.getX() + (outlineWidth / 2), (int) loc.getY() + (outlineWidth / 2),
				(int) (((double) val / (double) maxVal) * (width - (outlineWidth))), height - (outlineWidth));
		g2D.setStroke(temp);
	}
}
