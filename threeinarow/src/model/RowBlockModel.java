package model;


/**
 * The RowBlockModel class represents a given block in the game.
 */
public class RowBlockModel
{
    /**
     * The game that contains this block
     */
    private RowGameModel game;

    /**
     * The current value of the contents of this block
     */
    private BoardChar contents;

    /**
     * Whether or not it is currently legal to move into this block
     */
    private boolean isLegalMove;

    /**
     * Creates a new block that will be contained in the given game.
     *
     * @param game The game that will contain the new block
     * @throws IllegalArgumentException When the given game is null
     */
    public RowBlockModel(RowGameModel game) {
        super();
        if (game == null) {
            throw new IllegalArgumentException("The game must be non-null.");
        }
        this.isLegalMove = true;
        this.game = game;
        this.reset();
    }

    public RowGameModel getGame() {
        return this.game;
    }

    /**
     * Sets the contents of this block to the given value.
     *
     * @param boardChar The new value for the contents of this block
     * @throws IllegalArgumentException When the given value is null
     */
    public void setContents(BoardChar boardChar) {
        if (boardChar == null) {
            throw new IllegalArgumentException("The value must be non-null.");
        }
        this.contents = boardChar;
    }

    /**
     * This is a helper method which converts the boardChar enum to string, which is to be sent to the view object.
     *
     * @return String value of the boardChar to be sent to view.
     */
    public String getSymbol() {
        switch (this.contents) {
            case ZERO_SYMBOL:
                return "O";
            case CROSS_SYMBOL:
                return "X";
            default:
                return "";
        }
    }

    /**
     * Returns the non-null String value of the contents of this block.
     *
     * @return The non-null String value
     */
    public BoardChar getContents() {
        return this.contents;
    }

    public void setIsLegalMove(boolean isLegalMove) {
        this.isLegalMove = isLegalMove;
    }

    public boolean getIsLegalMove() {
        return this.isLegalMove;
    }

    /**
     * Resets this block before starting a new game.
     */
    public void reset() {
        this.contents = BoardChar.EMPTY_SYMBOL;
        this.isLegalMove = true;
    }
}