package Item;

import javax.swing.JLabel;

import DataStructures.Location;


//I will hold off on this for now, do i want an individual popup to come for each item? or a display above all the items showing what the item being moused over is
public class ItemInfoLabel extends JLabel{
	private static final long serialVersionUID = -8480720553760376546L;
	
	public ItemInfoLabel(Item item){
		
	}
	
	public void setLocation(Location loc){
		this.setLocation((int)loc.getX(), (int)loc.getY());// (loc.getPoint());
	}
}
