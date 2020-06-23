package frame.panels;

import frame.GameFrame;
import frame.components.GameButton;
import frame.components.SliderButton;
import frame.utils.GameColors;
import game.Game;
import game.Types;

import java.awt.*;
import java.util.ArrayList;

import static frame.utils.Images.LOGOIMAGE;

/**
 * A panel that represent what is showed when a game is launched
 *
 * @author Q.Cosnier
 */
public class LaunchPanel extends CommonPanel {

    private SliderButton choiceW;
    private SliderButton choiceB;

    /**
     * Create a new panel for launching a game
     */
    public LaunchPanel() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Joueur");
        choices.add("Ordinateur");
        this.choiceW = new SliderButton(new Point(100, 350), new Dimension(200, 60), choices);
        this.choiceB = new SliderButton(new Point(500, 350), new Dimension(200, 60), choices);

        this.setLayout(null);
        setBackground(GameColors.FONTCOLOR);
        setSize(new Dimension(800, 600));
        add(new GameButton(new Point(50, getHeight() - 100), new Dimension(180, 60), "Retour", () -> GameFrame.instance.changePanel(new MenuPanel())));
        add(new GameButton(new Point(getWidth() - 50 - 180, getHeight() - 100), new Dimension(180, 60), "Lancer", () -> {
            GameFrame.instance.setPlayersTypes(choiceW.getIndice() == 0 ? false : true, choiceB.getIndice() == 0 ? false : true);
            GameFrame.instance.changePanel(new GamePanel());
            GameFrame.instance.reset();
            GameFrame.instance.setCurrentPlayer(Math.random() > 0.5 ? Types.WHITE : Types.BLACK);
        }));

        add(choiceW);
        add(choiceB);
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
        g.fillOval(getWidth() / 2 - 150, 75, 300, 300);
        g.drawImage(LOGOIMAGE, getWidth() / 2 - 150, 75, 295, 295, this);
    }

    /**
     * Update the panel every game tick
     *
     * @param game the game used to update the panel
     */
    @Override
    public void update(Game game) {
        repaint();
    }
}
