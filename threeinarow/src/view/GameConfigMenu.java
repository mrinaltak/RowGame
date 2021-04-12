// Author: Mrinal Tak
// Date: March 2021
package view;

import controller.GameType;
import javafx.util.Pair;

import javax.swing.*;

/**
 * This class creates a GUI to get the Game type input and also gets the dimensions of the board from the user as inputs.
 */
public class GameConfigMenu {

    /**
     * This method creates a GUI to get the Game type input and also gets the dimensions of the board from the user as inputs.
     *
     * @return Pair of GameType and dimensions of the board.
     */
    public static Pair<GameType, Pair<Integer, Integer>> getGameConfig() {
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton three_in_a_row = new JRadioButton("Three in a row");
        JRadioButton tic_tac_toe = new JRadioButton("Tic Tac Toe");
        buttonGroup.add(three_in_a_row);
        buttonGroup.add(tic_tac_toe);
        JTextField rowCount = new JTextField(5);
        JTextField columnCount = new JTextField(5);
        JPanel myPanel = new JPanel();
        myPanel.setSize(900, 300);
        myPanel.add(new JLabel("GameType:"));
        myPanel.add(three_in_a_row);
        myPanel.add(tic_tac_toe);
        three_in_a_row.setSelected(true);
        myPanel.add(Box.createHorizontalStrut(30));
        myPanel.add(new JLabel("Row count:"));
        myPanel.add(rowCount);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Column count:"));
        myPanel.add(columnCount);
        rowCount.setText("3");
        columnCount.setText("3");
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Game type and dimensions of the board", JOptionPane.OK_CANCEL_OPTION);
        GameType gameType = GameType.THREE_IN_A_ROW;
        int n = 3, m = 3;
        if (result == JOptionPane.OK_OPTION) {
            if (tic_tac_toe.isSelected()) {
                gameType = GameType.TIC_TAC_TOE;
            }
            n = Integer.parseInt(rowCount.getText());
            m = Integer.parseInt(columnCount.getText());
            if (n <= 0) n = 3;
            if (m <= 0) m = 3;
        }
        return new Pair<>(gameType, new Pair<>(n, m));
    }
}
