package Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

import Item.Inventory;
import Item.Interfaces.IItem;

public class CustomEventSource implements Serializable{
	private static final long serialVersionUID = 1515868116459166516L;
	private List<EventListener> _listeners = new ArrayList<>();

	public synchronized void addEventListener(EventListener listener) {
		_listeners.add(listener);
	}

	public synchronized void removeEventListener(EventListener listener) {
		_listeners.remove(listener);
	}

	// call this method whenever you want to notify
	// the event listeners of the particular event
	public synchronized void NewInventory(Inventory inv, IItem item) {
		InventoryEvent event = new InventoryEvent(this, item, inv);
		Iterator<EventListener> i = _listeners.iterator();
		while (i.hasNext()) {
			((InventoryEventClassListener) i.next()).handleNewInventory(event);
		}
	}
	public synchronized void RemoveInventory(Inventory inv, IItem item){
		InventoryEvent event = new InventoryEvent(this, item, inv);
		Iterator<EventListener> i = _listeners.iterator();
		while (i.hasNext()) {
			((InventoryEventClassListener) i.next()).handleRemoveInventory(event);
		}
	}
//	public synchronized int getEntityCountEvent(String s, String subS){
//		StringEvent event = new StringEvent(this, s, subS);
//		Iterator<EventListener> i = _listeners.iterator();
//		while (i.hasNext()) {
//			return ((EventListener) i.next()).handleGetEntityCountEvent(event);
//		}
//		return 0;
//	}
//	public synchronized int[] getAdjacentTileLocation(Entity entity, String sType){
//		EntityEvent event = new EntityEvent(this, entity, sType);
//		Iterator<EventListener> i = _listeners.iterator();
//		while (i.hasNext()) {
//			return ((EventListener) i.next()).handleGetAdjacentTileLocation(event);
//		}
//		return null;
//	}
//	public synchronized Map getMap(){
//		Iterator<EventListener> i = _listeners.iterator();
//		while (i.hasNext()) {
//			return ((EventListener) i.next()).handleGetMap();
//		}
//		return null;
//	}
//
//	public synchronized void createStructure(Entity entity, String subS){
//		EntityEvent event = new EntityEvent(this, entity, subS);
//		Iterator<EventListener> i = _listeners.iterator();
//		while (i.hasNext()) {
//			((EventListener) i.next()).handleCreateStructure(event);
//		}
//	}
}
