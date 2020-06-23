package frame.panels;

import frame.GameFrame;
import frame.components.GameBoard;
import frame.components.GameButton;
import frame.utils.GameColors;
import game.AutoPlayer;
import game.Game;
import game.Types;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static frame.utils.Images.LOGOIMAGE;

/**
 * The panel used to represent the a game
 *
 * @author Q.Cosnier
 */
public class GamePanel extends CommonPanel {

    /**
     * Create a new panel for a game
     */
    public GamePanel() {
        setLayout(null);
        setSize(800, 600);
        add(new GameBoard(new Point(25, 25), new Dimension(550, 550), GameFrame.instance));
        add(new GameButton(new Point(getWidth() - 150 - 40, 300), new Dimension(180, 60), "Sauvegarder", () -> GameFrame.instance.changePanel(new SavePanel(GamePanel.this, true))));
        add(new GameButton(new Point(getWidth() - 150 - 40, 400), new Dimension(180, 60), "Règles", () -> GameFrame.instance.changePanel(new RulesPanel(GamePanel.this))));
        add(new GameButton(new Point(getWidth() - 150 - 40, 500), new Dimension(180, 60), "Quitter", () -> GameFrame.instance.changePanel(new MenuPanel())));
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
        g.fillOval(getWidth() - 150 - 40, 50, 150, 150);
        g.drawImage(LOGOIMAGE, getWidth() - 150 - 40, 50, 145, 145, this);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Broadway", Font.PLAIN, 20));
        Rectangle2D rectangle2D = g.getFontMetrics().getStringBounds("Tour de " + GameFrame.instance.getCurrentPlayer(), g);
        g.drawString("Tour de " + GameFrame.instance.getCurrentPlayer(), (int) (getWidth() - 20 - (rectangle2D.getWidth())), 250);
    }

    /**
     * Update the game panel
     *
     * @param game the game used to update the panel
     */
    @Override
    public void update(Game game) {
        if (game.getCurrentPlayer() == Types.BLACK ? game.isPlayerBType() : game.isPlayerWType()) {
            AutoPlayer.autoShot(game.getManager(), game.getCurrentPlayer());
            game.switchCurrent();
            boolean egalite = false;
            if (game.getManager().gameEnded(game.getCurrentPlayer())) egalite = true;
            if (game.getManager().gameEnded(game.getCurrentPlayer()) && game.getManager().gameEnded(game.getCurrentPlayer() == Types.WHITE ? Types.BLACK : Types.WHITE))egalite = true;
            if(egalite){
                GameFrame.instance.changePanel(new WinPanel(Types.ZEN));
            }else if(game.getManager().gameEnded(game.getCurrentPlayer() == Types.WHITE ? Types.BLACK : Types.WHITE)){
                Types opposite = game.getCurrentPlayer() == Types.WHITE ? Types.BLACK : Types.WHITE;
                GameFrame.instance.changePanel(new WinPanel(opposite));
            }
        }
        repaint();
    }
}
