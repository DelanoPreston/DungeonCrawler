package Components;

import java.awt.Image;

import com.artemis.Component;

public class ImageComp extends Component {
	Image image;

	public ImageComp(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
