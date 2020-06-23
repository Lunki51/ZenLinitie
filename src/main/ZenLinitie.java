package main;

import console.GameConsole;
import frame.GameFrame;
import game.BoardManager;
import game.Game;

/**
 * Represent the main class of the game where the game will be controlled
 */
public class ZenLinitie {

    private Game game;
    public static ZenLinitie instance;

    public ZenLinitie(Game game) {
        this.game=game;
        this.game.start();
        while(!this.game.hasToStop()){
            this.game.loop();
        }
        this.game.stop();
    }

    public Game getGame() {
        return game;
    }

    /**
     * Start a new game by looking at the arguments for the configuration of the game
     *
     * @param args the -console argument will launch the game with the console option
     */
    public static void main(String[] args) {
        String arg;
        if(args.length>0){
            arg = args[0];
        }else{
            arg = "";
        }

        instance = new ZenLinitie(arg.equals("-c")?new GameConsole():new GameFrame());
    }

}
