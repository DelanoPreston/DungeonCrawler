package Settings;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class RowMenuLayout implements LayoutManager {
	private int buttonGap, buttonHeight, buttonWidth;
	private int rows;
	private int minWidth = 0;// , minHeight = 0;
	private int preferredWidth = 0, preferredHeight = 0;
	private boolean sizeUnknown = true;

	public RowMenuLayout() {
		this(1, 64, 32, 5);
	}

	public RowMenuLayout(int inRows, int inButtonWidth, int inButtonHeight, int inButtonGap) {
		rows = inRows;
		buttonWidth = inButtonWidth;
		buttonHeight = inButtonHeight;
		buttonGap = inButtonGap;
	}

	/* Required by LayoutManager. */
	public void addLayoutComponent(String name, Component comp) {
	}

	/* Required by LayoutManager. */
	public void removeLayoutComponent(Component comp) {
	}

	private void setSizes(Container parent) {
		int nComps = parent.getComponentCount();

		preferredHeight = ((nComps >= rows ? rows : nComps) * buttonHeight) + ((nComps >= rows ? rows + 1 : nComps + 1) * buttonGap);

		preferredWidth = ((nComps % rows + 1) * buttonWidth) + ((nComps % rows) * buttonGap);
	}

	/* Required by LayoutManager. */
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		// int nComps = parent.getComponentCount();

		setSizes(parent);

		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	/* Required by LayoutManager. */
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	/* Required by LayoutManager. */
	/*
	 * This is called when the panel is first displayed, and every time its size
	 * changes. Note: You CAN'T assume preferredLayoutSize or minimumLayoutSize
	 * will be called -- in the case of applets, at least, they probably won't
	 * be.
	 */
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int nComps = parent.getComponentCount();
		// int previousWidth = 0, previousHeight = 0;
		int x = 0, y = insets.top;
		// int rowh = 0, start = 0;
		// int xFudge = 0, yFudge = 0;
		boolean oneColumn = true;

		// Go through the components' sizes, if neither
		// preferredLayoutSize nor minimumLayoutSize has
		// been called.
		if (sizeUnknown) {
			setSizes(parent);
		}

		if (maxWidth <= minWidth) {
			oneColumn = true;
		}

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				Dimension d = c.getPreferredSize();

				// increase x and y, if appropriate
				int tempX = (i / rows) + 1;

				x = (int) (maxWidth - d.getWidth() * tempX);

				int tempY = (i % rows) + 1;

				y = (int) (maxHeight - (d.getHeight() * tempY));

				// If x is too large,
				if ((!oneColumn) && (x + d.width) > (parent.getWidth() - insets.right)) {
					// reduce x to a reasonable number.
					x = parent.getWidth() - insets.bottom - d.width;
				}

				// Set the component's size and position.
				c.setBounds(x, y, d.width, d.height);
			}
		}
	}

	public String toString() {
		String str = "";
		return getClass().getName() + "[vgap=" + buttonGap + str + "]";
	}

}
