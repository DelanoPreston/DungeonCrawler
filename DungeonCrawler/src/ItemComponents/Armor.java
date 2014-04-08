package ItemComponents;

import java.io.Serializable;

import DataStructures.Magical;

import com.artemis.Component;

public class Armor extends Component implements Serializable {
	private static final long serialVersionUID = 4187406392666070106L;
	float peircing;
	float slashing;
	float blunt;
	Magical resistance;

	public Armor() {
		peircing = 0f;
		slashing = 0f;
		blunt = 0f;
		resistance = new Magical();
	}

	public Armor(float peircing, float slashing, float blunt, Magical resistance) {
		this.peircing = peircing;
		this.slashing = slashing;
		this.blunt = blunt;
		this.resistance = resistance;
	}

	public float getPeircing() {
		return peircing;
	}

	public float getSlashing() {
		return slashing;
	}

	public float getBlunt() {
		return blunt;
	}

	public Magical getResistance() {
		return resistance;
	}

	public void setPeircing(float peirce) {
		this.peircing = peirce;
	}

	public void setSlashing(float slash) {
		this.slashing = slash;
	}

	public void setBlunt(float blunt) {
		this.blunt = blunt;
	}

	public void setResistance(Magical resistance) {
		this.resistance = resistance;
	}
}
