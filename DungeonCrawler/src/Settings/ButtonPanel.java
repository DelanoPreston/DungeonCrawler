package Settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * GamePanel class that extends JPanel
 */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {

	//JPanel mapDraw;

	/**
	 * Constructor for the GamePanel class that extends JPanel
	 */
	public ButtonPanel() {
		// creates test buttons
		createButtonLayout();
	}

	/**
	 * createButtonLayout class creates buttons for the screen
	 */
	private void createButtonLayout() {
		JPanel cards = new JPanel(new CardLayout());
		cards.setBackground(new Color(0, 0, 0, 0));
		/*
		 * Button Panel creation/
		 */
		cards.add(setupTestButtons(), "Test Buttons");// I think the
														// "Test Buttons" is the
														// identifier for this
														// card

		// adds button pane to cards

		JPanel temp = new JPanel(new BorderLayout());
		temp.setBackground(new Color(0, 0, 0, 0));
		temp.add(cards, BorderLayout.PAGE_END);
		this.add(temp, BorderLayout.PAGE_START);
	}

	/**
	 * 
	 */
	public JPanel setupTestButtons() {
		JPanel temp = new JPanel();

		temp.setLayout(new RowMenuLayout(1, 64, 32, 5));// new GridLayout(3,
														// 0, 5, 5));
		temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		temp.setBackground(new Color(0, 0, 0, 0));// sets the portion of the
													// panel to transparent, so
													// I can see the map

		ButtonListener btnListener = new ButtonListener();

		String[] buttonNames = { "Toggle FOW", "Toggle MM", "Toggle Rm #s" };

		// this is the build menu cancel button
		JButton btn;

		for (int i = 0; i < buttonNames.length; i++) {
			btn = new JButton(buttonNames[i]);
			btn.setForeground(Color.BLACK);
			btn.setPreferredSize(new Dimension(64, 64));
			btn.setActionCommand(buttonNames[i]);
			btn.setBackground(new Color(0, 96, 0, 255));
			btn.addActionListener(btnListener);
			temp.add(btn);
		}

		JPanel retTemp = new JPanel(new BorderLayout());
		retTemp.add(temp, BorderLayout.SOUTH);

		return retTemp;
	}

	/**
	 * ButtonListener class, implements ActionListener, this class is used when
	 * a button is clicked
	 * 
	 * @author Preston Delano
	 * 
	 */
	private class ButtonListener implements ActionListener {

		public ButtonListener() {
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// gm - game menu
			if (arg0.getActionCommand().equals("Toggle FOW")) {
				if (Key.drawMMFogOfWar == true) {
					// Key.drawFogOfWar = false;
					Key.drawMMFogOfWar = false;
				} else {
					// Key.drawFogOfWar = true;
					Key.drawMMFogOfWar = true;
				}
			} else if (arg0.getActionCommand().equals("Toggle MM")) {
				if (Key.drawMiniMap == true) {
					Key.drawMiniMap = false;
				} else {
					Key.drawMiniMap = true;
				}
			} else if (arg0.getActionCommand().equals("Toggle Rm #s")) {
				if (Key.drawRoomNumbers == true) {
					Key.drawRoomNumbers = false;
				} else {
					Key.drawRoomNumbers = true;
				}
			}
		}
	}
}
