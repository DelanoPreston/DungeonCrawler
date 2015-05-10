package Item;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import DataStructures.Location;
import Settings.DungeonPanel;

class InventoryMouseListener implements MouseListener, MouseMotionListener {
	DungeonPanel reference;
	// private int lastOffsetX;
	// private int lastOffsetY;
	Location location = new Location(0, 0);
	boolean showPopup;

	InventoryMouseListener() {
	}

	public Location getMouseAbsoluteLocation() {
		return location;
	}

	public Location GetPopupLocation() {
		// System.out.println("PopupLocation:" + location.getX() + "," +
		// location.getY());
		return location;
	}

	public void mousePressed(MouseEvent e) {
		// System.out.println("mousePressed");
	}

	public void mouseReleased(MouseEvent e) {
		// System.out.println("mouseReleased");
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getMousePosition(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
		// System.out.println("get mouse position {" + x + ", " + y + "}");
	}
}