package Item;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import DataStructures.Location;
import Settings.Key;

class InventoryMouseListener implements MouseListener, MouseMotionListener {
	Inventory inventory;
	// private int lastOffsetX;
	// private int lastOffsetY;
	Location location = new Location(0, 0);
	boolean showPopup;
	Item heldItem = null;

	public InventoryMouseListener(Inventory i) {
		inventory = i;
	}

	public Location getMouseAbsoluteLocation() {
		return location;
	}

	public Location GetInventoryLocation() {
		// System.out.println("PopupLocation:" + location.getX() + "," +
		// location.getY());
		return new Location((int) Math.floor(location.getX() / Key.tileSizeInventory), (int) Math.floor(location.getY() / Key.tileSizeInventory));
	}

	public void mousePressed(MouseEvent e) {
		// System.out.println("mousePressed");
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getModifiers() == 16) {//left click
			if (heldItem == null) {
				Location temp2 = new Location((int) GetInventoryLocation().getX(), (int) GetInventoryLocation().getY());
				heldItem = inventory.removeItemFrom((int) temp2.getX(), (int) temp2.getY());
//				if (heldItem != null) {
//					System.out.println("got item " + heldItem.getName() + " in hand from: " + temp2.toString());
//				} else
//					System.out.println("no item at location: " + temp2.toString());
			} else {
//				System.out.println("trying to put item down");
				if (inventory.putItemIn(heldItem, (int) GetInventoryLocation().getX(), (int) GetInventoryLocation().getY())) {

//					Location temp = new Location((int) GetInventoryLocation().getX(), (int) GetInventoryLocation().getY());
//					System.out.println("put item " + heldItem.getName() + " in inv at " + temp.toString());
					heldItem = null;
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		getMousePosition(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getMousePosition(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println(GetInventoryLocation().getX() + "," +
		// GetInventoryLocation().getY());

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}

	public void getMousePosition(MouseEvent e) {
		// System.out.println("get mouse position {" + e.getX() + ", " +
		// e.getY() + "}");
		// location = new Location(e.getX(), e.getY());

		location = new Location((float) e.getX(), (float) e.getY());
		// System.out.println("get mouse position {" + e.getX() + ", " +
		// e.getY() + "}");
	}
}