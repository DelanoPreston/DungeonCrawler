package Components;

import com.artemis.Component;

public class ExpireComp extends Component {
	int lifeTime;

	public ExpireComp() {
		this(100);
	}

	public ExpireComp(int lifetime) {
		this.lifeTime = lifetime;
	}

	public int getLife() {
		return lifeTime;
	}

	public void setLife(int life) {
		this.lifeTime = life;
	}

	public void addLife(int life) {
		this.lifeTime += life;
	}
}
