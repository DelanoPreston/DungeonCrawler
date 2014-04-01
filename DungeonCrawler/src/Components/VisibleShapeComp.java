package Components;

import java.awt.Shape;

import com.artemis.Component;

public class VisibleShapeComp extends Component {
	Shape shape;
	
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
