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
			DungeonPanel gamePanel = new DungeonPanel(new BorderLayout());
			// ButtonPanel buttonPanel = new ButtonPanel();
			frame.setSize(1100, 900);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(true);
			frame.setVisible(true);
			frame.add(gamePanel);// , new
															// BorderLayout().CENTER);
			// frame.add(buttonPanel, new BorderLayout().SOUTH);
			frame.setVisible(true);
		} catch (Exception e) {
			// IOClass io = new IOClass();
			e.printStackTrace();
		}
	}
}
