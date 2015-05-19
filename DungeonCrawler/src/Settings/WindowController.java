package Settings;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import Item.Inventory;

public class WindowController implements IWIndowController {

	private JFrame gameWindow;
	HashMap<String, JInternalFrame> frames = new HashMap<>();

	public WindowController(JFrame window) {
		gameWindow = window;
	}

	@Override
	public void newInventoryWindow(String name, Inventory inv) {
		JInternalFrame testInv = new JInternalFrame("inventory", true, false, false, false);

		testInv.add(inv);
//		testInv.pack();

		gameWindow.add(testInv);
		testInv.setVisible(true);
		frames.put(name, testInv);
		System.out.println("window controller reporting");
		showWindows();
		System.out.println(testInv.isVisible());
	}

	@Override
	public void removeWindow(String name) {

	}

	@Override
	public void showWindows() {
		for (Entry<String, JInternalFrame> f : frames.entrySet()) {
			f.getValue().setVisible(true);
		}
	}

	@Override
	public void hideWindows() {
		for (Entry<String, JInternalFrame> f : frames.entrySet()) {
			f.getValue().setVisible(false);
		}
	}

}
