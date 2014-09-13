package Settings;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class VisionManager {
	Image image;

	public VisionManager() {

	}

	public void update(Dimension d, List<Vision> shapes) {
		image = toBufferedImage(d, shapes);
	}

	public void paint(Graphics2D g2D) {
		g2D.drawImage(image, 0, 0, null);
	}

	public BufferedImage toBufferedImage(Dimension d, List<Vision> v) {
		// Create a buffered image with a format that's compatible with the screen
		BufferedImage tempBImage = new BufferedImage((int) d.getWidth(), (int) d.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = (Graphics2D) tempBImage.createGraphics();
		g2D.setColor(Color.BLACK);
		g2D.fill(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
		g2D.dispose();

		for (int y = 0; y < v.size(); y++) {
			BufferedImage temp = makeImage(v.get(y));

			// Copy image to buffered image
			g2D = (Graphics2D) tempBImage.createGraphics();

			// Paint the image onto the buffered image
			g2D.setComposite(AlphaComposite.DstOut);
			g2D.drawImage(temp, (int) v.get(y).shape.getBounds().getX(), (int) v.get(y).shape.getBounds().getY(), null);

			g2D.dispose();
		}
		// System.out.println(tempBImage.getHeight() + "," + tempBImage.getWidth());
		return tempBImage;
	}

	public BufferedImage makeImage(Vision v) {
		Rectangle r = v.shape.getBounds();
		if (r.height <= 0)
			r.height = 2;
		if (r.width <= 0)
			r.width = 2;
		BufferedImage image = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = image.createGraphics();
		// move the shape in the region of the image
		gr.translate(-r.x, -r.y);

		float radius = 35;
		float[] dist = { 0.2f, 0.6f, 1.0f };
		Color[] colors = { new Color(0, 0, 0, 255), new Color(0, 0, 0, 127), new Color(0, 0, 0, 0) };
		// Color[] colors = { new Color(0, 0, 0, 0), new Color(0, 0, 0, 127), new Color(0, 0, 0, 255) };
		RadialGradientPaint p = new RadialGradientPaint(v.source.getPoint(), radius, dist, colors);
		gr.setPaint(p);
		gr.fill(v.shape);

		// gr.draw(s);
		gr.dispose();
		return image;
	}
}
