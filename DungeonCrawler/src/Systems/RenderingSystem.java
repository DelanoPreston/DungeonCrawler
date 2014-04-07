package Systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import Components.Image;
import Components.Position;
import Settings.ContentBank;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class RenderingSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pc;
	@Mapper
	ComponentMapper<Image> ic;
	Graphics g;

	@SuppressWarnings("unchecked")
	public RenderingSystem(GameContainer container) {
		super(Aspect.getAspectForAll(Position.class, Image.class));
		this.g = container.getGraphics();
	}

	@Override
	protected void process(Entity entity) {
		Position position = pc.get(entity);
		Image image = ic.get(entity);

		g.drawImage(ContentBank.getImage(image.getImage()), position.getWindowX(), position.getWindowY());
	}

}
