// Author: Mrinal Tak
// Date: March 2021
package view;

import controller.TicTacToeRowGameController;
import controller.RowGameRulesStrategy;
import javafx.util.Pair;
import model.RowBlockModel;

import javax.swing.*;
import java.awt.*;

/**
 * This class contains all the methods which  correspond to updating the GUI. The View is only modified by the Model class.
 * And the View class updates the controller when the input is captured by the GUI.
 */
public class RowGameGUI {
    private RowGameBoardView rowGameBoardView;
    private RowGameStatusView rowGameStatusView;
    private RowGameRulesStrategy controller;
    private JButton reset;
    private JFrame gui;
    private String statusMessage;
    private RowBlockModel[][] rowBlockModels;

    /**
     * The constructor for the view class takes as input the dimensions of the board. It initializes the different JAVA
     * Swing elements.
     *
     * @param boardDimensions Pair of row, column size for the board.
     */
    public RowGameGUI(Pair<Integer, Integer> boardDimensions) {
        rowGameStatusView = new RowGameStatusView(this);
        rowGameBoardView = new RowGameBoardView(boardDimensions, this);
        initializeGUI();
    }

    private void initializeGUI() {
        reset  = new JButton("Reset");
        this.gui = new JFrame();
        this.gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gui.setSize(new Dimension(500, 500));
        this.gui.setResizable(true);
        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);
        this.gui.add(rowGameBoardView.getGamePanel(), BorderLayout.NORTH);
        this.gui.add(options, BorderLayout.CENTER);
        this.gui.add(rowGameStatusView.getMessages(), BorderLayout.SOUTH);
    }

    /**
     * This method adds actionListeners on reset button and the block elements.
     *
     * @param controller {@link TicTacToeRowGameController} object which gets called when a user input is received.
     */
    public void setController(RowGameRulesStrategy controller) {
        this.controller = controller;
        rowGameBoardView.addController(this.controller);
        this.reset.addActionListener(e -> this.controller.reset());
        this.gui.setVisible(true);
    }

    /**
     * Observer(View class) provides the update method to the Observable (RowGameModel).
     * This method is called by the model to display the message string and also enable and disable certain blocks.
     * @param board
     */
    public void update(String status, RowBlockModel[][] board) {
        this.statusMessage = status;
        this.rowBlockModels = board;
        rowGameStatusView.update();
        rowGameBoardView.update();
    }

    public JFrame getGui() {
        return gui;
    }

    public RowGameStatusView getRowGameStatusView() {
        return rowGameStatusView;
    }
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public RowBlockModel[][] getRowBlockModels() {
        return rowBlockModels;
    }
}