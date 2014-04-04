package Systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import Components.ImageComp;
import Components.PositionComp;
import Settings.ContentBank;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class RenderingSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<PositionComp> pc;
	@Mapper
	ComponentMapper<ImageComp> ic;
	Graphics g;

	@SuppressWarnings("unchecked")
	public RenderingSystem(GameContainer container) {
		super(Aspect.getAspectForAll(PositionComp.class, ImageComp.class));
		this.g = container.getGraphics();
	}

	@Override
	protected void process(Entity entity) {
		PositionComp position = pc.get(entity);
		ImageComp image = ic.get(entity);

		g.drawImage(ContentBank.getImage(image.getImage()), position.getX(), position.getY());
	}

}
