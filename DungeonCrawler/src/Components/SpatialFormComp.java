package Components;

import com.artemis.Component;

public class SpatialFormComp extends Component {
	private String spatialFormFile;
	private boolean visible;

	public SpatialFormComp(String spatialFormFile) {
		this(spatialFormFile, true);
	}

	public SpatialFormComp(String spatialFormFile, boolean visible) {
		this.spatialFormFile = spatialFormFile;
		this.visible = visible;
	}

	public String getSpatialFormFile() {
		return spatialFormFile;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void toggleVisible() {
		this.visible = !visible;
	}
}
