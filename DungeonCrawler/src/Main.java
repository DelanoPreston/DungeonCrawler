


import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		try{
		JFrame frame = new JFrame();
		DungeonPanel gamePanel = new DungeonPanel();
		frame.setSize(850, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(gamePanel);
		frame.setVisible(true);
		}catch(Exception e){
//			IOClass io = new IOClass();
			e.printStackTrace();
		}
		
	}

}
