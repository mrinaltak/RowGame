// Author: Mrinal Tak
// Date: March 2021
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.RowGameRulesStrategy;
import javafx.util.Pair;

/**
 * This class is responsible for updating the blocks with the information received from the GUI class, regarding
 * toggling the blocks and updating the message displayed by each block.
 */
public class RowGameBoardView implements RowGameView{
    private JButton[][] blocks;
    private JPanel gamePanel;
    private JPanel game;
    private RowGameRulesStrategy controller;
    Pair<Integer, Integer> boardDimensions;
    private RowGameGUI rowGameGUI;

    public RowGameBoardView(Pair<Integer, Integer> boardDimensions, RowGameGUI rowGameGUI) {
        this.rowGameGUI = rowGameGUI;
        this.boardDimensions = boardDimensions;
        gamePanel = new JPanel(new FlowLayout());
        blocks = new JButton[boardDimensions.getKey()][boardDimensions.getValue()];
        game = new JPanel(new GridLayout(boardDimensions.getKey(), boardDimensions.getValue()));
        gamePanel.add(game, BorderLayout.CENTER);
    }

    /**
     * This method adds the action listeners to the blocks, which trigger the given controller.
     *
     * @param controller Controller which needs to be triggered on receiving input.
     */
    public void addController(RowGameRulesStrategy controller) {
        this.controller = controller;
        initializeBlocks();
    }

    /**
     * This method adds the action listeners to the blocks, which trigger the given controller.
     */
    private void initializeBlocks() {
        for (int row = 0; row < this.boardDimensions.getKey(); row++) {
            for (int column = 0; column < this.boardDimensions.getValue(); column++) {
                this.blocks[row][column] = new JButton("");
                this.blocks[row][column].setPreferredSize(new Dimension(75, 75));
                this.game.add(blocks[row][column]);
                this.blocks[row][column].setEnabled(true);
                int finalRow = row;
                int finalColumn = column;
                blocks[row][column].addActionListener(e -> this.controller.move(finalRow, finalColumn));
            }
        }
    }

    /**
     * This method resets all the blocks with the default text and enables all the blocks.
     */
    @Override
    public void update() {
        for (int row = 0; row < this.boardDimensions.getKey(); row++) {
            for (int column = 0; column < this.boardDimensions.getValue(); column++) {
                this.blocks[row][column].setText(this.rowGameGUI.getRowBlockModels()[row][column].getSymbol());
                this.blocks[row][column].setEnabled(this.rowGameGUI.getRowBlockModels()[row][column].getIsLegalMove());
            }
        }
    }

    public RowGameRulesStrategy getController() {
        return controller;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }
}