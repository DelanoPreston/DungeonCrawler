package Components;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import Settings.Key;

import com.artemis.Component;

public class VisibleComp extends Component {
	// public Point2D source;//the positionComp
	// float radius;//possibly not, a visionComponent
	// Map mapRef;//use the map comp
	Line2D[] rays;
	Point2D[] allIntersects;
	int resolution;
	

	public VisibleComp() {
		this(Key.rayCastResolution);
	}

	public VisibleComp(int resolution) {
		this.resolution = resolution;
		rays = new Line2D[resolution];
	}

	public Line2D[] getRays() {
		return rays;
	}

	public Point2D[] getIntersects() {
		return allIntersects;
	}

	public int getResolution() {
		return resolution;
	}

	
	public void setRays(Line2D[] lines) {
		rays = lines;
	}

	public void setIntersects(Point2D[] intersects) {
		allIntersects = intersects;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	

}
