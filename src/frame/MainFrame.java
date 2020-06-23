package frame;

import frame.panels.CommonPanel;
import frame.panels.MenuPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The main frame of the application when running on a frame
 *
 * @author Q.Cosnier
 */
public class MainFrame extends JFrame {

    /**
     * Create a new frame for the application
     * @param title the title for the frame
     */
    public MainFrame(String title) {
        super(title);
        this.setSize(new Dimension(800, 600));
        this.setUndecorated(true);
        this.setContentPane(new MenuPanel());
        this.setVisible(true);
    }

    /**
     * Change the current panel of the frame
     * @param panel the new panel to display
     */
    public void changePanel(JPanel panel) {
        this.setContentPane(panel);
        this.repaint();
    }

    /**
     * Update the frame and his pane
     * @param game the game used to update the frame
     */
    public void update(GameFrame game) {
        ((CommonPanel) this.getContentPane()).update(game);
    }

}
