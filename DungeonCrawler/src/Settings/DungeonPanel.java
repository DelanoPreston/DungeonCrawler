package Settings;

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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import DataStructures.Location;

/**
 * GamePanel class that extends JPanel
 */
@SuppressWarnings("serial")
public class DungeonPanel extends JPanel {
	int timer = 0;
	Timer mainTimer;
	// MapCreator map;
	Map map;
	public int[][] level;
	List<Vision> v = new ArrayList<>();
	VisionManager vm;
	// Vision v2;
	PopupListener popupListener;
	

	// JPanel cards;

	/**
	 * Constructor for the GamePanel class that extends JPanel
	 */
	public DungeonPanel() {
		setFocusable(true);
		addKeyListener(new KeyboardListener());

		map = new Map(Key.width, Key.height);
		v.add(new Vision(map, Key.rayCastResolution, Key.rayCastingDistance));
		// for (Room r : map.rooms)
		// v.add(new Vision(map, 72, 35, r.getMapLocation()));
		// vm = new VisionManager();
		// v2 = new Vision(map);
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
			v.get(0).source = popupListener.location;
			// System.out.println("yes");
		}
		for (int i = 0; i < v.size(); i++)
			v.get(i).update();
		//
		// vm.update(this.getSize(), v);
		//
		// // v2.update();
		// map.setVisible(v.get(0).getShape());
	}

	/**
	 * Paint Method, Action performed repaint to paint the game
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		super.paintComponent(g);
		if (Key.drawMap) {
			if (Key.drawGamePlay) {
				map.drawGameMap(g2D, this.getSize());//, translateX, translateY, scale);
			} else {
				map.drawWholeMap(g2D);
			}
		}
		// for (Vision vis : v)
		// vis.paint(g2D, this.getSize());
		// if (Key.drawFogOfWar)
		// vm.paint(g2D);

		v.get(0).paint(g2D, this.getSize());

		// this tells the minimap to be drawn
		if (Key.drawMiniMap) {
			int tx = (int) (popupListener.location.getX() / Key.tileSize);
			int ty = (int) (popupListener.location.getY() / Key.tileSize);
			map.drawMiniMap(g2D, this.getSize(), tx, ty, 24, 24);
		}

		// this draws the mouse's tile location under the map
		int x = (int) (popupListener.location.getX() / Key.tileSize);
		int y = (int) (popupListener.location.getY() / Key.tileSize);
		g2D.setColor(new Color(0, 0, 0, 255));
		g2D.drawString("Mouse is at: " + x + ", " + y, 15, 16 + (Key.tileSize * Key.height));
		g2D.drawString("you are awesome", 15, 32 + (Key.tileSize * Key.height));
	}

	/**
	 * TimerListener class, implements ActionListener, this class only calls the
	 * update methods that run for every cycle/scene of the game
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
	 * KeyboardListener class, implements ActionListener, this class is used
	 * when there is a key press, release, or type
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
			if (key == KeyEvent.VK_UP)
				map.translateY--;
			if (key == KeyEvent.VK_DOWN)
				map.translateY++;
			if (key == KeyEvent.VK_LEFT)
				map.translateX--;
			if (key == KeyEvent.VK_RIGHT)
				map.translateX++;
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
	 * PopupListener class, implements ActionListener, this is called when the
	 * user clicks anywhere, this is only used for right click for the popup at
	 * the momment
	 * 
	 * @author Preston Delano
	 * 
	 */
	class PopupListener implements MouseListener, MouseWheelListener, MouseMotionListener {
		DungeonPanel reference;
		// private int lastOffsetX;
		// private int lastOffsetY;
		Location location = new Location(0, 0);
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

		public Location GetPopupLocation() {
			// System.out.println("PopupLocation:" + location.getX() + "," +
			// location.getY());
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
			// // double[] loc = { popupListener.GetPopupLocation().getX(),
			// popupListener.GetPopupLocation().getY() };
			// System.out.println("something");
			// if (level.placingEntity()) {
			// ConstructionEntity temp = level.getConstructionEntity();
			// ConstructionEntity asdf = new ConstructionEntity(temp);
			// asdf.setPlaced();
			// level.addConstruction(asdf);
			//
			// } else {
			// // Location loc = new Location(e.getX(), e.getY());
			// Human temp = new Human("mouse", getMouseLocation(), 0.0, false,
			// reference.source);
			// Entity tempSel = reference.level.getSelectedEntity();
			// tempSel = reference.source.findEntityEvent(temp, "humans");
			// if (tempSel != null && bgf.getDistance(getMouseLocation(),
			// tempSel.getMapLocation()) < 25)
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
			location = new Location(e.getX(), e.getY());
			// // this gets the position on the map of the mouse, given the
			// translation, and scale
			// double x = ((reference.getWidth() / 2) - reference.translateX) -
			// (((reference.getWidth() / 2) - e.getX()) / reference.scale);
			// double y = ((reference.getHeight() / 2) - reference.translateY) -
			// (((reference.getHeight() / 2) - e.getY()) / reference.scale);
			// location = new Point2D.Double(x, y);
			// // System.out.println(location.getX() + ", " + location.getY());
		}
	}
}
