package frame.panels;

import frame.GameFrame;
import frame.components.GameButton;
import frame.components.ScrollablePanel;
import frame.utils.GameColors;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represent the panel displayed when consulting the rules of the game
 *
 * @author Q.Cosnier
 */
public class RulesPanel extends CommonPanel {

    /**
     * Create a new panel to display the rules of the game
     *
     * @param lastPanel the panel to go back when the rules are closed
     */
    public RulesPanel(JPanel lastPanel) {
        setLayout(null);
        setSize(new Dimension(800, 600));
        this.add(new GameButton(new Point(getWidth() / 2 - 90, 10), new Dimension(180, 60), "Règles", null));
        this.add(new GameButton(new Point(getWidth() / 2 - 90, getHeight() - 10 - 60), new Dimension(180, 60), "Retour", () -> GameFrame.instance.changePanel(lastPanel)));
        ArrayList<String> test = new ArrayList<>();
        String[] rules = GameFrame.instance.getRules().split("\n");
        for (int i = 0; i < rules.length; i++) {
            test.add(rules[i]);
        }
        this.add(new ScrollablePanel(test, new Point(10, 80), new Dimension(getWidth() - 20, 400),15));
        setVisible(true);
    }

    /**
     * Paint the rules panel
     *
     * @param g the graphics used to paint the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(GameColors.FONTCOLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Update the panel so it can repaint itself
     *
     * @param game the game used to update the panel
     */
    @Override
    public void update(Game game) {
        repaint();
    }
}
