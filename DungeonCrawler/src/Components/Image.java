package Components;

import java.io.Serializable;

import com.artemis.Component;

public class Image extends Component implements Serializable{
	private static final long serialVersionUID = 5859320395372714349L;
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
