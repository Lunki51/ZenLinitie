package game;

import java.util.Objects;

/**
 * Abstract class that represent an instance of a game on different support.
 *
 * @author Q.Cosnier
 */
public abstract class Game {
    protected boolean toStop;
    protected boolean playerWType;
    protected boolean playerBType;
    protected BoardManager manager;
    protected Types currentPlayer;
    protected SaveManager saveManager;
    protected AutoPlayer autoPlayer;

    /**
     * Create a new Game
     *
     */
    public Game() {
        this.manager = new BoardManager();
        try {
            this.saveManager = new SaveManager("./data");
        } catch (Exception e) {
        }

    }

    /**
     * Return the rules of the game ad a String
     *
     * @return the String that contain the rules of the game
     */
    public String getRules() {
        return "\tRègles Zen l'initié :\n" +
                "Lorsqu'une partie commence, le premier joueur a jouer est tiré au sort\n" +
                "Le vainqueur de la partie est celui qui arriveras a former une chaîne continue avec\n" +
                "la totalitée de ses pions qui se trouvent encore sur le plateau, y compris le pion Zen\n" +
                "si il est toujours en jeux.\n" +
                "A chaque tour, le joueur déplace un pion de sa couleur ou le pion Zen\n" +
                "Les pions peuvent se déplacer dans n'importe qu'elle direction mais ils doivent toujours se \n" +
                "déplacer d'autans de cases qu'il y a de pions sur la ligne choisie.\n" +
                "Lorsqu'ils se déplacent, les pions peuvent passer au dessus des pions de leurs couleurs\n" +
                "ou Zen mais jamais au dessus des pions de couleurs adverse.\n" +
                "Les pions peuvent êtres capturés par l'adversaire en placant un pion sur la case a capturer\n" +
                "Ce pion est alors définitivement retiré du jeux.\n" +
                "Le Zen est considéré comme étant de la couleurs choisie par le joueurs qui joue,\n" +
                "il peut donc être noir ou blanc à l'envie du joueur.\n" +
                "Les règles particulières du Zen sont qu'il ne peut pas être replacé sur la case qu'il \n" +
                "venais de quitter et qu'il est interdit de le déplacer si il ne se retrouve pas \n" +
                "adjacent à un autre pion.\n" +
                "Un match nul peut avoir lieu si un joueur fait gagner les deux joueurs en déplacant le Zen \n" +
                "ou si un joueur capture le dernier pion isolé de son adversaire.\n";
    }

    /**
     * Switch the current player to be the other one
     */
    public void switchCurrent() {
        if (currentPlayer == Types.WHITE) {
            currentPlayer = Types.BLACK;
        } else {
            currentPlayer = Types.WHITE;
        }
    }

    /**
     * Check if the game has to stop
     *
     * @return if the game has to stop
     */
    public boolean hasToStop() {
        return toStop;
    }

    /**
     * Abstract method used to represent the beginning of a game
     */
    public abstract void start();

    /**
     * Abstract method used to represent the looping method of the game
     */
    public abstract void loop();

    /**
     * Abstract method to represent the end of a game
     */
    public abstract void stop();

    /**
     * Return the currentPlayer
     *
     * @return the current player
     */
    public Types getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set if the application as to stop or not
     *
     * @param toStop the new value of toStop
     */
    public void setToStop(boolean toStop) {
        this.toStop = toStop;
    }

    /**
     * Change the current player of the game
     *
     * @param currentPlayer the new current player
     */
    public void setCurrentPlayer(Types currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Return if the white player is a bot or not
     *
     * @return true if white is a bot false if not
     */
    public boolean isPlayerWType() {
        return playerWType;
    }

    /**
     * Return if the black player is a bot or not
     *
     * @return true if black is a bot false if not
     */
    public boolean isPlayerBType() {
        return playerBType;
    }

    /**
     * Return the board manager of this game
     *
     * @return the board manager
     */
    public BoardManager getManager() {
        return this.manager;
    }

    /**
     * Return the save manager of this game
     *
     * @return the save manager
     */
    public SaveManager getSaveManager() {
        return saveManager;
    }

    /**
     * Test if this Game is equals to another object
     *
     * @param o the object to test
     * @return true if the game and the object are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return toStop == game.toStop &&
                playerWType == game.playerWType &&
                playerBType == game.playerBType &&
                Objects.equals(manager, game.manager) &&
                currentPlayer == game.currentPlayer &&
                Objects.equals(saveManager, game.saveManager) &&
                Objects.equals(autoPlayer, game.autoPlayer);
    }

}
