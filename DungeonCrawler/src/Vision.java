import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Vision {
	Point2D source;
	float radius;
	Line2D[] rays;

	Vision(int x, int y) {
		source = new Point2D.Float(x, y);
		radius = 100;
		rays = new Line2D[90];
		update();
	}

	public void paint(Graphics2D g2D) {
		for (Line2D l : rays) {
			g2D.draw(l);
		}
	}

	public void update() {
		for (int i = 0; i < rays.length; i++) {
			double angle = Math.toRadians(i * (360 / rays.length));
			rays[i] = new Line2D.Float(source, new Point2D.Double((Math.cos(angle) * radius) + source.getX(), (Math.sin(angle) * radius) + source.getY()));
			Point2D[] intersects = new Point2D[0];
			for (int j = 0; j < Map.wallList.size(); j++) {
				intersects = concatenateArrays(intersects, getIntersectionPoint(rays[i], Map.wallList.get(j).positionSize));
			}
			if (intersects.length > 0)
				rays[i] = new Line2D.Float(source, findClosestPoint(source, intersects));
		}
	}

	public Point2D[] getIntersectionPoint(Line2D line, Rectangle2D rectangle) {
		int count = 0;
		Point2D[] p = new Point2D[4];

		// Top line
		p[0] = getIntersectionPoint(line, new Line2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY()));
		// Bottom line
		p[1] = getIntersectionPoint(line, new Line2D.Double(rectangle.getX(), rectangle.getY() + rectangle.getHeight(),
				rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight()));
		// Left side...
		p[2] = getIntersectionPoint(line, new Line2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getX(), rectangle.getY() + rectangle.getHeight()));
		// Right side
		p[3] = getIntersectionPoint(line, new Line2D.Double(rectangle.getX() + rectangle.getWidth(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(),
				rectangle.getY() + rectangle.getHeight()));

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
			}
		}

		return temp;

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
		double dist = radius;
		
		for (Point2D p : intersects) {
			if (p != null && Math.abs(source.distance(p)) < dist){
				if(Math.abs(source.distance(p)) == 0.0){
					double d;
					d=0;
					if(d==0){
						
					}
				}
					
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
