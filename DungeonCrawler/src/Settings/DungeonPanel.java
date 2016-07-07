package Settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import DataStructures.Door;
import DataStructures.Location;
import DataStructures.Path;
import Entities.EntityManager;
import Event.CustomEventSource;
import Item.Inventory;
import Item.Item;
import Map.Map;
import Player.Player;
import Player.PlayerView;

/**
 * GamePanel class that extends JPanel
 */
@SuppressWarnings("serial")
public class DungeonPanel extends JPanel {
	public static JFrame parent;
	public static CustomEventSource source;
	// JInternalFrame testInv;
	int timer = 0;
	Timer mainTimer;
	Timer fpsTimer;
	// MapCreator map;
	Map map;
	// Player player;
	EntityManager eM;
	// public int[][] level;
	// TODO put vis inside player class (or call if from derived class
	// Vision vis;
	// VisionManager vm;
	// Vision v2;
	PopupListener popupListener;
	// JPanel mapDraw;
	JPanel cards;
	// AffineTransform normView;

	// List<JInternalFrame> inventories = new ArrayList<>();
	// create class that can be passed in that creates windows

	// Inventory testing;
	WindowController wc;
	Item itemHeld;
	// InventoryGui ig;// = new InventoryGui(new Inventory(3, 3));

	// debug variables
	int uCounter = 0;
	int pCounter = 0;
	int updateCounter = 0;
	int paintCounter = 0;

	public Player getPlayer() {
		// return player;
		return eM.getPlayerRef();
	}

	public DungeonPanel() {

	}

	/**
	 * Constructor for the GamePanel class that extends JPanel
	 */
	public DungeonPanel(JFrame parent, BorderLayout borderLayout) {
		super(borderLayout);
		DungeonPanel.parent = parent;
		setFocusable(true);
		addKeyListener(new KeyboardListener());

		// JInternalFrame testInv = new JInternalFrame("name", true, false,
		// false, false);
		// testInv.add(new Inventory(4, 4));
		// testInv.setVisible(true);
		// testInv.pack();
		// parent.add(testInv);

		// creates test buttons
		createButtonLayout(Key.width * Key.tileSize, Key.mmHeight * Key.mmtileSize);

		map = new Map(Key.width, Key.height);
		Player player = new Player(new Point2D.Double(map.getRoom(0).getCenter().getX() * Key.tileSize, map.getRoom(0).getCenter().getY() * Key.tileSize), map);
		wc = new WindowController(parent);
		eM = new EntityManager(map, player, wc);
		// vis = new Vision(map, Key.rayCastResolution, Key.rayCastingDistance,
		// player);

		popupListener = new PopupListener(this);
		this.addMouseMotionListener(popupListener);
		this.addMouseListener(popupListener);
		this.addMouseWheelListener(popupListener);

		source = new CustomEventSource();
		source.addEventListener(eM);

		// timer for updating game every 10 milliseconds
		// up to 100 frames per second - it caps at 60
		mainTimer = new Timer(10, new TimerListener());
		mainTimer.start();
		fpsTimer = new Timer(1000, new TimerFPSListener());
		fpsTimer.start();
	}

	/**
	 * Update Method, Action performed calls this to update game
	 */
	public void Update() {
		uCounter++;
		// player.update();
		// vis.update(getPlayer().getPlayerView());//this is now updated in
		// player, which is updated in eM

		eM.update();
		map.updateMinimapVisibility(eM.getPlayerRef().getVision());
		// if (popupListener.location != null) {
		// vis.source = popupListener.location;

		// System.out.println(popupListener.location);

	}

