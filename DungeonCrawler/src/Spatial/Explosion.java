package Spatial;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Components.ExpireComp;
import Components.TransformComp;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class Explosion extends Spatial {
	private TransformComp transform;
	private ExpireComp expires;
	private int initialLifeTime;
	private Color color;
	private int radius;

	public Explosion(World world, Entity owner, int radius) {
		super(world, owner);
		this.radius = radius;
	}

	@Override
	public void initalize() {

		ComponentMapper<TransformComp> transformMapper = world.getMapper(TransformComp.class);// new ComponentMapper<TransformComp>(TransformComp.class, world);
		transform = transformMapper.get(owner);

		ComponentMapper<ExpireComp> expiresMapper = world.getMapper(ExpireComp.class);// new ComponentMapper<ExpireComp>(ExpireComp.class, world);
		expires = expiresMapper.get(owner);
		initialLifeTime = expires.getLife();

		color = new Color(Color.yellow);
	}

	@Override
	public void render(Graphics g) {

		color.a = (float) expires.getLife() / (float) initialLifeTime;

		g.setColor(color);
		g.setAntiAlias(true);
		g.fillOval(transform.getX() - radius, transform.getY() - radius, radius * 2, radius * 2);
	}

}