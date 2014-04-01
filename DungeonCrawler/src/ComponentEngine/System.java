package ComponentEngine;

public class System {
	private int priority;

	public System() {
		this(0);
	}

	public System(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void addedToEngine(EntityManager engine) {

	}

	public void removedFromEngine(EntityManager engine) {

	}

	public void update(float deltaTime) {

	}
}
