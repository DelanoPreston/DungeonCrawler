package Event;

import java.util.EventListener;

public interface InventoryEventClassListener extends EventListener{
	public void handleNewInventory(InventoryEvent s);
	public void handleRemoveInventory(InventoryEvent s);
	
//	public Entity handleFindEntityEvent(HumanEntityEvent e);
//	public void handleRemoveEntityEvent(EntityEvent e);
//	public int handleGetEntityCountEvent(StringEvent e);
//	public int[] handleGetAdjacentTileLocation(EntityEvent e);
//	public Map handleGetMap();
//	public Location handleGetTileAtLocation(Location e);
//	public void handleCreateStructure(EntityEvent e);
}
