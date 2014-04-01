package Components;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import Settings.Map;

public class VisibleComp {
	public Point2D source;
	float radius;
	Line2D[] rays;
	Map mapRef;
	Point2D[] allIntersects;
	Shape shape;
}
