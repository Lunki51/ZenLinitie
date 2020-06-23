package frame.panels;

import game.Game;

import javax.swing.*;

/**
 * Used to represent every panel in the application
 * This abstract class represent a panel that can be updated with a game object
 *
 * @author Q.Cosnier
 */
public abstract class CommonPanel extends JPanel {
    /**
     * Update the panel
     *
     * @param game the game used to update the panel
     */
    public abstract void update(Game game);
}
