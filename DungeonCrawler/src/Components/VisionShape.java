package Components;

import java.awt.Shape;
import java.io.Serializable;

import com.artemis.Component;

public class VisionShape extends Component implements Serializable{
	private static final long serialVersionUID = -280604280752663438L;
	Shape shape;

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
