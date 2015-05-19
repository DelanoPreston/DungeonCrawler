package Settings;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class DungeonCrawler {

	// public DungeonCrawler() {
	//
	// }

	public static void main(String[] args) {
		try {
			JFrame frame = new JFrame();
			Key.setDungeonTiles();
			DungeonPanel gamePanel = new DungeonPanel(frame, new BorderLayout());
			// ButtonPanel buttonPanel = new ButtonPanel();
			frame.setSize(Key.resWidth, Key.resHeight);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(true);
			frame.add(gamePanel);
			frame.setVisible(true);
		} catch (Exception e) {
			// IOClass io = new IOClass();
			e.printStackTrace();
		}
	}
}
