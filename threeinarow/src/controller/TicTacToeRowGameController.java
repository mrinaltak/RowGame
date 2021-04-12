// Author: Mrinal Tak
// Date: March 2021
package controller;

import model.GameState;
import model.RowGameModel;

/**
 * This class contains methods which are triggered by the view class and this class interacts with model class.
 */
public class TicTacToeRowGameController implements RowGameRulesStrategy {
    // The instance of the model class which needs to be modified by the controller.
    private RowGameModel rowGameModel;

    @Override
    public GameType gameType() {
        return GameType.TIC_TAC_TOE;
    }

    @Override
    public void setModel(RowGameModel model) {
        this.rowGameModel = model;
        this.rowGameModel.setGameType(gameType());
        this.rowGameModel.setBoard(this.rowGameModel.getEmptyBoard(this.rowGameModel.getBoardDimensions()));
    }

    @Override
    public void reset() {
        this.rowGameModel.resetRowGameModel();
    }

    @Override
    public void move(int row, int col) {
        this.rowGameModel.setState(row, col);
    }

    @Override
    public boolean isWin() {
        return this.rowGameModel.getGameState().equals(GameState.PLAYER1_WINNER) ||
                this.rowGameModel.getGameState().equals(GameState.PLAYER2_WINNER);
    }

    @Override
    public boolean isTie() {
        return this.rowGameModel.getGameState().equals(GameState.DRAW);
    }
}