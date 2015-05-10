package DataStructures;

public class ID {
	int id;
	int subID;
	int imageID;

	public ID() {
		id = -1;
		subID = -1;
	}

	public ID(int id) {
		this.id = id;
		subID = 0;
	}

	public ID(int id, int subID) {
		this.id = id;
		this.subID = subID;
	}
	
	public ID(int id, int subID, int imageID) {
		this.id = id;
		this.subID = subID;
		this.imageID = imageID;
	}

	public boolean equals(ID thingID) {
		return equals(thingID.id, thingID.subID);
	}

	public boolean equals(int inID, int inSubID) {
		if (inID == this.id && inSubID == this.subID)
			return true;
		return false;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setSubID(int subid) {
		this.subID = subid;
	}

	public int getID() {
		return id;
	}
	
	public int getImageID(){
		return imageID;
	}
	
	public int getSubID() {
		return subID;
	}
}
