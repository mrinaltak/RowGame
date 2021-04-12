// Author: Mrinal Tak
// Date: March 2021
import controller.GameType;
import controller.ThreeInARowGameController;
import controller.TicTacToeRowGameController;
import controller.RowGameRulesStrategy;
import javafx.util.Pair;
import model.RowGameModel;
import view.GameConfigMenu;
import view.RowGameGUI;

/**
 * This is the driver class, which instantiates a model, view and controller and also aggregates the three objects.
 * The class supports extensibility, as the game board size has not been hardcoded.
 */
public class RowGameApp {
    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
        Pair<Integer, Integer> dimensions;
        GameType gameType;
        // Get the GameType and dimensions as inputs using GUI.
        Pair<GameType,Pair<Integer,Integer>> gameConfig = GameConfigMenu.getGameConfig();
        dimensions = gameConfig.getValue();
        gameType = gameConfig.getKey();
        // Change the controller from here
        RowGameRulesStrategy rowGameController;
        if (gameType.equals(GameType.THREE_IN_A_ROW)) {
            rowGameController = new ThreeInARowGameController();
        } else {
            rowGameController = new TicTacToeRowGameController();
        }
        RowGameModel rowGameModel = new RowGameModel(dimensions);
        RowGameGUI rowGameGUI = new RowGameGUI(dimensions);
        rowGameController.setModel(rowGameModel);
        rowGameGUI.setController(rowGameController);
        rowGameModel.registerObserver(rowGameGUI);
    }
}