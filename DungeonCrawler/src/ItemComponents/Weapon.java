package ItemComponents;

import java.io.Serializable;

import com.artemis.Component;

import DataStructures.Magical;

public class Weapon extends Component implements Serializable {
	private static final long serialVersionUID = -1091913916020041152L;
	int level;
	float peircing;
	float slashing;
	float blunt;
	Magical effects;

	public Weapon() {
		peircing = 0f;
		slashing = 0f;
		blunt = 0f;
		effects = new Magical();
	}

	public Weapon(int level, float peircing, float slashing, float blunt) {
		this.level = level;
		this.peircing = peircing;
		this.slashing = slashing;
		this.blunt = blunt;
		this.effects = null;
	}

	public Weapon(int level, float peircing, float slashing, float blunt, Magical effects) {
		this.level = level;
		this.peircing = peircing;
		this.slashing = slashing;
		this.blunt = blunt;
		this.effects = effects;
	}

	public int getLevel() {
		return level;
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

	public Magical getEffects() {
		return effects;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public void setEffects(Magical effects) {
		this.effects = effects;
	}
}
