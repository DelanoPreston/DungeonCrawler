package Settings;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		try {
			JFrame frame = new JFrame();
//			ContentBank.ContentLoader();
			DungeonPanel gamePanel = new DungeonPanel();
			frame.setSize(1200, 925);
//			frame.setSize(300, 300);
//			frame.setSize(200, 200);
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
