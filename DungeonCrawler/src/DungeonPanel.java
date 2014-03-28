

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	Map map;
	public int[][] level;
	Vision v;
//	double translateX = -3200;
//	double translateY = -3200;
//	double scale = 1.0;
//	JPanel cards;

	/**
	 * Constructor for the GamePanel class that extends JPanel
	 */
	public DungeonPanel() {
		setFocusable(true);
		addKeyListener(new KeyboardListener());
		
		map = new Map(32, 32);
		v = new Vision(16 * ContentBank.tileSize, 16 * ContentBank.tileSize);
//		level = createDungeon(32, 32);
	}

	/**
	 * Update Method, Action performed calls this to update game
	 */
	public void Update() {
		
	}

	/**
	 * Paint Method, Action performed repaint to paint the game
	 */
	@Override
	public void paintComponent(Graphics g) {
		int tileSize = 8;
		super.paintComponent(g);

		
		
		map.paint(g);
		
		if(level != null){
			for(int y = 0; y < level.length; y++){
				for(int x = 0; x < level[0].length; x++){
					if(level[y][x] == 0)
						g.setColor(new Color(255, 255, 255, 255));
					else
						g.setColor(new Color(0, 0, 0, 255));
					
					g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
				}
			}
		}
		
		g.setColor(new Color(55, 55, 55, 255));
		v.paint((Graphics2D)g);
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
	
	public int[][] createDungeon(int width, int height) {
		int[][] temp = setArray(height, width);
		Random rand = new Random();
		int rooms = rand.nextInt(4) + ((width + height)/2)/16;
		
		for(int i = 0; i < rooms; i++){
			boolean roomSuccess = true;
			int roomWidth = rand.nextInt(8) + 8;
			int roomHeight = rand.nextInt(8) + 8;
			int roomX = rand.nextInt(width - roomWidth);
			int roomY = rand.nextInt(height - roomHeight);
			
			for(int y = roomY; y < roomY + roomHeight; y++){
				for(int x = roomX; x < roomX + roomWidth; x++){
					if(temp[y][x] == 1){
						roomSuccess = false;
					}
				}
			}
			
			if(roomSuccess){
				for(int y = roomY; y < roomY + roomHeight; y++){
					for(int x = roomX; x < roomX + roomWidth; x++){
						temp[y][x] = 1;
					}
				}
			}
			
			if(!roomSuccess)
				i--;
		}
		
		return temp;
	}
	
	private int[][] setArray(int height, int width){
		int[][] temp = new int[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				temp[i][j] = 0;
			}
		}
		return temp;
	}
}
