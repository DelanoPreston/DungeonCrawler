import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Vision {
	public Point2D source;
	float radius;
	Line2D[] rays;
	boolean drawRays = false;
	GeneralPath visShape;// = new GeneralPath();

	Point2D[] allIntersects;

	Vision(int x, int y) {
		// allIntersects = new Point2D[0];
		source = new Point2D.Float(x, y);
		radius = 75;
		rays = new Line2D[180];
		update();
		visShape = new GeneralPath();

	}

	public void paint(Graphics2D g2D) {
		if (drawRays) {
			for (Line2D l : rays) {
				g2D.draw(l);
			}
		} else {
			g2D.setColor(new Color(0, 0, 0, 255));
			g2D.draw(visShape);
		}

//		g2D.setColor(new Color(255, 0, 0, 255));
//		for (Point2D p : allIntersects) {
//			g2D.fillOval((int) p.getX() - 1, (int) p.getY() - 1, 2, 2);
//		}
	}

	public void update() {
		allIntersects = new Point2D[0];
		for (int i = 0; i < rays.length; i++) {
			double angle = Math.toRadians(i * (360 / rays.length));
			rays[i] = new Line2D.Double(source, new Point2D.Double((Math.cos(angle) * radius) + source.getX(), (Math.sin(angle) * radius) + source.getY()));
			Point2D[] intersects = new Point2D[0];
			for (int j = 0; j < Map.wallList.size(); j++) {
				if (rays[i].intersects(Map.wallList.get(j).positionSize)) {
					Point2D[] temp = getIntersectionPoint(rays[i], Map.wallList.get(j).positionSize);
					intersects = concatenateArrays(intersects, temp);
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
	
	Point2D useFindInt(Line2D l1, Line2D l2){
		return findIntersection(l1.getP1(), l1.getP2(), l2.getP1(), l2.getP2());
	}
	
	Point2D findIntersection(Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
		double xD1, yD1, xD2, yD2, xD3, yD3;
		double dot, deg, len1, len2;
		double segmentLen1, segmentLen2;
		double ua, ub, div;

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
		ub = (xD1 * yD3 - yD1 * xD3) / div;
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
		visShape = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		for (int i = 0; i < rays.length; i++) {
			if (i == 0) {
				visShape.moveTo(rays[i].getX2(), rays[i].getY2());
			} else {
				visShape.lineTo(rays[i].getX2(), rays[i].getY2());
			}
		}
		visShape.closePath();
	}

	// // Vector2D pos; // this is the position on the grid
	// // Map map = new Map(10, 10);
	// Location source; // this is the screen position for the light
	// float radius;
	// float angle;
	// float angleSpread;
	//
	// Vision(int x, int y) {
	// source = new Location(x, y);
	// radius = 400;
	// angle = 0;
	// angleSpread = 360; // 30
	// }
	// private static class Line
	// {
	// final Location start, end;
	// final double xshift;
	//
	// public Line(Location s, Location e) {
	// start = s.getY() > e.getY() ? s : e; // ensures that the starting point of the lines is always above(>y) the end point
	// end = s.getY() > e.getY() ? e : s;
	// double mul = 1. / (start.getY() - end.getY());
	// xshift = (end.getX() - start.getX()) * mul;
	// }
	// }
	// void render() {
	// // //////////////////////////////////
	// // stroke(255);
	// // fill(255, 255, 0);
	// // ellipse(pos.getX() + ContentBank.tileSize / 2, pos.getY() + ContentBank.tileSize / 2, ContentBank.tileSize, ContentBank.tileSize);
	// // shine();
	// }
	//
	// void shine() {
	// // //////////////////////////////////
	// Vector lightPoints = new Vector();
	//
	// // For each ray...
	// for (float current_angle = angle - angleSpread / 2; current_angle < angle + angleSpread / 2; current_angle += 1) {
	//
	// float x1 = (float) (Math.cos(Math.toRadians(current_angle)) * radius + source.getX());
	// float y1 = (float) (Math.sin(Math.toRadians(current_angle)) * radius + source.getY());
	// Location rayEndUnblocked = new Location(x1, y1);
	// Location rayEndBlocked = null;
	// Location rayLineIntersection = null;
	//
	// // For each block...
	// for (int b = 0; b < Map.wallList.size(); b++) {
	//
	// // Get its lines...
	// Vector lines = ((Block) blocks.get(b)).lines;
	//
	// // ...and test collision
	// for (int i = 0; i < lines.size(); i++) {
	//
	// Line ln = (Line) lines.get(i);
	//
	// // ...if the line's normal is facing the ray.
	// if (rayCanStrikeFacing(radians(current_angle), ln.facing)) {
	//
	// // If the ray strikes the line, a PVector is returned. Otherwise, it is null.
	// rayLineIntersection = segIntersection(source.getX(), source.getY(), rayEndUnblocked.getX(), rayEndUnblocked.getY(), ln.v1.getX(), ln.v1.getY(), ln.v2.getX(), ln.v2.getY());
	//
	// // If it is not null...
	// if (rayLineIntersection != null) {
	// // And it hasn't yet been assigned a closer vector...
	// if (rayEndBlocked == null) {
	// // Assign the intersecting vector.
	// rayEndBlocked = new PVector(rayLineIntersection.getX(), rayLineIntersection.getY(), rayLineIntersection.z);
	// }
	// // But if it has already been assigned a closer vector...
	// else {
	// // See if this new vector is closer before assigning it
	// if (pos.dist(rayLineIntersection) < pos.dist(rayEndBlocked)) {
	// rayEndBlocked.set(rayLineIntersection);
	// }
	// }
	// }
	// }
	//
	// }
	//
	// }
	//
	// // Add a point of light
	// if (rayEndBlocked != null) {
	// lightPoints.add(rayEndBlocked);
	// } else {
	// lightPoints.add(rayEndUnblocked);
	// }
	//
	// }
	//
	// // Render the light according to preference
	// if (SHOW_RAYS) {
	// for (int i = 0; i < lightPoints.size(); i++) {
	// PVector v1 = (PVector) lightPoints.get(i);
	// line(source.getX(), source.getY(), v1.getX(), v1.getY());
	// }
	// } else {
	// // Draw a polygon using the points of light
	// fill(255, 128);
	// noStroke();
	// beginShape();
	//
	// // Put a point at the source if it's not an omni light, which by definition is any light without an angle of 360
	// if (angleSpread < 360)
	// vertex(source.getX(), source.getY());
	//
	// // Build a polygon using the points which were created
	// for (int i = 0; i < lightPoints.size(); i++) {
	// PVector v1 = (PVector) lightPoints.get(i);
	// vertex(v1.getX(), v1.getY());
	// }
	// endShape();
	// }
	//
	// }

}
