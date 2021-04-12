import controller.GameType;
import controller.RowGameRulesStrategy;
import controller.ThreeInARowGameController;
import controller.TicTacToeRowGameController;
import javafx.util.Pair;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import view.RowGameGUI;
import view.RowGameStatusView;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameModel rowGameModel;
    private RowGameGUI rowGameGUI;
    private RowGameStatusView rowGameStatusView;
    private RowGameRulesStrategy controller;

    @Before
    public void setUp() {
        rowGameModel = new RowGameModel(new Pair<>(3,3));
        rowGameGUI = new RowGameGUI(new Pair<>(3,3));
        rowGameStatusView = rowGameGUI.getRowGameStatusView();
    }

    @After
    public void tearDown() {
        rowGameModel = null;
    }

    @Test
    public void testNewGame() {
        assertEquals (Player.PLAYER1, rowGameModel.getPlayer());
        assertEquals (9, rowGameModel.getRemainingMoves());
        assertEquals(GameState.RUNNING, rowGameModel.getGameState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
        RowBlockModel block = new RowBlockModel(null);
    }

    @Test
    public void testViewStatusUpdates() {
        rowGameGUI.setStatusMessage("Player 2");
        rowGameStatusView.update();
        assertEquals("Player 2",rowGameStatusView.getPlayerTurn().getText());
    }

    @Test
    public void testControllerStrategy() {
        controller = new ThreeInARowGameController();
        assertEquals(GameType.THREE_IN_A_ROW,controller.gameType());
        controller = new TicTacToeRowGameController();
        assertEquals(GameType.TIC_TAC_TOE,controller.gameType());
    }

    @Test
    public void testModelState() {
        controller = new ThreeInARowGameController();
        controller.setModel(rowGameModel);
        assertEquals (Player.PLAYER1, rowGameModel.getPlayer());
        assertEquals (9, rowGameModel.getRemainingMoves());
        assertEquals(GameState.RUNNING, rowGameModel.getGameState());
        rowGameModel.setState(2,2);
        assertEquals(GameState.RUNNING, rowGameModel.getGameState());
        assertEquals(Player.PLAYER2,rowGameModel.getPlayer());
        assertEquals(8,rowGameModel.getRemainingMoves());
        rowGameModel.setState(2,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMoveThreeInARow() {
        controller = new ThreeInARowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(1,2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMoveTicTacToe() {
        controller = new TicTacToeRowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(-1,2);
    }

    @Test
    public void testLegalMoveThreeInARow() {
        controller = new ThreeInARowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        BoardChar initialBoardChar = rowGameModel.getBoard()[2][0].getContents();
        rowGameModel.setState(2,0);
        BoardChar finalBoardChar = rowGameModel.getBoard()[2][0].getContents();
        assertNotEquals(initialBoardChar, finalBoardChar);
    }

    @Test
    public void testLegalMoveTicTacToe() {
        controller = new TicTacToeRowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        BoardChar initialBoardChar = rowGameModel.getBoard()[0][0].getContents();
        rowGameModel.setState(0,0);
        BoardChar finalBoardChar = rowGameModel.getBoard()[0][0].getContents();
        assertNotEquals(initialBoardChar, finalBoardChar);
    }

    @Test
    public void testPlayerWinThreeInARow() {
        controller = new ThreeInARowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(2,0);
        rowGameModel.setState(2,1);
        rowGameModel.setState(1,0);
        rowGameModel.setState(1,1);
        rowGameModel.setState(0,0);
        assertEquals(GameState.PLAYER1_WINNER,rowGameModel.getGameState());
        assertEquals("Player 1 wins!",rowGameModel.getDisplayMessage());
    }

    @Test
    public void testPlayerWinTicTacToe() {
        controller = new TicTacToeRowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(2,0);
        rowGameModel.setState(2,1);
        rowGameModel.setState(1,0);
        rowGameModel.setState(1,1);
        rowGameModel.setState(0,2);
        rowGameModel.setState(0,1);
        assertEquals(GameState.PLAYER2_WINNER,rowGameModel.getGameState());
        assertEquals("Player 2 wins!",rowGameModel.getDisplayMessage());
    }

    @Test
    public void testTieThreeInARow() {
        controller = new ThreeInARowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(2,2);
        rowGameModel.setState(2,0);
        rowGameModel.setState(2,1);
        rowGameModel.setState(1,2);
        rowGameModel.setState(1,0);
        rowGameModel.setState(1,1);
        rowGameModel.setState(0,2);
        rowGameModel.setState(0,1);
        rowGameModel.setState(0,0);
        assertEquals(GameState.DRAW,rowGameModel.getGameState());
        assertEquals("Game ends in a draw",rowGameModel.getDisplayMessage());
    }

    @Test
    public void testTieTicTacToe() {
        controller = new TicTacToeRowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(0,0);
        rowGameModel.setState(0,1);
        rowGameModel.setState(0,2);
        rowGameModel.setState(1,1);
        rowGameModel.setState(1,0);
        rowGameModel.setState(1,2);
        rowGameModel.setState(2,1);
        rowGameModel.setState(2,0);
        rowGameModel.setState(2,2);
        assertEquals(GameState.DRAW,rowGameModel.getGameState());
        assertEquals("Game ends in a draw",rowGameModel.getDisplayMessage());
    }

    @Test
    public void testResetThreeInARow() {
        controller = new ThreeInARowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(2,2);
        rowGameModel.setState(2,0);
        rowGameModel.setState(2,1);
        rowGameModel.setState(1,2);
        rowGameModel.setState(1,0);
        rowGameModel.setState(1,1);
        controller.reset();
        assertEquals (Player.PLAYER1, rowGameModel.getPlayer());
        assertEquals (9, rowGameModel.getRemainingMoves());
        assertEquals(GameState.RUNNING, rowGameModel.getGameState());
    }

    @Test
    public void testResetTicTacToe() {
        controller = new TicTacToeRowGameController();
        controller.setModel(rowGameModel);
        rowGameModel.resetRowGameModel();
        rowGameModel.setState(0,0);
        rowGameModel.setState(0,1);
        rowGameModel.setState(0,2);
        rowGameModel.setState(1,1);
        controller.reset();
        assertEquals (Player.PLAYER1, rowGameModel.getPlayer());
        assertEquals (9, rowGameModel.getRemainingMoves());
        assertEquals(GameState.RUNNING, rowGameModel.getGameState());
    }
}