// Author: Mrinal Tak
// Date: March 2021
package model;

import controller.GameType;
import javafx.util.Pair;
import view.RowGameGUI;

/**
 * This class is called by the Controller object and it modifies the view object. It controls the logic segment of the
 * application.
 */
public class RowGameModel {
    // The dimensions of the board
    private Pair<Integer, Integer> boardDimensions;
    // Observer
    private RowGameGUI view = null;
    // This denotes the current player
    private Player player;
    // This denotes the number of moves which are left to be played.
    private int remainingMoves;
    // This denotes the logical board where different conditions are being checked.
    private RowBlockModel[][] board;
    // This denotes the final string which has to be sent to the View object.
    private String displayMessage;
    // This denotes the current state of the game. For eg, running, player1_won, draw, etc.
    private GameState gameState;
    private GameType gameType;
    // To incorporate Type-Safety, String constants are being used.
    // These are the strings to be sent to the view object.
    public static final String player1WinningMessage = "Player 1 wins!";
    public static final String player2WinningMessage = "Player 2 wins!";
    public static final String drawMessage = "Game ends in a draw";
    public static final String player1TurnMessage = "X: Player 1";
    public static final String player2TurnMessage = "O: Player 2";
    public static final String defaultMessage = "Player 1 to play 'X'";

    /**
     * This is the constructor of this class, which sets the first player as Player1 and also sets the gameState as
     * running.
     *
     * @param boardDimensions The dimensions of the board
     */
    public RowGameModel(Pair<Integer, Integer> boardDimensions) {
        setBoardDimensions(boardDimensions);
        setPlayer(Player.PLAYER1);
        setRemainingMoves(getBoardDimensions().getKey() * getBoardDimensions().getValue());
        setDisplayMessage(defaultMessage);
        setGameState(GameState.RUNNING);
    }

    /**
     * This method registers the Observer(view) to the Observable(RowGameModel)
     *
     * @param view Observer to be registered.
     */
    public void registerObserver(RowGameGUI view) {
        this.view = view;
        this.view.getGui().setTitle(gameType.toString());
        notifyObserver();
    }

    /**
     * This method removes the registered Observer(view) object.
     */
    public void unregisterObserver() {
        this.view = null;
    }

    /**
     * This method notifies the Observer(View) object of the changes made in the state of the game.
     */
    private void notifyObserver() {
        if (view == null) return;
        view.update(this.displayMessage, this.board);
    }

    /**
     * This method updates the logical board and checks if the game has stopped or not.
     * It also notifies the observers of any change in state.
     *
     * @param row    The x coordinate of the last added element to the board
     * @param column The y coordinate of the last added element to the board
     */
    public void setState(int row, int column) {
        if (row<0 || column<0 || row>=boardDimensions.getKey() || column>=boardDimensions.getValue() ||
                !board[row][column].getIsLegalMove()) {
            throw new IllegalArgumentException("The cell must correspond to a legal move.");
        }
        if (gameType.equals(GameType.THREE_IN_A_ROW) && row > 0) {
            board[row - 1][column].setIsLegalMove(true);
        }
        board[row][column].setIsLegalMove(false);
        setRemainingMoves(getRemainingMoves() - 1);
        switch (this.player) {
            case PLAYER1:
                board[row][column].setContents(BoardChar.CROSS_SYMBOL);
                break;
            case PLAYER2:
                board[row][column].setContents(BoardChar.ZERO_SYMBOL);
                break;
        }
        // After the execution of state change method, the observers are notified.
        stateChange(row, column);
        switch (getGameState()) {
            case PLAYER1_WINNER:
                setDisplayMessage(player1WinningMessage);
                setBlocksIllegal();
                break;
            case PLAYER2_WINNER:
                setDisplayMessage(player2WinningMessage);
                setBlocksIllegal();
                break;
            case DRAW:
                setDisplayMessage(drawMessage);
                break;
            default:
                if (player.equals(Player.PLAYER1)) {
                    setDisplayMessage(player2TurnMessage);
                    setPlayer(Player.PLAYER2);
                } else {
                    setDisplayMessage(player1TurnMessage);
                    setPlayer(Player.PLAYER1);
                }
                break;
        }
        // Notifying the observer after change of state.
        notifyObserver();
    }

    /**
     * Observable(RowGameModel) provides the getState method to the controller.
     *
     * @return The current state of the game.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * This method updates the state of the game by determining if the players have won or the match is a draw.
     *
     * @param row    The x coordinate of the last added element to the board
     * @param column The y coordinate of the last added element to the board
     */
    private void stateChange(int row, int column) {
        if (hasPlayerWon(row, column)) {
            if (board[row][column].getContents().equals(BoardChar.CROSS_SYMBOL)) {
                setGameState(GameState.PLAYER1_WINNER);
            } else {
                setGameState(GameState.PLAYER2_WINNER);
            }
        } else if (remainingMoves == 0) {
            setGameState(GameState.DRAW);
        } else setGameState(GameState.RUNNING);
    }

