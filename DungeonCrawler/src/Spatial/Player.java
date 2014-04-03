//package Spatial;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Polygon;
//
//import Components.TransformComp;
//
//import com.artemis.ComponentMapper;
//import com.artemis.Entity;
//import com.artemis.World;
//
//public class Player extends Spatial {
//
//	private TransformComp transform;
//	private Polygon ship;
//
//	public Player(World world, Entity owner) {
//		super(world, owner);
//	}
//
//	@Override
//	public void initalize() {
//		ComponentMapper<TransformComp> transformMapper = new ComponentMapper<TransformComp>(TransformComp.class, world);
//		transform = transformMapper.get(owner);
//
//		ship = new Polygon();
//		ship.addPoint(0, -10);
//		ship.addPoint(10, 10);
//		ship.addPoint(-10, 10);
//		ship.setClosed(true);
//	}
//
//	@Override
//	public void render(Graphics g) {
//		g.setColor(Color.white);
//		g.setAntiAlias(true);
//		ship.setLocation(transform.getX(), transform.getY());
//		g.fill(ship);
//	}
//
//}
