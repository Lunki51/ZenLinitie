package frame.components;

import frame.utils.GameColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Represent the board of a game and every action that can be done to the pawns on this board
 *
 * @author Q.Cosnier
 */
public class GameButton extends JComponent {

    boolean entered;

    /**
     * Create a new button component to be displayed in the application
     *
     * @param location  the location of the component on the panel
     * @param dimension the dimension of the component
     * @param title     the title to display in the button
     * @param action    the action performed when pressing the button
     */
    public GameButton(Point location, Dimension dimension, String title, Runnable action) {
        this.setLocation(location);
        this.setSize(dimension);
        this.setName(title);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
                entered = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                entered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                entered = false;
            }
        });
    }

    /**
     * Paint the button
     *
     * @param g the graphics used to paint the button
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("Broadway", Font.PLAIN, 20));
        g.setColor(GameColors.BUTTONCOLOR);
        g.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 10, 10);
        g.setColor(this.entered ? Color.BLUE : Color.BLACK);
        g.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 10, 10);
        Rectangle2D rectangle2D = g.getFontMetrics().getStringBounds(this.getName(), g);
        g.drawString(this.getName(), getWidth() / 2 - (int) rectangle2D.getWidth() / 2, getHeight() / 2 + (int) rectangle2D.getHeight() / 2);

    }
}
