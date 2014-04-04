package Systems;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import Components.PositionComp;
import Components.VisionShapeComp;
import Settings.Vision;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class VisionSystem extends EntitySystem {
	@Mapper
	ComponentMapper<VisionShapeComp> visionShape;
	@Mapper
	ComponentMapper<PositionComp> position;

	@SuppressWarnings("unchecked")
	public VisionSystem() {
		super(Aspect.getAspectForAll(PositionComp.class, VisionShapeComp.class));
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		// ImmutableBag<Entity> bullets = world.getGroupManager().getEntities("BULLETS");
		// ImmutableBag<Entity> rocks = world.getGroupManager().getEntities("ASTEROIDS");
		// Entity player = world.getTagManager().getEntity("PLAYER");

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	// http://slick.ninjacave.com/forum/viewtopic.php?t=2769

	// public Image drawLights()
	// {
	// BufferedImage buff = BufferUtils.getCompatibleImage(pane.getWidth()/lightmapScale, pane.getHeight()/lightmapScale);
	// Graphics2D g = (Graphics2D) buff.getGraphics();
	// g.setClip(0,0,buff.getWidth(),buff.getHeight());
	// for(int i = 0; i <= lights.size()-1;i++)
	// {
	// lights.get(i).render(g, lightmapScale);
	// }
	// g.setColor(Color.black);
	// g.setComposite(AlphaComposite.getInstance(
	// AlphaComposite.SRC_OVER, ambient));
	// g.fillRect(0, 0, pane.getWidth(), pane.getHeight());
	//
	// g.dispose();
	// return buff;
	// }

	// Image light = drawLights();
	// g2d.setClip(light.getGraphics().getClip());
	// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP, all));
	// g2d.drawImage(light,0,0,frame.getWidth(),frame.getHeight(),null);
	//
	// g2d.setComposite(AlphaComposite.getInstance(
	// AlphaComposite.SRC_OVER, color));
	// g2d.drawImage(light,0,0,frame.getWidth(),frame.getHeight(),null);

	// public BufferedImage toBufferedImage(Dimension d, List<VisionShapeComp> v) {
	// // Create a buffered image with a format that's compatible with the screen
	// BufferedImage tempBImage = new BufferedImage((int) d.getWidth(), (int) d.getHeight(), BufferedImage.TYPE_INT_ARGB);
	// Graphics g = (Graphics) tempBImage.createGraphics();
	// g.setColor(Color.black);
	// g.fill(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
	// g.dispose();
	//
	// for (int y = 0; y < v.size(); y++) {
	// // BufferedImage temp = makeImage(v.get(y));
	//
	// // Copy image to buffered image
	// g = (Graphics2D) tempBImage.createGraphics();
	//
	// // Paint the image onto the buffered image
	// g.setComposite(AlphaComposite.DstOut);
	// // g2D.drawImage(temp, (int) v.get(y).getShape().getBounds().getX(), (int) v.get(y).getShape().getBounds().getY(), null);
	//
	// g.dispose();
	// }
	// // System.out.println(tempBImage.getHeight() + "," + tempBImage.getWidth());
	// return tempBImage;
	// }
	//
	// public BufferedImage makeImage(Vision v) {
	// Rectangle r = v.getShape().getBounds();
	// if (r.height <= 0)
	// r.height = 2;
	// if (r.width <= 0)
	// r.width = 2;
	// BufferedImage image = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_ARGB);
	// Graphics2D gr = image.createGraphics();
	// // move the shape in the region of the image
	// gr.translate(-r.x, -r.y);
	//
	// float radius = 35;
	// float[] dist = { 0.2f, 0.6f, 1.0f };
	// Color[] colors = { new Color(0, 0, 0, 255), new Color(0, 0, 0, 127), new Color(0, 0, 0, 0) };
	// // Color[] colors = { new Color(0, 0, 0, 0), new Color(0, 0, 0, 127), new Color(0, 0, 0, 255) };
	// RadialGradientPaint p = new RadialGradientPaint(v.source, radius, dist, colors);
	// gr.setPaint(p);
	// gr.fill(v.getShape());
	//
	// // gr.draw(s);
	// gr.dispose();
	// return image;
	// }
}
