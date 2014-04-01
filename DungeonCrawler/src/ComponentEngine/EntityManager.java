package ComponentEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EntityManager {
	private boolean frozen;
	private List<UUID> entities;
	private HashMap<UUID, String> entityStringNames;
	// private List<System> systems;
	private HashMap<Class<? extends Component>, HashMap<UUID, Component>> componentStore;

	public EntityManager() {
		frozen = false;
		entities = new ArrayList<>();
		entityStringNames = new HashMap<UUID, String>();
		componentStore = new HashMap<Class<? extends Component>, HashMap<UUID, Component>>();
	}

	public Component getComponent(UUID entity, Class<? extends Component> componentType) {
		// synchronized (componentStore) {
		HashMap<UUID, ? extends Component> store = componentStore.get(componentType);

		if (store == null)
			throw new IllegalArgumentException("GET FAIL: there are no entities with a Component of class: " + componentType);

		Component result = store.get(entity);

		if (result == null) {
			/**
			 * DEFAULT: normal debug info:
			 */
			throw new IllegalArgumentException("GET FAIL: " + entity + "(name:" + nameFor(entity) + ")" + " does not possess Component of class\n   missing: "
					+ componentType);
			/**
			 * OPTIONAL: more detailed debug info:
			 * 
			 * 
			 * StringBuffer sb = new StringBuffer(); for( UUID e : store.keySet() ) { sb.append( "\nUUID: "+e+" === "+store.get(e) ); }
			 * 
			 * throw new IllegalArgumentException("GET FAIL: " + entity + "(name:"+nameFor(entity)+")" + " does not possess Component of class\n   missing: " +
			 * componentType + "TOTAL STORE FOR THIS COMPONENT CLASS : "+ sb.toString() );
			 */
		}

		return result;
		// }
	}

	public void removeComponent(UUID entity, Class<? extends Component> component) {
		// synchronized (componentStore) {
		HashMap<UUID, ? extends Component> store = componentStore.get(component.getClass());

		if (store == null)
			throw new IllegalArgumentException("REMOVE FAIL: there are no entities with a Component of class: " + component.getClass());

		Component result = store.remove(entity);
		if (result == null)
			throw new IllegalArgumentException("REMOVE FAIL: " + entity + "(name:" + nameFor(entity) + ")"
					+ " does not possess Component of class\n   missing: " + component.getClass());
		// }
	}

	public boolean hasComponent(UUID entity, Class<? extends Component> componentType) {
		// synchronized (componentStore) {
		HashMap<UUID, ? extends Component> store = componentStore.get(componentType);

		if (store == null)
			return false;
		else
			return store.containsKey(entity);
		// }
	}

	/**
	 * WARNING: low performance implementation!
	 * 
	 * @param entity
	 * @return
	 */
	public List<? extends Component> getAllComponentsOnEntity(UUID entity) {
		// synchronized (componentStore) {
		List<Component> entitiesComponents = new ArrayList<>();

		for (HashMap<UUID, ? extends Component> store : componentStore.values()) {
			if (store != null) {

				Component componentFromEntity = store.get(entity);

				if (componentFromEntity != null)
					entitiesComponents.add(componentFromEntity);
			}
		}

		return entitiesComponents;
		// }
	}

	public List<Component> getAllComponentsOfType(Class<? extends Component> componentType) {
		// synchronized (componentStore) {
		HashMap<UUID, ? extends Component> store = componentStore.get(componentType);

		if (store == null)
			return new ArrayList<Component>();

		return new ArrayList<Component>(store.values());
		// }
	}

	public List<UUID> getAllEntitiesPossessingComponent(Class<? extends Component> componentType) {
		// synchronized (componentStore) {
		HashMap<UUID, ? extends Component> store = componentStore.get(componentType);

		if (store == null)
			return new ArrayList<UUID>();

		return new ArrayList<UUID>(store.keySet());
		// }
	}

	public <T extends Component> void addComponent(UUID entity, T component) {
		if (frozen)
			return;

		// synchronized (componentStore) {
		HashMap<UUID, Component> store = componentStore.get(component.getClass());

		if (store == null) {
			store = new HashMap<UUID, Component>();
			componentStore.put(component.getClass(), store);
		}

		// store.put(entity, component);
		((HashMap<UUID, Component>) store).put(entity, component);
		// }
	}

	public UUID createEntity() {
		if (frozen)
			return null;

		final UUID uuid = UUID.randomUUID();
		entities.add(uuid);

		return uuid;
	}

	public UUID createEntity(String name) {
		if (frozen)
			return null;

		final UUID uuid = UUID.randomUUID();
		entities.add(uuid);
		entityStringNames.put(uuid, name);

		return uuid;
	}

	public void setEntityName(UUID entity, String name) {
		entityStringNames.put(entity, name);
	}

	public String nameFor(UUID entity) {
		return entityStringNames.get(entity);
	}

	public void killEntity(UUID entity) {
		if (frozen)
			return;

		// synchronized (componentStore) {

		for (HashMap<UUID, ? extends Component> entityComponents : componentStore.values()) {
			entityComponents.remove(entity);
		}
		entities.remove(entity);
		// }
	}

	public void freeze() {
		frozen = true;
	}

	public void unFreeze() {
		frozen = false;
	}
}
