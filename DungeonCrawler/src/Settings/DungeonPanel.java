package Settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import DataStructures.Location;
import Player.Player;

/**
 * GamePanel class that extends JPanel
 */
@SuppressWarnings("serial")
public class DungeonPanel extends JPanel {
	int timer = 0;
	Timer mainTimer;
	// MapCreator map;
	Map map;
	Player player;
	public int[][] level;
	// List<Vision> v = new ArrayList<>();
	Vision vis;
	// VisionManager vm;
	// Vision v2;
	PopupListener popupListener;
	// JPanel mapDraw;
	JPanel cards;

	public DungeonPanel() {

	}

	/**
	 * Constructor for the GamePanel class that extends JPanel
	 */
	public DungeonPanel(BorderLayout borderLayout) {
		super(borderLayout);
		setFocusable(true);
		addKeyListener(new KeyboardListener());

		// creates test buttons
		createButtonLayout(Key.width * Key.tileSize, Key.mmHeight * Key.mmtileSize);

		map = new Map(Key.width, Key.height);
		player = new Player(new Point2D.Double(400, 400));// map.rooms.get(1).getCenter());
		// v.add(new Vision(map, Key.rayCastResolution, Key.rayCastingDistance,
		// player));
		vis = new Vision(map, Key.rayCastResolution, Key.rayCastingDistance, player);
		// for (Room r : map.rooms)
		// v.add(new Vision(map, 72, 35, r.getMapLocation()));
		// vm = new VisionManager();
		// v2 = new Vision(map)
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
		player.update();
		// for(Vision vi : v)
		// vi.update();
		// vis.source = player.getLoc();
		vis.update(player.getPlayerView());
		map.updateMinimapVisibility(vis);// player.getLoc().getTileX(),
											// player.getLoc().getTileY(),
											// Key.rayCastingDistance);
		// v.get(0).update();
		// if (popupListener.location != null) {
		// vis.source = popupListener.location;
		// v.get(0).source = player.getLoc();

		// System.out.println(player.getLoc());
		// System.out.println(popupListener.location);
		// System.out.println("yes");
		// }
		// for (int i = 0; i < v.size(); i++)
		// v.get(i).update();
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
				map.drawGameMap(g2D, this.getSize(), player.getPlayerView().getTraslateX(), player.getPlayerView().getTraslateY(), player.getPlayerView()
						.getScale());
			} else {
				map.drawWholeMap(g2D);
			}
		}
		vis.drawVisShape(g2D);
		if (Key.drawFogOfWar)
			vis.paint(g2D, this.getSize());
		// this tells the minimap to be drawn
		if (Key.drawMiniMap) {

			int tx = 0;// (int) (popupListener.location.getX() / Key.tileSize);
			int ty = 0;// (int) (popupListener.location.getY() / Key.tileSize);
			if (Key.minimapFollowPlayer) {
				tx = player.getLoc().getTileX();
				ty = player.getLoc().getTileY();
			} else {
				tx = (int) (popupListener.location.getX() / Key.tileSize);
				ty = (int) (popupListener.location.getY() / Key.tileSize);
			}
			map.drawMiniMap(g2D, this.getSize(), tx, ty);
		}

		player.draw(g2D);
		// this draws the mouse's tile location under the map
		// int x = (int) (popupListener.location.getX() / Key.tileSize);
		// int y = (int) (popupListener.location.getY() / Key.tileSize);
		int x = player.location.getTileX();
		int y = player.location.getTileY();
		// g2D.setColor(new Color(0, 0, 0, 255));
		g2D.setColor(Color.GRAY);
		g2D.drawString("Player is at: " + x + ", " + y, 15, 16 + (Key.tileSize * Key.height));
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
				System.out.println("nothing - Pressed");
			}

			player.pressed(arg0);

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			int key = arg0.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				System.out.println("nothing - Release");
			}

			player.released(arg0);
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			int key = arg0.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {
				System.out.println("nothing - Typed");
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

		temp.setLayout(new RowMenuLayout(1, 100, 32, 5));// new GridLayout(3,
															// 0, 5, 5));
		temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		temp.setBackground(new Color(0, 0, 0, 0));// sets the portion of the
													// panel to transparent, so
													// I can see the map

		ButtonListener btnListener = new ButtonListener(this);

		String[] buttonNames = { "Toggle FOW", "Toggle MMFOW", "Toggle MM", "Toggle Rm #s" };

		// this is the build menu cancel button
		JButton btn;

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
		JPanel jpanel;

		public ButtonListener(JPanel jp) {
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
			} else if (arg0.getActionCommand().equals("Toggle Rm #s")) {
				if (Key.drawRoomNumbers == true) {
					Key.drawRoomNumbers = false;
				} else {
					Key.drawRoomNumbers = true;
				}
			}
			jpanel.requestFocus();
		}
	}
}
