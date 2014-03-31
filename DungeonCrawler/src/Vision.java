import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Vision {
	public Point2D source;
	float radius;
	Line2D[] rays;
	Map mapRef;
	Point2D[] allIntersects;
	Shape shape;

	Vision(Map mapRef) {
		this.mapRef = mapRef;
		source = new Point2D.Float(0, 0);
		radius = 45;
		rays = new Line2D[180];
		// update();
		shape = new GeneralPath();
	}

	public void paint(Graphics2D g2D, Dimension d) {
		if (Key.drawRays) {
			for (Line2D l : rays) {
				g2D.draw(l);
			}
		} else {
			g2D.setColor(new Color(0, 0, 0, 64));
			if (Key.drawInverseVisionShape) {
				Point2D center = source;
//				 float radius = 25;
				Point2D focus = source;
				//this second number describes how dark the transition is depending on the color list
				float[] dist = { 0.0f, 0.1f, 1.0f };
				Color[] colors = { new Color(0, 0, 0, 64), new Color(0, 0, 0, 128), new Color(0, 0, 0, 255) };
				RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, CycleMethod.NO_CYCLE);
				// AffineTransform af = new AffineTransform();
				// af
				g2D.setPaint(p);
				g2D.fill(createDrawVisionShape(d));
			} else {
				g2D.fill(shape);
			}

			// g2D.draw(visShape);
		}
	}

	public void update() {
		// recreates the intersect array, for new position
		allIntersects = new Point2D[0];
		// re grabs the list of walls from the map
		List<MapTile> walls = mapRef.getWalls();
		// calculates values for each ray in the cast
		for (int i = 0; i < rays.length; i++) {
			double angle = Math.toRadians(i * (360 / rays.length));
			rays[i] = new Line2D.Double(source, new Point2D.Double((Math.cos(angle) * radius) + source.getX(), (Math.sin(angle) * radius) + source.getY()));
			Point2D[] intersects = new Point2D[0];
			// checks if it intersects with each wall in the list
			for (int j = 0; j < walls.size(); j++) {
				if (walls.get(j).solid) {
					// this uses the generic collision to check if there is a collision
					if (rays[i].intersects(walls.get(j).positionSize)) {
						// then this checks the close walls that seem to have an intersection, and gets the points
						Point2D[] temp = getIntersectionPoint(rays[i], walls.get(j).positionSize);
						intersects = concatenateArrays(intersects, temp);
					}
				}
			}
			allIntersects = concatenateArrays(intersects, allIntersects);
			if (intersects.length >= 1) {
				Point2D lineEnd = findClosestPoint(source, intersects);

				// catch for the bad values
				if (lineEnd.getY() > 240) {
					Point2D temp = lineEnd;
					lineEnd = temp;
				}
				rays[i] = new Line2D.Double(source, lineEnd);
			}
		}
		createVisionShape();
	}

	public Point2D getSource() {
		return source;
	}

	public Shape getShape() {
		return shape;
	}

	public Point2D getTileSource() {
		return new Point2D.Double(source.getX() / ContentBank.tileSize, source.getY() / ContentBank.tileSize);
	}

	public Point2D[] getIntersectionPoint(Line2D l, Rectangle2D rec) {
		int count = 0;
		Point2D[] p = new Point2D[4];

		// Top line
		p[0] = useFindInt(l, new Line2D.Double(rec.getMinX(), rec.getMinY(), rec.getMaxX(), rec.getMinY()));
		// Bottom line
		p[1] = useFindInt(l, new Line2D.Double(rec.getMinX(), rec.getMaxY(), rec.getMaxX(), rec.getMaxY()));
		// Left side...
		p[2] = useFindInt(l, new Line2D.Double(rec.getMinX(), rec.getMinY(), rec.getMinX(), rec.getMaxY()));
		// Right side
		p[3] = useFindInt(l, new Line2D.Double(rec.getMaxX(), rec.getMinY(), rec.getMaxX(), rec.getMaxY()));

		for (Point2D po : p) {
			if (po != null) {
				count++;
			}
		}

		Point2D[] temp = new Point2D[count];
		int tempIndex = 0;

		for (Point2D po : p) {
			if (po != null) {
				temp[tempIndex] = po;
				tempIndex++;
				// System.out.println(po);
			}
		}

		return temp;

	}

	Point2D intersection(Line2D l1, Line2D l2) {
		double x1 = l1.getX1(), y1 = l1.getY1(), x2 = l1.getX2(), y2 = l1.getY2(), x3 = l2.getX1(), y3 = l2.getY1(), x4 = l2.getX2(), y4 = l2.getY2();
		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d == 0)
			return null;

		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

		Point2D p = new Point2D.Double(xi, yi);
		// these if statements say there is no intersection if the lines are too short
		if (xi < Math.min(x1, x2) || xi > Math.max(x1, x2))
			return null;
		if (xi < Math.min(x3, x4) || xi > Math.max(x3, x4))
			return null;
		if (yi < Math.min(y1, y2) || yi > Math.max(y1, y2))
			return null;
		if (yi < Math.min(y3, y4) || yi > Math.max(y3, y4))
			return null;
		return p;
	}

	Point2D useFindInt(Line2D l1, Line2D l2) {
		return findIntersection(l1.getP1(), l1.getP2(), l2.getP1(), l2.getP2());
	}

	Point2D findIntersection(Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
		double xD1, yD1, xD2, yD2, xD3, yD3;
		double dot, deg, len1, len2;
		double segmentLen1, segmentLen2;
		double ua, div;// , ub;//I did not write this method, though ub does not seemed to be used anywhere

		// calculate differences
		xD1 = p2.getX() - p1.getX();
		xD2 = p4.getX() - p3.getX();
		yD1 = p2.getY() - p1.getY();
		yD2 = p4.getY() - p3.getY();
		xD3 = p1.getX() - p3.getX();
		yD3 = p1.getY() - p3.getY();

		// calculate the lengths of the two lines
		len1 = Math.sqrt(xD1 * xD1 + yD1 * yD1);
		len2 = Math.sqrt(xD2 * xD2 + yD2 * yD2);

		// calculate angle between the two lines.
		dot = (xD1 * xD2 + yD1 * yD2); // dot product
		deg = dot / (len1 * len2);

		// if abs(angle)==1 then the lines are parallell,
		// so no intersection is possible
		if (Math.abs(deg) == 1)
			return null;

		// find intersection Pt between two lines
		Point2D pt = new Point2D.Double(0, 0);
		div = yD2 * xD1 - xD2 * yD1;
		ua = (xD2 * yD3 - yD2 * xD3) / div;
		// ub = (xD1 * yD3 - yD1 * xD3) / div;
		pt.setLocation(p1.getX() + ua * xD1, p1.getY() + ua * yD1);

		// calculate the combined length of the two segments
		// between Pt-p1 and Pt-p2
		xD1 = pt.getX() - p1.getX();
		xD2 = pt.getX() - p2.getX();
		yD1 = pt.getY() - p1.getY();
		yD2 = pt.getY() - p2.getY();
		segmentLen1 = Math.sqrt(xD1 * xD1 + yD1 * yD1) + Math.sqrt(xD2 * xD2 + yD2 * yD2);

		// calculate the combined length of the two segments
		// between Pt-p3 and Pt-p4
		xD1 = pt.getX() - p3.getX();
		xD2 = pt.getX() - p4.getX();
		yD1 = pt.getY() - p3.getY();
		yD2 = pt.getY() - p4.getY();
		segmentLen2 = Math.sqrt(xD1 * xD1 + yD1 * yD1) + Math.sqrt(xD2 * xD2 + yD2 * yD2);

		// if the lengths of both sets of segments are the same as
		// the lenghts of the two lines the point is actually
		// on the line segment.

		// if the point isn't on the line, return null
		if (Math.abs(len1 - segmentLen1) > 0.01 || Math.abs(len2 - segmentLen2) > 0.01)
			return null;

		// return the valid intersection
		return pt;
	}

	public Point2D[] concatenateArrays(Point2D[] p1, Point2D[] p2) {
		Point2D[] temp = new Point2D[p1.length + p2.length];

		for (int i = 0; i < p1.length; i++) {
			temp[i] = p1[i];
		}
		for (int i = 0; i < p2.length; i++) {
			temp[i + p1.length] = p2[i];
		}

		return temp;
	}

	public Point2D findClosestPoint(Point2D source, Point2D[] intersects) {
		Point2D temp = null;
		double dist = radius + 1;

		for (Point2D p : intersects) {
			if (p != null && Math.abs(source.distance(p)) < dist) {
				dist = Math.abs(source.distance(p));

				temp = p;
			}
		}

		return temp;
	}

	public void createVisionShape() {
		GeneralPath visShape = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		for (int i = 0; i < rays.length; i++) {
			if (i == 0) {
				visShape.moveTo(rays[i].getX2(), rays[i].getY2());
			} else {
				visShape.lineTo(rays[i].getX2(), rays[i].getY2());
			}
		}
		visShape.closePath();
		shape = visShape;
	}

	public Shape createDrawVisionShape(Dimension d) {
		GeneralPath tempShape = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		for (int i = 0; i < rays.length; i++) {
			if (i == 0) {
				tempShape.moveTo(rays[i].getX2(), rays[i].getY2());
			} else {
				tempShape.lineTo(rays[i].getX2(), rays[i].getY2());
			}
		}
		if (Key.drawInverseVisionShape) {
			tempShape.lineTo(rays[0].getX2(), rays[0].getY2());
			tempShape.lineTo(d.getWidth(), rays[0].getY2());
			tempShape.lineTo(d.getWidth(), 0);
			tempShape.lineTo(0, 0);
			tempShape.lineTo(0, d.getHeight());
			tempShape.lineTo(d.getWidth(), d.getHeight());
			tempShape.lineTo(d.getWidth(), rays[0].getY2());
		}
		tempShape.closePath();
		return tempShape;
	}
}
