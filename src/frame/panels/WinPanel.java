package frame.panels;

import frame.GameFrame;
import frame.components.GameButton;
import frame.utils.GameColors;
import game.Game;
import game.Types;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A panel used to represent what is displayed at the end of a game
 *
 * @author Q.Cosnier
 */
public class WinPanel extends CommonPanel {

    String text;

    /**
     * Create a new win panel to display the winner of a game
     *
     * @param winner who is the winner or ZEN if draw
     */
    public WinPanel(Types winner) {
        setLayout(null);
        setSize(800, 600);
        switch (winner) {
            case BLACK:
                text = "Victoire pour le joueur noir";
                break;
            case WHITE:
                text = "Victoire pour le joueur blanc";
                break;
            case ZEN:
                text = "EGALITEE";
                break;
        }
        add(new GameButton(new Point(getWidth() / 2 - 90, getHeight() - 100), new Dimension(180, 60), "Menu", () -> GameFrame.instance.changePanel(new MenuPanel())));
    }

    /**
     * Paint the panel
     *
     * @param g the graphics used to paint the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(GameColors.FONTCOLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Broadway", Font.PLAIN, 20));
        Rectangle2D rectangle2D = g.getFontMetrics().getStringBounds(this.text, g);
        g.drawString(this.text, (int) (getWidth() / 2 - (rectangle2D.getWidth() / 2)), (int) (getHeight() / 2 - (rectangle2D.getHeight() / 2)));
    }

    /**
     * Update the frame so it can repaint itself
     *
     * @param game the game used to update the panel
     */
    @Override
    public void update(Game game) {
        repaint();
    }
}
