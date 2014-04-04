package Systems;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.UUID;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import Components.MapComp;
import Settings.ContentBank;
import Settings.Key;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class MapDrawSystem extends EntitySystem {
	BufferedImage map;
	@Mapper
	ComponentMapper<MapComp> mc;
	private Graphics graphics;
	private GameContainer container;
	private HashMap<Integer, UUID> mapKey;
	private int currentMap;

	@SuppressWarnings("unchecked")
	public MapDrawSystem(GameContainer container) {
		super(Aspect.getAspectForAll(MapComp.class));

		this.container = container;
		this.graphics = container.getGraphics();

		mapKey = new HashMap<>();
		currentMap = 1;
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	public boolean addMapKey(int level, UUID id) {
		if (!mapKey.containsKey(level)) {
			mapKey.put(level, id);
			return true;
		}
		return false;
	}

	public boolean setMap(int currentMap) {
		if (mapKey.containsKey(currentMap)) {
			this.currentMap = currentMap;
			return true;
		}
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		// System.out.println("drawn");
		for (int i = 0; entities.size() > i; i++) {
			process(entities.get(i));

		}
	}

	protected void process(Entity e) {
		if (mc.has(e) && e.getUuid() == mapKey.get(currentMap)) {
			MapComp map = mc.get(e);
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
}
