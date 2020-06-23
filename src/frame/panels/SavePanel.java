package frame.panels;

import frame.GameFrame;
import frame.components.GameButton;
import frame.components.SelectableScrollablePanel;
import frame.utils.GameColors;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static frame.utils.Images.LOGOIMAGE;

/**
 * Represent a panel used to represent a save menu for the application
 *
 * @author Q.Cosnier
 */
public class SavePanel extends CommonPanel {

    /**
     * Create a new SavePanel to display the saves and manage them
     *
     * @param latest the latest panel to return to when closing this panel
     * @param save   if true the panel can create new saves
     */
    public SavePanel(JPanel latest, boolean save) {
        setLayout(null);
        this.setSize(new Dimension(800, 600));
        ArrayList<String> saves = new ArrayList<>();
        for (File f : GameFrame.instance.getSaveManager().getSavesList()) {
            saves.add(f.getName());
        }
        SelectableScrollablePanel savesPanel = new SelectableScrollablePanel(saves, new Point(5, 5), new Dimension(getWidth() / 2, getHeight() - 10), 25);
        add(savesPanel);
        if (save)
            add(new GameButton(new Point((getWidth() / 3) * 2, 200), new Dimension(180, 60), "Sauvegarder", () -> {
                GameFrame.instance.getSaveManager().createNewSave(GameFrame.instance);
                ArrayList<String> savelist = new ArrayList<>();
                for (File f : GameFrame.instance.getSaveManager().getSavesList()) {
                    savelist.add(f.getName());
                }
                savesPanel.update(savelist);
            }));
        add(new GameButton(new Point((getWidth() / 3) * 2, 300), new Dimension(180, 60), "Charger", () -> {
            GameFrame.instance.getSaveManager().loadSave(savesPanel.getSelected(), GameFrame.instance);
            GameFrame.instance.changePanel(new GamePanel());
        }));
        add(new GameButton(new Point((getWidth() / 3) * 2, 400), new Dimension(180, 60), "Effacer", () -> {
            GameFrame.instance.getSaveManager().removeSave(savesPanel.getSelected());
            ArrayList<String> savelist = new ArrayList<>();
            for (File f : GameFrame.instance.getSaveManager().getSavesList()) {
                savelist.add(f.getName());
            }
            savesPanel.update(savelist);
        }));
        add(new GameButton(new Point((getWidth() / 3) * 2, 500), new Dimension(180, 60), "Retour", () -> GameFrame.instance.changePanel(latest)));

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
        g.setColor(GameColors.DARKFONT);
        g.fillOval((getWidth() / 3) * 2 + 15, 25, 150, 150);
        g.drawImage(LOGOIMAGE, (getWidth() / 3) * 2 + 15, 25, 150, 150, this);
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