	/**
	 * Paint Method, Action performed repaint to paint the game
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// debug thing
		pCounter++;

		Graphics2D g2D = (Graphics2D) g;
		Dimension d = null;
		if (Key.drawMap) {
			if (Key.drawGamePlay) {
				getPlayer().getPlayerView().update(getPlayer());
				AffineTransform temp = getPlayer().getPlayerView().draw(this.getSize());
				g2D.transform(temp);
				map.drawMapWithChunks(g2D, getPlayer());
				// map.drawGameMap(g2D, this.getSize());
			} else {
				map.drawWholeMap(g2D);
			}
		}

		// g2D.drawImage(ContentBank.sword, null, null);

		eM.draw(g2D);
		// vis.drawVisShape(g2D);
		if (Key.drawFogOfWar) {
			if (d == null)
				d = new Dimension(Key.tileSize * Key.width, Key.tileSize * Key.height);
			// d = this.getSize();
			eM.getPlayerRef().getVision().paint(g2D, d);
		}

		if (Key.drawPathMap) {
			List<Path> tempPaths = eM.getMapRef().getPaths();
			g2D.setColor(Color.RED);
			int ts = Key.tileSize;//
			for (int i = 0; i < tempPaths.size(); i++) {
				for (int j = 0; j < tempPaths.get(i).getLength() - 1; j++) {
					g2D.drawLine(tempPaths.get(i).getX(j) * ts + (ts / 2), tempPaths.get(i).getY(j) * ts + (ts / 2), tempPaths.get(i).getX(j + 1) * ts + (ts / 2), tempPaths.get(i).getY(j + 1) * ts + (ts / 2));
				}
			}
		}

		if (Key.drawDoorLines) {
			g2D.setColor(Color.RED);
			// System.out.println("drawing lines");
			for (int i = 0; i < map.getDoors().size(); i++) {
				g2D.draw(map.getDoors().get(i).getLine());
			}
		}
		getPlayer().draw(g2D);

		if (Key.drawSelectSquare)
			drawSelectSquare(g2D);
		if (Key.drawWallLines)
			map.drawWallLines(g2D);
		if (Key.drawRoomNumbers)
			map.drawRoomNumbers(g2D);

		// this resets the affine transform so I can draw on the borders easier
		g2D.setTransform(new AffineTransform());
		eM.drawGui(g2D);
		// draws the minimap
		if (Key.drawMiniMap) {
			map.drawMiniMap(g2D, this.getSize(), getPlayer().getLoc().getTileX(), getPlayer().getLoc().getTileY());
		}
		drawInfo(g2D, getPlayer());
	}

	private void drawInfo(Graphics2D g2D, Player player) {
		int x = player.location.getTileX();
		int y = player.location.getTileY();
		g2D.setColor(Color.GRAY);
		int startHeight = (int) (this.getSize().getHeight());

		int mx = (int) popupListener.getMouseScreenLocation().getX();
		int my = (int) popupListener.getMouseScreenLocation().getY();
		Location l = new Location(mx, my);
		l.setLocationAtTile();
		g2D.drawString("Mouse is at: " + l.getTileX() + ", " + l.getTileY(), 15, startHeight - 80);

		g2D.drawString("Player is at: " + x + ", " + y, 15, startHeight - 64);
		g2D.drawString("you are awesome", 15, startHeight - 48);
		g2D.drawString("update fps: " + updateCounter, 15, startHeight - 32);
		g2D.drawString("paint fps:  " + paintCounter, 15, startHeight - 16);
		g2D.drawString("Turn: " + eM.getTurn(), 15, startHeight);
	}

	public void drawSelectSquare(Graphics2D g2D) {
		// mouse over things
		float x = popupListener.getMouseScreenLocation().getX();
		float y = popupListener.getMouseScreenLocation().getY();
		// Point2D p2D = new Point2D.Double(0.0, 0.0);
		// p2D = temp.transform(new Point2D.Double(x, y), p2D);
		int ts = Key.tileSize;
		if (eM.getPlayerRef().getVision().getShape().intersects(new Rectangle((int) x, (int) y, ts, ts))) {
			g2D.setColor(Color.WHITE);
			Location lTemp = new Location(x, y);
			lTemp.setLocationAtTile();
			g2D.drawRect((int) lTemp.getX() - (ts / 2), (int) lTemp.getY() - (ts / 2), ts, ts);
			// g2D.drawRect((int) lTemp.getX(), (int) lTemp.getY(), ts, ts);
		}
	}

	public void addInventory(Inventory i) {

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
			// repaint();
			// testInv.repaint();
			parent.repaint();
		}
	}

	/**
	 * TimerFPSListener class, implements ActionListener, this class records fps
	 * for update and paint methods
	 * 
	 * @author Preston Delano
	 */
	class TimerFPSListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			updateCounter = uCounter;
			paintCounter = pCounter;
			uCounter = 0;
			pCounter = 0;
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
				System.out.println("nothing - Pressed");
			} else if (key == KeyEvent.VK_I) {
				wc.hideWindows();
			} else if (key == KeyEvent.VK_O) {
				wc.showWindows();
			}

			getPlayer().pressed(arg0);

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			int key = arg0.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				System.out.println("nothing - Release");
			}

			getPlayer().released(arg0);
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			int key = arg0.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				System.out.println("nothing - Typed");
			} else if (key == KeyEvent.VK_I) {
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

		public Location getMouseScreenLocation() {
			PlayerView pv = reference.getPlayer().getPlayerView();
			double x = ((Key.resWidth / 2) - pv.getTraslateX()) - (((Key.resWidth / 2) - location.getX()) / pv.getScale());
			double y = ((Key.resHeight / 2) - pv.getTraslateY()) - (((Key.resHeight / 2) - location.getY()) / pv.getScale());
			return new Location((float) x, (float) y);
		}

		public Location getMouseAbsoluteLocation() {
			return location;
		}

		public Location GetPopupLocation() {
			// System.out.println("PopupLocation:" + location.getX() + "," +
			// location.getY());
			return location;
		}

		public void mousePressed(MouseEvent e) {
			// System.out.println("mousePressed");
		}

		public void mouseReleased(MouseEvent e) {
			// System.out.println("mouseReleased");
		}

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			getMousePosition(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

				// make it a reasonable amount of zoom
				// .1 gives a nice slow transition
				reference.getPlayer().getPlayerView().setScale(reference.getPlayer().getPlayerView().getScale() - (.25f * e.getWheelRotation()));
				// don't cross negative threshold.
				// also, setting scale to 0 has bad effects
				reference.getPlayer().getPlayerView().setScale((float) Math.max(1, reference.getPlayer().getPlayerView().getScale()));
				reference.getPlayer().getPlayerView().setScale((float) Math.min(10, reference.getPlayer().getPlayerView().getScale()));
				// System.out.println(reference.player.getPlayerView().getScale());
				reference.repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// left click
			if (e.getModifiersEx() == 0) {
				if (eM.clickedGui(location)) {

				} else {
					Location tempMouse = getMouseScreenLocation();
					double tempMin = 250;//Key.tileSize * 2;
					Door closestDoor = null;
					Location tempm = eM.getPlayerRef().getLoc();
					for (Door d : reference.map.getDoors()) {
						Location tempd = d.getLocation().getScreenLoc();
						if (tempm.getDistance(tempd) < Key.distanceOpenDoors) {
							if (tempMouse.getDistance(tempd) < tempMin) {
								tempMin = tempMouse.getDistance(tempd);
								closestDoor = d;
							}
						}
					}

					if (closestDoor != null) {
						// System.out.println(tempMin + " dist| door at loc: {"
						// +
						// closestDoor.getLocation().getX() + ", " +
						// closestDoor.getLocation().getY() + "}");
						if (tempMin < Key.distanceOpenDoors) {
							if (closestDoor.isDoorOpen())
								closestDoor.setID(Key.doorClosed);
							else
								closestDoor.setID(Key.doorOpened);
						}
					}
				}
			} else if (e.getModifiersEx() == 256) {
				System.out.println("button");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered dungeonPanel");
			reference.requestFocus();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited dungeonPanel");
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
			// System.out.println("get mouse position {" + e.getX() + ", " +
			// e.getY() + "}");
			// location = new Location(e.getX(), e.getY());

			location = new Location((float) e.getX(), (float) e.getY());
			// System.out.println("get mouse position {" + x + ", " + y + "}");
		}
	}

	/**
	 * createButtonLayout class creates buttons for the screen
	 */
	private void createButtonLayout(int x, int y) {
		cards = new JPanel(new CardLayout());
		cards.setBackground(new Color(0, 0, 0, 0));
		/*
		 * Button Panel creation/
		 */
		JPanel testButtons = setupTestButtons();
		cards.add(testButtons, "Test Buttons");// I think the
												// "Test Buttons" is the
												// identifier for this
												// card

		// adds button pane to cards

		JPanel temp = new JPanel(new BorderLayout());
		temp.setBackground(new Color(0, 0, 0, 0));
		temp.add(cards, BorderLayout.EAST);
		this.add(temp, BorderLayout.SOUTH);
	}

	/**
	 * 
	 */
	private JPanel setupTestButtons() {
		JPanel temp = new JPanel();

		temp.setLayout(new RowMenuLayout(3, 196, 32, 5));// new GridLayout(3,
															// 0, 5, 5));
		temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		temp.setBackground(new Color(0, 0, 0, 0));// sets the portion of the
													// panel to transparent, so
													// I can see the map

		ButtonListener btnListener = new ButtonListener(this);

		String[] buttonNames = { "Done", "Toggle FOW", "Toggle Rm Paths", "Toggle Vision Draw", "Toggle MMFOW", "Toggle MM" };

		// this is the build menu cancel button
		// JButton btn;

		for (int i = 0; i < buttonNames.length; i++) {
			temp.add(buttonCreator(buttonNames[i], 128, 32, btnListener));
		}

		// JPanel retTemp = new JPanel(new BorderLayout());
		// retTemp.add(temp, BorderLayout.SOUTH);

		return temp;// retTemp;
	}

	private JButton buttonCreator(String name, int width, int height, ButtonListener bl) {
		JButton btn = new JButton(name);
		btn.setForeground(Color.BLACK);
		btn.setPreferredSize(new Dimension(width, height));
		btn.setActionCommand(name);
		btn.setBackground(new Color(0, 96, 0, 255));
		btn.addActionListener(bl);
		return btn;
	}

	/**
	 * ButtonListener class, implements ActionListener, this class is used when
	 * a button is clicked
	 * 
	 * @author Preston Delano
	 * 
	 */
	private class ButtonListener implements ActionListener {
		DungeonPanel jpanel;

		public ButtonListener(DungeonPanel jp) {
			jpanel = jp;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// gm - game menu
			if (arg0.getActionCommand().equals("Toggle FOW")) {
				if (Key.drawFogOfWar == true) {
					Key.drawFogOfWar = false;
				} else {
					Key.drawFogOfWar = true;
				}
			} else if (arg0.getActionCommand().equals("Toggle MMFOW")) {
				if (Key.drawMMFogOfWar == true) {
					Key.drawMMFogOfWar = false;
				} else {
					Key.drawMMFogOfWar = true;
				}
			} else if (arg0.getActionCommand().equals("Toggle MM")) {
				if (Key.drawMiniMap == true) {
					Key.drawMiniMap = false;
				} else {
					Key.drawMiniMap = true;
				}
			} else if (arg0.getActionCommand().equals("Toggle Rm Paths")) {
				if (Key.drawPathMap == true) {
					Key.drawPathMap = false;
				} else {
					Key.drawPathMap = true;
				}
			} else if (arg0.getActionCommand().equals("Toggle Vision Draw")) {
				if (Key.drawRays == true) {
					Key.drawRays = false;
				} else {
					Key.drawRays = true;
				}
			} else if (arg0.getActionCommand().equals("Done")) {
				jpanel.eM.playersDone();
			}

			// /
			jpanel.requestFocus();
		}
	}
}
