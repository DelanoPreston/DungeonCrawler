package Event;

import java.util.EventObject;

public class StringEvent extends EventObject {
	private static final long serialVersionUID = -2516985610201628221L;
	public String command;
	public String subCommand;
	
	// here's the constructor
	public StringEvent(Object source, String inCommand, String inSubCommand) {
		super(source);
		command = inCommand;
		subCommand = inSubCommand;
	}
}
