// Author: Mrinal Tak
// Date: March 2021
package view;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This class is responsible for displaying the game status messages in the GUI.
 */
public class RowGameStatusView implements RowGameView
{
	private JTextArea playerTurn = new JTextArea();
	private JPanel messages = new JPanel(new FlowLayout());
	private RowGameGUI rowGameGUI;

	public RowGameStatusView(RowGameGUI rowGameGUI) {
		messages.setBackground(Color.white);
		messages.add(playerTurn);
		this.rowGameGUI = rowGameGUI;
	}

	public JPanel getMessages() {
		return messages;
	}

	@Override
	public void update() {
		this.playerTurn.setText(rowGameGUI.getStatusMessage());
	}

	public JTextArea getPlayerTurn() {
		return playerTurn;
	}
}