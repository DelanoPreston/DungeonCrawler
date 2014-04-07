package Components;

import com.artemis.Component;

public class Image extends Component {
	String image;

	public Image(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
