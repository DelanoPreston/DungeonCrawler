package ItemComponents;

import java.io.Serializable;

import com.artemis.Component;

public class Material extends Component implements Serializable {
	private static final long serialVersionUID = 4857273449775237154L;
	String material;

	public Material(String material) {
		this.material = material;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
}
