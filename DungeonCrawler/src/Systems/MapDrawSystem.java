package Systems;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import Settings.ContentBank;
import Settings.Key;
import Settings.Map;
import States.GamePlayState;

import com.artemis.systems.VoidEntitySystem;

public class MapDrawSystem extends VoidEntitySystem {
	private Graphics graphics;

	// private GameContainer container;

	public MapDrawSystem(GameContainer container) {
		// this.container = container;
		this.graphics = container.getGraphics();
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processSystem() {
		Map map = GamePlayState.mapKey.get(GamePlayState.currentMap);
		int tS = ContentBank.tileSize;
		for (int y = 0; y < map.getHeightInTiles(); y++) {
			for (int x = 0; x < map.getWidthInTiles(); x++) {
				graphics.setColor(new Color(110, 110, 110, 255));
				if (map.isCell(x, y, Key.floor)) {
					graphics.setColor(new Color(32, 127, 32, 255));
				} else if (map.isCell(x, y, Key.sideWall)) {
					graphics.setColor(new Color(127, 127, 127, 255));
				} else if (map.isCell(x, y, Key.cornerWall)) {
					graphics.setColor(new Color(137, 137, 137, 255));
				} else if (map.isCell(x, y, Key.door)) {
					graphics.setColor(new Color(32, 32, 32, 255));
				}
				graphics.fillRect(x * tS, y * tS, tS, tS);

				if (Key.drawGrid) {
					graphics.setColor(new Color(180, 180, 180, 255));
					graphics.drawRect(x * tS, y * tS, tS, tS);
				}
			}

		}
	}
}
