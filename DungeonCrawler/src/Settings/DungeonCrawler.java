package Settings;

import javax.swing.JFrame;

import Components.PositionComp;
import Components.RotationComp;
import Components.VelocityComp;
import Systems.MovementSystem;
import Systems.RenderingSystem;

import com.artemis.Entity;
import com.artemis.World;

public class DungeonCrawler {

	public DungeonCrawler() {
		World world = new World();
		world.setSystem(new MovementSystem());
//		world.setSystem(new RotationSystem());
		world.setSystem(new RenderingSystem());

		world.initialize();

		while (true) {
//			world.setDelta(MyGameTimer.getDelta());
			world.process();
		}

//		// creating an entity
//		Entity e = world.createEntity();
//		e.addComponent(new PositionComp(200, 400));
//		e.addComponent(new VelocityComp(2.4f, 0.9f));
//		e.addComponent(new RotationComp(180));
//		e.addToWorld();
	}

	public static void main(String[] args) {

		try {
			JFrame frame = new JFrame();
			ContentBank.ContentLoader();
			DungeonPanel gamePanel = new DungeonPanel();
			frame.setSize(900, 600);
			// frame.setSize(300, 300);
			// frame.setSize(200, 200);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(true);
			frame.setVisible(true);
			frame.add(gamePanel);
			frame.setVisible(true);

			DungeonCrawler game = new DungeonCrawler();
		} catch (Exception e) {
			// IOClass io = new IOClass();
			e.printStackTrace();
		}

	}

}
