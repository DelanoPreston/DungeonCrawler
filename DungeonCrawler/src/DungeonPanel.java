import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * GamePanel class that extends JPanel
 */
@SuppressWarnings("serial")
public class DungeonPanel extends JPanel {
	int timer = 0;// if I load a game, this will be reset to 0 causing problems with crops and other timed things
	Timer mainTimer;
	// MapCreator map;
	Map map;
	public int[][] level;
	Vision v;
	PopupListener popupListener;

	// double translateX = -3200;
	// double translateY = -3200;
	// double scale = 1.0;
	// JPanel cards;

	/**
	 * Constructor for the GamePanel class that extends JPanel
	 */
	public DungeonPanel() {
		setFocusable(true);
		addKeyListener(new KeyboardListener());

		map = new Map(64, 64);
		v = new Vision(map);
		// level = createDungeon(32, 32);
		popupListener = new PopupListener(this);
		this.addMouseMotionListener(popupListener);
		// timer for updating game every 17 miliseconds
		mainTimer = new Timer(17, new TimerListener());
		mainTimer.start();
	}

	/**
	 * Update Method, Action performed calls this to update game
	 */
	public void Update() {
		if (popupListener.location != null) {
			v.source = popupListener.location;
			// System.out.println("yes");
		}
		v.update();
		map.setVisible(v.getShape());
	}

	/**
	 * Paint Method, Action performed repaint to paint the game
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		int tileSize = 8;
		super.paintComponent(g);
		if (Key.drawMap) {
			if (Key.drawGamePlay) {
				map.drawGameMap(g2D, this.getSize(), v.getShape());
			} else {
				map.drawWholeMap(g2D);
			}
		}

		v.paint(g2D, this.getSize());

		if (Key.drawMiniMap) {
			int tx = (int) v.getTileSource().getX();
			int ty = (int) v.getTileSource().getY();
			map.drawMiniMap(g2D, this.getSize(), tx, ty, 24, 24);
		}

		if (level != null) {
			for (int y = 0; y < level.length; y++) {
				for (int x = 0; x < level[0].length; x++) {
					if (level[y][x] == 0)
						g.setColor(new Color(255, 255, 255, 255));
					else
						g.setColor(new Color(0, 0, 0, 255));

					g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
				}
			}
		}
		int x = (int) (popupListener.location.getX() / ContentBank.tileSize);
		int y = (int) (popupListener.location.getY() / ContentBank.tileSize);
		g2D.drawString("Mouse is at: " + x + ", " + y, 15, 560);
	}

	/**
	 * TimerListener class, implements ActionListener, this class only calls the update methods that run for every cycle/scene of the game
	 * 
	 * @author Preston Delano
	 */
	class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Update();
			repaint();

