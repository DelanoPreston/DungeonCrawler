package Components;

import com.artemis.Component;

public class ImageComp extends Component {
	String image;

	public ImageComp(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
