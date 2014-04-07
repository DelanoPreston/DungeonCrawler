package Components;

import java.io.Serializable;

import com.artemis.Component;

public class ExpireComp extends Component implements Serializable{
	private static final long serialVersionUID = 4912394325995674813L;
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