    /**
     * This method checks if the current position of x and y result in win for the player who made the last move.
     *
     * @param x The x coordinate of the last added element to the board
     * @param y The y coordinate of the last added element to the board
     * @return true if the any player has won.
     */
    private boolean hasPlayerWon(int x, int y) {
        // Checking if the current column has a winning pattern.
        if (checkWinningPattern(x, y, x - 1, y, x + 1, y)) return true;
        if (checkWinningPattern(x, y, x - 1, y, x - 2, y)) return true;
        if (checkWinningPattern(x, y, x + 1, y, x + 2, y)) return true;
        // Checking if the current row has a winning pattern.
        if (checkWinningPattern(x, y, x, y - 1, x, y + 1)) return true;
        if (checkWinningPattern(x, y, x, y - 1, x, y - 2)) return true;
        if (checkWinningPattern(x, y, x, y + 1, x, y + 2)) return true;
        // Checking if the diagonals have a winning solution
        if (checkWinningPattern(x, y, x + 1, y + 1, x - 1, y - 1)) return true;
        if (checkWinningPattern(x, y, x - 1, y - 1, x - 2, y - 2)) return true;
        if (checkWinningPattern(x, y, x + 1, y + 1, x + 2, y + 2)) return true;
        if (checkWinningPattern(x, y, x - 1, y + 1, x + 1, y - 1)) return true;
        if (checkWinningPattern(x, y, x - 1, y + 1, x - 2, y + 2)) return true;
        return checkWinningPattern(x, y, x + 2, y - 2, x + 1, y - 1);
    }

    /**
     * This method checks if three pairs of coordinates correspond to a winning pattern.
     *
     * @param x  Row of last block changed
     * @param y  Column of last block changed
     * @param x1 Row of a block to be checked
     * @param y1 Column of a block to be checked
     * @param x2 Row of third block changed
     * @param y2 Column of a block to be checked
     * @return true if this is a winning combination
     */
    private boolean checkWinningPattern(int x, int y, int x1, int y1, int x2, int y2) {
        int n = boardDimensions.getKey();
        int m = boardDimensions.getValue();
        if (x1 < 0 || x1 >= n || x2 < 0 || x2 >= n || y1 < 0 || y1 >= m || y2 < 0 || y2 >= m) return false;
        return board[x][y].getContents().equals(board[x1][y1].getContents())
                && board[x][y].getContents().equals(board[x2][y2].getContents());
    }

    /**
     * This method creates an empty instance of the logical board object.
     *
     * @param boardDimensions The dimensions of the board
     * @return new board object with all cells initialized with empty symbol.
     */
    public RowBlockModel[][] getEmptyBoard(Pair<Integer, Integer> boardDimensions) {
        int n = boardDimensions.getKey();
        int m = boardDimensions.getValue();
        RowBlockModel[][] boardChars = new RowBlockModel[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boardChars[i][j] = new RowBlockModel(this);
                if (this.gameType.equals(GameType.THREE_IN_A_ROW)) {
                    boardChars[i][j].setIsLegalMove(i == n - 1);
                }
            }
        }
        return boardChars;
    }

    public void setBlocksIllegal() {
        int n = boardDimensions.getKey();
        int m = boardDimensions.getValue();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.board[i][j].setIsLegalMove(false);
            }
        }
    }

    /**
     * This method is triggered by the controller object. It resets the state of the game and also asks the view object
     * to reset itself.
     */
    public void resetRowGameModel() {
        setPlayer(Player.PLAYER1);
        setRemainingMoves(getBoardDimensions().getKey() * getBoardDimensions().getValue());
        setDisplayMessage(defaultMessage);
        setBoard(getEmptyBoard(getBoardDimensions()));
        setGameState(GameState.RUNNING);
        notifyObserver();
    }

    public Pair<Integer, Integer> getBoardDimensions() {
        return this.boardDimensions;
    }

    public void setBoardDimensions(Pair<Integer, Integer> boardDimensions) {
        this.boardDimensions = boardDimensions;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getRemainingMoves() {
        return remainingMoves;
    }

    public void setRemainingMoves(int remainingMoves) {
        this.remainingMoves = remainingMoves;
    }

    public RowBlockModel[][] getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public void setBoard(RowBlockModel[][] board) {
        this.board = board;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}