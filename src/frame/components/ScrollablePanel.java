package frame.components;

import frame.utils.GameColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Represent a panel of data that can be scroll if the array of data is too long
 *
 * @author Q.Cosnier
 */
public class ScrollablePanel extends JComponent {

    protected int scroll;
    protected int maxScroll;
    protected int textSize;
    protected ArrayList<String> data;

    /**
     * Create a new component that represent a scrollable panel
     *
     * @param data      the array of data to display in the panel
     * @param location  the location of the panel on the parent panel
     * @param dimension the dimension of the panel
     * @param textSize the size of the text to display
     */
    public ScrollablePanel(ArrayList<String> data, Point location, Dimension dimension,int textSize) {
        this.setLocation(location);
        this.setSize(dimension);
        this.data = data;
        this.scroll = 0;
        this.textSize=textSize;
        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scroll += e.getWheelRotation() * 10;
                if (scroll < 0) scroll = 0;
                if (scroll > maxScroll) scroll = maxScroll;
            }
        });


    }


    /**
     * Paint the scrollable panel
     *
     * @param g the graphics used to paint the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("Broadway", Font.PLAIN, this.textSize));
        g.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g.setColor(GameColors.DARKFONT);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g.setColor(GameColors.BUTTONCOLOR);
        g.drawRoundRect(getWidth() - 20, 10, 10, getHeight() - 20, 10, 10);
        g.setColor(GameColors.FONTCOLOR);
        if (data.size() != 0) {
            Rectangle2D rectangle2D = g.getFontMetrics().getStringBounds(data.get(0), g);
            this.maxScroll = (int) rectangle2D.getHeight() * data.size() - getHeight();
            if (maxScroll < 1) maxScroll = 1;
            g.drawRoundRect(getWidth() - 20, (int) (10 + ((this.scroll * ((20 + rectangle2D.getHeight() * this.data.size() / (getHeight() - 20) * 10)) / maxScroll))), 10, (int) (getHeight() - 20 - (20 + rectangle2D.getHeight() * this.data.size() / (getHeight() - 20) * 10)), 10, 10);
            for (int i = 0; i < this.data.size(); i++) {
                g.drawString(data.get(i), 0, (int) (20 + (rectangle2D.getHeight() * i - this.scroll)));
            }
        }
    }
}
