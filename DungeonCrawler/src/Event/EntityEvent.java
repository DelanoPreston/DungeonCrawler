package Event;

import java.util.EventObject;

import Entities.Entity;

public class EntityEvent extends EventObject {
	private static final long serialVersionUID = -2516985610201628221L;
	public Entity entity;
	public String entityType;
	// here's the constructor
	public EntityEvent(Object source, Entity inEntity, String inEntityType) {
		super(source);
		this.entity = inEntity;
		entityType = inEntityType;
	}
}
