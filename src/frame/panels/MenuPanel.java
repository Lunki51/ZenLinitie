package frame.panels;

import frame.GameFrame;
import frame.components.GameButton;
import frame.utils.GameColors;
import game.Game;

import java.awt.*;

import static frame.utils.Images.*;

/**
 * Represent the menu panel that is displayed when launching the application
 *
 * @author Q.Cosnier
 */
public class MenuPanel extends CommonPanel {

    /**
     * Create a new menuPanel to display the menu
     */
    public MenuPanel() {
        setLayout(null);
        setSize(new Dimension(800, 600));
        add(new GameButton(new Point(50, 150), new Dimension(180, 60), "Jouer", () -> GameFrame.instance.changePanel(new LaunchPanel())));
        add(new GameButton(new Point(50, 250), new Dimension(180, 60), "Quitter", () -> System.exit(0)));
        add(new GameButton(new Point(600, 150), new Dimension(180, 60), "Regles", () -> GameFrame.instance.changePanel(new RulesPanel(this))));
        add(new GameButton(new Point(600, 250), new Dimension(180, 60), "Charger", () -> GameFrame.instance.changePanel(new SavePanel(this, false))));

    }

    /**
     * Paint the menu Panel
     *
     * @param g the graphics used to paint the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(GameColors.FONTCOLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.YELLOW);
        g.fillRect(5, 2, 2, getHeight() - 4);
        g.fillRect(getWidth() - 7, 2, 2, getHeight() - 4);
        g.fillRect(2, 5, getWidth() - 4, 2);
        g.fillRect(2, getHeight() - 7, getWidth() - 4, 2);

        g.setColor(GameColors.DARKFONT);
        g.fillOval(getWidth() / 2 - 150, 75, 300, 300);
        g.drawImage(LOGOIMAGE, getWidth() / 2 - 150, 75, 295, 295, this);

        g.drawImage(DRAGONIMAGE, 0, getHeight() - 200, 380, 200, this);
        g.drawImage(DRAGONFLIPPED, getWidth() - 380, getHeight() - 200, 380, 200, this);
    }

    /**
     * Update the menu panel so it can repaint itself
     *
     * @param game the game used to update the panel
     */
    @Override
    public void update(Game game) {
        repaint();
    }
}
