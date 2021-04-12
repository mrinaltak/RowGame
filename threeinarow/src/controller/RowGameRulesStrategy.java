// Author: Mrinal Tak
// Date: March 2021
package controller;

import model.RowGameModel;

/**
 * This is the interface for the Controller class.
 */
public interface RowGameRulesStrategy {

    public GameType gameType();
    /**
     * This method adds the model to the controller class.
     *
     * @param model Model object to be modified by the controller.
     */
    public void setModel(RowGameModel model);

    /**
     * This method resets the logical gameModel object.
     *
     */
    public void reset();

    /**
     * This method informs the model object about the next move at position row, col.
     *
     * @param row Last accessed row of the board.
     * @param col Last accessed col of the board.
     */
    public void move(int row, int col);

    /**
     * This method denotes if the game has resulted in a win state or not.
     *
     * @return true if the game has resulted in a win state.
     */
    public boolean isWin();

    /**
     * This method denotes if the game has resulted in a tie state or not.
     *
     * @return true if the game has resulted in a tie state.
     */
    public boolean isTie();
}