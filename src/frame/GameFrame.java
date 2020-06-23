package frame;

import frame.utils.Images;
import game.BoardManager;
import game.Game;

import javax.swing.*;

/**
 * Represent a game running on a frame
 *
 * @author Q.Cosnier
 */
public class GameFrame extends Game {

    public MainFrame frame;
    public static GameFrame instance;

    /**
     * Create a new Game
     *
     */
    public GameFrame() {
        super();
        instance = this;
    }

    /**
     * Executed when a game is launched
     */
    @Override
    public void start() {
        Images.create();
        this.frame = new MainFrame("Zen l'initie");
    }

    /**
     * The method that is looping while the game is running
     */
    @Override
    public void loop() {
        this.frame.update(this);
    }

    /**
     * The method that is executed when the game stop
     */
    @Override
    public void stop() {
    }

    /**
     * Change the current panel to display
     *
     * @param panel the new panel
     */
    public void changePanel(JPanel panel) {
        this.frame.changePanel(panel);
    }

    /**
     * Set the players types for the start of a game
     *
     * @param white the type of the white player
     * @param black the type of the black player
     */
    public void setPlayersTypes(boolean white, boolean black) {
        this.playerBType = black;
        this.playerWType = white;
    }

    /**
     * Reset the game
     */
    public void reset() {
        this.manager = new BoardManager();
    }
}