			// if(timer >= 25){
			// System.out.println(timer);
			// }
			// timer++;
		}
	}

	/**
	 * KeyboardListener class, implements ActionListener, this class is used when there is a key press, release, or type
	 * 
	 * @author Preston Delano
	 * 
	 */
	class KeyboardListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {

			int key = arg0.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				System.out.println("nothing");
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			int key = arg0.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				System.out.println("nothing");
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			int key = arg0.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				System.out.println("nothing");
			}
		}
	}

	/**
	 * PopupListener class, implements ActionListener, this is called when the user clicks anywhere, this is only used for right click for the popup at the
	 * momment
	 * 
	 * @author Preston Delano
	 * 
	 */
	class PopupListener implements MouseListener, MouseWheelListener, MouseMotionListener {
		DungeonPanel reference;
		// private int lastOffsetX;
		// private int lastOffsetY;
		Point2D location = new Point2D.Double(0, 0);
		boolean showPopup;

		PopupListener(DungeonPanel inGamePanel) {
			reference = inGamePanel;
			showPopup = true;
		}

		public Location getMouseLocation() {
			if (location != null)
				return new Location(location.getX(), location.getY());
			else
				return new Location(0, 0);
		}

		public Point2D GetPopupLocation() {
			// System.out.println("PopupLocation:" + location.getX() + "," + location.getY());
			return location;
		}

		public void mousePressed(MouseEvent e) {
			// // capture starting point
			// lastOffsetX = (int) (e.getX() / reference.scale);
			// lastOffsetY = (int) (e.getY() / reference.scale);
		}

		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// // System.out.println(e.getModifiersEx());
			// if (e.getModifiersEx() == 2048) {// scroll click
			// showPopup = false;
			// // new x and y are defined by current mouse location subtracted
			// // by previously processed mouse location
			// getMousePosition(e);
			// int newX = (int) (e.getX() / reference.scale) - lastOffsetX;
			// int newY = (int) (e.getY() / reference.scale) - lastOffsetY;
			//
			// // increment last offset to last processed by drag event.
			// lastOffsetX += newX;
			// lastOffsetY += newY;
			//
			// // update the canvas locations
			// reference.translateX += newX;
			// reference.translateY += newY;
			//
			// // schedule a repaint.
			// reference.repaint();
			// }
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			getMousePosition(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			//
			// // make it a reasonable amount of zoom
			// // .1 gives a nice slow transition
			// reference.scale -= (.1 * e.getWheelRotation());
			// // don't cross negative threshold.
			// // also, setting scale to 0 has bad effects
			// reference.scale = Math.max(0.1, reference.scale);
			// reference.scale = Math.min(1, reference.scale);
			// System.out.println(reference.scale);
			// reference.repaint();
			// }
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// // this is clicking with no movement
			// // System.out.println("mouse clicked");
			// // double[] loc = { popupListener.GetPopupLocation().getX(), popupListener.GetPopupLocation().getY() };
			// System.out.println("something");
			// if (level.placingEntity()) {
			// ConstructionEntity temp = level.getConstructionEntity();
			// ConstructionEntity asdf = new ConstructionEntity(temp);
			// asdf.setPlaced();
			// level.addConstruction(asdf);
			//
			// } else {
			// // Location loc = new Location(e.getX(), e.getY());
			// Human temp = new Human("mouse", getMouseLocation(), 0.0, false, reference.source);
			// Entity tempSel = reference.level.getSelectedEntity();
			// tempSel = reference.source.findEntityEvent(temp, "humans");
			// if (tempSel != null && bgf.getDistance(getMouseLocation(), tempSel.getMapLocation()) < 25)
			// System.out.println("you found: " + tempSel.name);
			// else
			// System.out.println("no one is there");
			// }
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// this is when the mouse enters the jpanel i think
			// System.out.println("mouse entered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// this is when the mouse exits the jpanel i think
			// System.out.println("mouse exited");
		}

		public void ShowPopup(MouseEvent e) {
			// if (e.isPopupTrigger()) {
			//
			// popup.show(e.getComponent(), e.getX(), e.getY());
			//
			// getMousePosition(e);
			// }
		}

		public void getMousePosition(MouseEvent e) {
			location = new Point2D.Double(e.getX(), e.getY());
			// // this gets the position on the map of the mouse, given the translation, and scale
			// double x = ((reference.getWidth() / 2) - reference.translateX) - (((reference.getWidth() / 2) - e.getX()) / reference.scale);
			// double y = ((reference.getHeight() / 2) - reference.translateY) - (((reference.getHeight() / 2) - e.getY()) / reference.scale);
			// location = new Point2D.Double(x, y);
			// // System.out.println(location.getX() + ", " + location.getY());
		}
	}

	public int[][] createDungeon(int width, int height) {
		int[][] temp = setArray(height, width);
		Random rand = new Random();
		int rooms = rand.nextInt(4) + ((width + height) / 2) / 16;

		for (int i = 0; i < rooms; i++) {
			boolean roomSuccess = true;
			int roomWidth = rand.nextInt(8) + 8;
			int roomHeight = rand.nextInt(8) + 8;
			int roomX = rand.nextInt(width - roomWidth);
			int roomY = rand.nextInt(height - roomHeight);

			for (int y = roomY; y < roomY + roomHeight; y++) {
				for (int x = roomX; x < roomX + roomWidth; x++) {
					if (temp[y][x] == 1) {
						roomSuccess = false;
					}
				}
			}

			if (roomSuccess) {
				for (int y = roomY; y < roomY + roomHeight; y++) {
					for (int x = roomX; x < roomX + roomWidth; x++) {
						temp[y][x] = 1;
					}
				}
			}

			if (!roomSuccess)
				i--;
		}

		return temp;
	}

	private int[][] setArray(int height, int width) {
		int[][] temp = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				temp[i][j] = 0;
			}
		}
		return temp;
	}
}
