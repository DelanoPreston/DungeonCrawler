package Settings;

import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import States.GamePlayState;
import States.MainMenuState;

public class DungeonCrawler extends StateBasedGame {
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;

	public DungeonCrawler() {
		super("Dungeon Crawler");
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE));
		this.enterState(GAMEPLAYSTATE);

		// World world = new World();
		// world.setSystem(new MovementSystem());
		// // world.setSystem(new RotationSystem());
		// world.setSystem(new RenderingSystem());
		//
		// world.initialize();
		//
		// while (true) {
		// // world.setDelta(MyGameTimer.getDelta());
		// world.process();
		// }

		// // creating an entity
		// Entity e = world.createEntity();
		// e.addComponent(new PositionComp(200, 400));
		// e.addComponent(new VelocityComp(2.4f, 0.9f));
		// e.addComponent(new RotationComp(180));
		// e.addToWorld();
	}

	public static void main(String[] args) throws SlickException {
		ContentBank.ContentLoader();
		if (Key.runSlickGame) {
			AppGameContainer app = new AppGameContainer(new DungeonCrawler());

			app.setDisplayMode(900, 600, false);
			app.start();
		} else {
			try {
				JFrame frame = new JFrame();

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
	//javagamexyz
	//https://code.google.com/p/javagamexyz/source/browse/
	
	// http://www-cs-students.stanford.edu/~amitp/gameprog.html#ai
	// http://intrinsicalgorithm.com/IAonAI/2011/12/getting-more-behavior-out-of-numbers-gdmag-article/
	// http://web.archive.org/web/20080102112756/http://www.gameai.com/blackandwhite.html

	// game stuff
	// http://ploobs.com.br/?p=1707

	// FSM
	// http://www.richardlord.net/blog/finite-state-machines-with-ash

	// hexagon stuff
	// http://www.sable.mcgill.ca/~clump/Hex/HGAT.html

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		container.setTargetFrameRate(100);

	}

}
