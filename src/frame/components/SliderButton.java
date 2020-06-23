package frame.components;

import frame.utils.GameColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Represent a button where you can select a choice by sliding different options
 *
 * @author Q.Cosnier
 */
public class SliderButton extends JComponent {

    boolean entered;
    int indice;
    ArrayList<String> data;

    /**
     * Create a new slider button
     *
     * @param position the position of the button in the parent frame
     * @param size     the size of the button
     * @param data     the array of data that represent the options of the button
     */
    public SliderButton(Point position, Dimension size, ArrayList<String> data) {
        this.indice = 0;
        this.data = data;
        this.setLocation(position);
        this.setSize(size);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() < 20) previous();
                if (e.getX() > getWidth() - 20) next();
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
     * Paint the slider button
     *
     * @param g the graphics used to paint the button
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("Broadway", Font.PLAIN, 20));
        g.setColor(GameColors.BUTTONCOLOR);
        g.fillRoundRect(10, 0, getWidth() - 21, getHeight() - 1, 10, 10);
        g.setColor(this.entered ? Color.BLUE : Color.BLACK);
        g.drawRoundRect(10, 0, getWidth() - 21, getHeight() - 1, 10, 10);
        g.drawLine(2, getHeight() / 2 - 2, 10, getHeight() / 2 - 2);
        g.drawLine(2, getHeight() / 2 + 2, 10, getHeight() / 2 + 2);
        g.drawLine(getWidth() - 10, getHeight() / 2 - 2, getWidth(), getHeight() / 2 - 2);
        g.drawLine(getWidth() - 10, getHeight() / 2 + 2, getWidth(), getHeight() / 2 + 2);
        g.drawLine(0, getHeight() / 2, 7, getHeight() / 3);
        g.drawLine(0, getHeight() / 2, 7, getHeight() / 3 * 2);
        g.drawLine(getWidth(), getHeight() / 2, getWidth() - 7, getHeight() / 3);
        g.drawLine(getWidth(), getHeight() / 2, getWidth() - 7, getHeight() / 3 * 2);
        Rectangle2D rectangle2D = g.getFontMetrics().getStringBounds(this.data.get(this.indice), g);
        g.drawString(this.data.get(this.indice), getWidth() / 2 - (int) rectangle2D.getWidth() / 2, getHeight() / 2 + (int) rectangle2D.getHeight() / 2);
    }

    /**
     * Return the indice that correspond of the index of the array for the selected option
     *
     * @return the index of the array for the selected option
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Pass to the next choice in the button
     */
    public void next() {
        this.indice++;
        if (this.indice > this.data.size() - 1) this.indice = 0;
    }

    /**
     * Pass to the previous choice in the button
     */
    public void previous() {
        this.indice--;
        if (this.indice < 0) this.indice = this.data.size() - 1;
    }
}
