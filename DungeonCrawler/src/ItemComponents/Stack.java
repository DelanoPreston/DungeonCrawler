package ItemComponents;

import java.io.Serializable;

import com.artemis.Component;

public class Stack extends Component implements Serializable {
	private static final long serialVersionUID = -1780061873971667690L;
	int quantity;

	public Stack(int quantity) {
		this.quantity = quantity;
	}

	public int getQty() {
		return quantity;
	}

	public void setQty(int quantity) {
		this.quantity = quantity;
	}

	public void addQty(int add) {
		this.quantity += add;
	}
}
