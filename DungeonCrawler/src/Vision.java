import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Vision {
	public Point2D source;
	float radius;
	Line2D[] rays;
//	Point2D[] allIntersects;

	Vision(int x, int y) {
//		allIntersects = new Point2D[0];
		source = new Point2D.Float(x, y);
		radius = 100;
		rays = new Line2D[360];
		update();
	}

	public void paint(Graphics2D g2D) {
		for (Line2D l : rays) {
			g2D.draw(l);
		}
//		g2D.setColor(new Color(255, 0, 0, 255));
//		for (Point2D p : allIntersects) {
//			g2D.fillOval((int) p.getX() - 1, (int) p.getY() - 1, 2, 2);
//		}
	}

	public void update() {
		for (int i = 0; i < rays.length; i++) {
			double angle = Math.toRadians(i * (360 / rays.length));
			rays[i] = new Line2D.Double(source, new Point2D.Double((Math.cos(angle) * radius) + source.getX(), (Math.sin(angle) * radius) + source.getY()));
			Point2D[] intersects = new Point2D[0];
			for (int j = 0; j < Map.wallList.size(); j++) {
				if (rays[i].intersects(Map.wallList.get(j).positionSize)){
					Point2D[] temp = getIntersectionPoint(rays[i], Map.wallList.get(j).positionSize);
					intersects = concatenateArrays(intersects, temp);
				}
			}
//			allIntersects = concatenateArrays(intersects, allIntersects);
			if (intersects.length >= 1){
				Point2D lineEnd = findClosestPoint(source, intersects);
//				if(lineEnd == null){
//					lineEnd = new Point2D.Double(0,0);
//				}else{
					rays[i] = new Line2D.Double(source, lineEnd);
//				}
				
			}
			
		}
	}

	public Point2D[] getIntersectionPoint(Line2D l, Rectangle2D rec) {
		int count = 0;
		Point2D[] p = new Point2D[4];

		// Top line
		p[0] = ustIntersection(l, new Line2D.Double(rec.getMinX(), rec.getMinY(), rec.getMaxX(), rec.getMinY()));
		// Bottom line
		p[1] = ustIntersection(l, new Line2D.Double(rec.getMinX(), rec.getMaxY(), rec.getMaxX(), rec.getMaxY()));
		// Left side...
		p[2] = ustIntersection(l, new Line2D.Double(rec.getMinX(), rec.getMinY(), rec.getMinX(), rec.getMaxY()));
		// Right side
		p[3] = ustIntersection(l, new Line2D.Double(rec.getMaxX(), rec.getMinY(), rec.getMaxX(), rec.getMaxY()));
		// // Top line
		// p[0] = getIntersectionPoint(l, new Line2D.Double(rec.getX(), rec.getY(), rec.getX() + rec.getWidth(), rec.getY()));
		// // Bottom line
		// p[1] = getIntersectionPoint(l, new Line2D.Double(rec.getX(), rec.getY() + rec.getHeight(), rec.getX() + rec.getWidth(), rec.getY() +
		// rec.getHeight()));
		// // Left side...
		// p[2] = getIntersectionPoint(l, new Line2D.Double(rec.getX(), rec.getY(), rec.getX(), rec.getY() + rec.getHeight()));
		// // Right side
		// p[3] = getIntersectionPoint(l, new Line2D.Double(rec.getX() + rec.getWidth(), rec.getY(), rec.getX() + rec.getWidth(), rec.getY() +
		// rec.getHeight()));

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
//				System.out.println(po);
			}
		}

		return temp;

	}

	public Point2D ustIntersection(Line2D l1, Line2D l2) {
		return intersection(l1.getX1(), l1.getY1(), l1.getX2(), l1.getY2(), l2.getX1(), l2.getY1(), l2.getX2(), l2.getY2());
	}

	public Point2D intersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d == 0)
			return null;

		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

		Point2D p = new Point2D.Double(xi, yi);
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

	public static Point2D getLineInter(Line2D l1, Line2D l2) {
		// double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		double det1And2 = det(l1.getX1(), l1.getY1(), l1.getX2(), l1.getY2());
		double det3And4 = det(l2.getX1(), l2.getY1(), l2.getX2(), l2.getY2());
		double x1LessX2 = l1.getX1() - l1.getX2();
		double y1LessY2 = l1.getY1() - l1.getY2();
		double x3LessX4 = l2.getX1() - l2.getX2();
		double y3LessY4 = l2.getY1() - l2.getY2();
		double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
		if (det1Less2And3Less4 == 0) {
			// the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
			return null;
		}
		double val = det(det1And2, x1LessX2, det3And4, x3LessX4);
		double x = (val / det1Less2And3Less4);
		double y = (val / det1Less2And3Less4);
		return new Point2D.Double(x, y);
	}

	protected static double det(double a, double b, double c, double d) {
		return a * d - b * c;
	}

	public Point2D getIntersectionPoint(Line2D lineA, Line2D lineB) {

		double x1 = lineA.getX1();
		double y1 = lineA.getY1();
		double x2 = lineA.getX2();
		double y2 = lineA.getY2();

		double x3 = lineB.getX1();
		double y3 = lineB.getY1();
		double x4 = lineB.getX2();
		double y4 = lineB.getY2();

		Point2D p = null;

		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d != 0) {
			double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
			double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

			p = new Point2D.Double(xi, yi);

		}
		return p;
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
	// start = s.y > e.y ? s : e; // ensures that the starting point of the lines is always above(>y) the end point
	// end = s.y > e.y ? e : s;
	// double mul = 1. / (start.y - end.y);
	// xshift = (end.x - start.x) * mul;
	// }
	// }
	// void render() {
	// // //////////////////////////////////
	// // stroke(255);
	// // fill(255, 255, 0);
	// // ellipse(pos.x + ContentBank.tileSize / 2, pos.y + ContentBank.tileSize / 2, ContentBank.tileSize, ContentBank.tileSize);
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
	// float x1 = (float) (Math.cos(Math.toRadians(current_angle)) * radius + source.x);
	// float y1 = (float) (Math.sin(Math.toRadians(current_angle)) * radius + source.y);
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
	// rayLineIntersection = segIntersection(source.x, source.y, rayEndUnblocked.x, rayEndUnblocked.y, ln.v1.x, ln.v1.y, ln.v2.x, ln.v2.y);
	//
	// // If it is not null...
	// if (rayLineIntersection != null) {
	// // And it hasn't yet been assigned a closer vector...
	// if (rayEndBlocked == null) {
	// // Assign the intersecting vector.
	// rayEndBlocked = new PVector(rayLineIntersection.x, rayLineIntersection.y, rayLineIntersection.z);
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
	// line(source.x, source.y, v1.x, v1.y);
	// }
	// } else {
	// // Draw a polygon using the points of light
	// fill(255, 128);
	// noStroke();
	// beginShape();
	//
	// // Put a point at the source if it's not an omni light, which by definition is any light without an angle of 360
	// if (angleSpread < 360)
	// vertex(source.x, source.y);
	//
	// // Build a polygon using the points which were created
	// for (int i = 0; i < lightPoints.size(); i++) {
	// PVector v1 = (PVector) lightPoints.get(i);
	// vertex(v1.x, v1.y);
	// }
	// endShape();
	// }
	//
	// }

}
