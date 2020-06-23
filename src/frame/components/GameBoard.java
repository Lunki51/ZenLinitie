package frame.components;

import frame.GameFrame;
import frame.panels.WinPanel;
import game.Types;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static frame.utils.Images.*;

/**
 * Represent a the board of the game as a component
 *
 * @author Q.Cosnier
 */
public class GameBoard extends JComponent {

    private GameFrame game;
    private int[] selected;

    /**
     * Create a new board component to be displayed
     *
     * @param location  the position of the component on the frame
     * @param dimension the dimension of the component
     * @param game      the game that use this board
     */
    public GameBoard(Point location, Dimension dimension, GameFrame game) {
        this.setLocation(location);
        this.setSize(dimension);
        this.game = game;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(GameBoard.this.game.getCurrentPlayer() == Types.BLACK ? GameBoard.this.game.isPlayerBType() : GameBoard.this.game.isPlayerWType())) {
                    int mouseX = e.getX() / ((getWidth() / 11) - 1);
                    int mouseY = e.getY() / ((getHeight() / 11) - 1);
                    if (selected == null) {
                        if (GameBoard.this.game.getManager().getBoard()[mouseY][mouseX] == GameBoard.this.game.getCurrentPlayer() || GameBoard.this.game.getManager().getBoard()[mouseY][mouseX] == Types.ZEN)
                            selected = new int[]{mouseY, mouseX};
                    } else {
                        ArrayList<int[]> solution = GameBoard.this.game.getManager().getSolutions(selected[1], selected[0]);
                        boolean valid = false;
                        for (int[] value : solution) {
                            if (value[0] == mouseY && value[1] == mouseX) valid = true;
                        }
                        Types select = GameBoard.this.game.getManager().getBoard()[mouseY][mouseX];

                        if (valid) {
                            if (select == Types.ZEN) {
                                GameBoard.this.game.getManager().setLastZenPos(mouseX, mouseY);
                            }
                            GameBoard.this.game.getManager().movePawn(selected[0], selected[1], mouseY, mouseX);
                            GameBoard.this.game.switchCurrent();
                            boolean egalite = false;
                            Types opposite = GameBoard.this.game.getCurrentPlayer() == Types.WHITE ? Types.BLACK : Types.WHITE;
                            if (GameBoard.this.game.getManager().gameEnded(GameBoard.this.game.getCurrentPlayer())) egalite = true;
                            if (GameBoard.this.game.getManager().gameEnded(GameBoard.this.game.getCurrentPlayer()) && GameBoard.this.game.getManager().gameEnded(opposite))egalite = true;
                            if(egalite){
                                GameFrame.instance.changePanel(new WinPanel(Types.ZEN));
                            }else if(GameBoard.this.game.getManager().gameEnded(opposite)){
                                GameFrame.instance.changePanel(new WinPanel(opposite));
                            }

                            selected = null;
                        } else {
                            selected = null;
                        }
                    }
                }
            }
        });
    }

    /**
     * Paint a game board with all the pawn on it
     *
     * @param g the graphics used to draw the components
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(BOARD, 0, 0, getWidth(), getHeight(), this);
        for (int i = 0; i < this.game.getManager().getBoard().length; i++) {
            for (int j = 0; j < this.game.getManager().getBoard()[0].length; j++) {
                Types pawn = this.game.getManager().getBoard()[j][i];
                if (pawn != null) {
                    switch (pawn) {
                        case WHITE:
                            g.drawImage(WHITEPAWN, getWidth() / 11 * i + 1, getHeight() / 11 * j + 1, getWidth() / 11 - 1, getHeight() / 11 - 1, this);
                            break;
                        case BLACK:
                            g.drawImage(BLACKPAWN, getWidth() / 11 * i + 1, getHeight() / 11 * j + 1, getWidth() / 11 - 1, getHeight() / 11 - 1, this);
                            break;
                        case ZEN:
                            g.drawImage(ZENPAWN, getWidth() / 11 * i + 1, getHeight() / 11 * j + 1, getWidth() / 11 - 1, getHeight() / 11 - 1, this);
                            break;
                    }
                }
                if (selected != null) {
                    ArrayList<int[]> solution = this.game.getManager().getSolutions(selected[1], selected[0]);
                    for (int[] value : solution) {
                        if (value[1] == j && value[0] == i) {
                            g.setColor(Color.GREEN);
                            g.drawRect(getWidth() / 11 * j + 1, getHeight() / 11 * i + 1, 50, 50);
                        }
                    }
                }
            }
        }
    }
}
