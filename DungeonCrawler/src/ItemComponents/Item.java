package ItemComponents;

import java.io.Serializable;

import com.artemis.Component;

public class Item extends Component implements Serializable {
	private static final long serialVersionUID = 9181250862513048499L;
	String name;
	float value;
	float weight;
	float condition;

	public Item(String name, float weight) {
		this.name = name;
		this.weight = weight;
		condition = 100f;
	}

	public Item(String name, float weight, float value) {
		this.name = name;
		this.weight = weight;
		this.value = value;
		condition = 100f;
	}

	public Item(String name, float weight, float value, float condition) {
		this.name = name;
		this.weight = weight;
		this.value = value;
		this.condition = condition;
	}

	public String getName() {
		return name;
	}

	public float getWeight() {
		return weight;
	}

	public float getValue() {
		return value;
	}

	public float getCondition() {
		return condition;
	}

	public void setName(String name) {
		this.name = name;

	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public void setCondition(float condition) {
		this.condition = condition;
	}

	public void addValue(float value) {
		this.value += value;
	}

	public void addWear(float wear) {
		condition -= wear;
	}
}
