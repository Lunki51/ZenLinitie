package frame.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Represent a scrollable panel where you can select one object of the list
 *
 * @author Q.Cosnier
 */
public class SelectableScrollablePanel extends ScrollablePanel {

    int selected=0;

    /**
     * Create a new selectable scrollable panel
     * @param data the array of data to store in the panel
     * @param location the location of the panel in the parent panel
     * @param dimension the dimension of the panel
     * @param textSize the size of the text displayed in the panel
     */
    public SelectableScrollablePanel(ArrayList<String> data, Point location, Dimension dimension,int textSize) {
        super(data, location, dimension,textSize);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Rectangle2D rectangle2D = getGraphics().getFontMetrics().getStringBounds(SelectableScrollablePanel.this.data.get(0),getGraphics());
                selected= (int) ((SelectableScrollablePanel.this.data.size()*(e.getY()+scroll))/(maxScroll+(rectangle2D.getHeight()*SelectableScrollablePanel.this.data.size())));
                if(selected>SelectableScrollablePanel.this.data.size())selected=SelectableScrollablePanel.this.data.size()-1;
            }
        });
    }

    /**
     * Paint a scrollable panel where you can select an object
     * @param g the graphics used to paint the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Broadway", Font.PLAIN, this.textSize));
        if(data.size()!=0){
            Rectangle2D rectangle2D = g.getFontMetrics().getStringBounds(data.get(0),g);
            g.drawRect(0,0+(int)rectangle2D.getHeight()*this.selected-this.scroll,getWidth(), (int) rectangle2D.getHeight());
        }
    }

    /**
     * Return the current selected object as a index in the array of data
     * @return the index in the array of data
     */
    public int getSelected() {
        return selected;
    }

    /**
     * Update the array of data of the panel
     * @param value the new array of data to display
     */
    public void update(ArrayList<String> value){
        this.data=value;
    }
}
