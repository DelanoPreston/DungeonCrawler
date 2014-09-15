package Settings;

import javax.swing.JFrame;

public class DungeonCrawler {

//	public DungeonCrawler() {
//
//	}

	public static void main(String[] args) {
		try {
			JFrame frame = new JFrame();
//			ContentBank.ContentLoader();
			DungeonPanel gamePanel = new DungeonPanel();
			frame.setSize(900, 750);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(true);
			frame.setVisible(true);
			frame.add(gamePanel);
			frame.setVisible(true);
		} catch (Exception e) {
			// IOClass io = new IOClass();
			e.printStackTrace();
		}
	}
}
