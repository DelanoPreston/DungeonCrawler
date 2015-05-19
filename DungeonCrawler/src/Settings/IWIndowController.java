package Settings;

import Item.Inventory;

public interface IWIndowController {
	void newInventoryWindow(String name, Inventory inv);

	void removeWindow(String name);

	void showWindows();

	void hideWindows();
}
