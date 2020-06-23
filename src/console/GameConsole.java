package console;

import game.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represent an instance of the game that is launching on a console
 */
public class GameConsole extends Game {

    private int currentPage;
    private static final int MENUPAGE = 0;
    private static final int LAUNCHGAMEPAGE = 1;
    private static final int RULESPAGE = 2;
    private static final int SAVEPAGE = 3;
    private static final int ENDPAGE = 4;
    private static final int GAMEPAGE = 5;

    /**
     * Create a new game running with the console
     *
     */
    public GameConsole() {
        super();
        this.currentPage = MENUPAGE;
        try {
            this.saveManager = new SaveManager("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the game
     */
    @Override
    public void start() {
        System.out.println(getHeader());
    }

    /**
     * The method that is looping while the game is running
     */
    @Override
    public void loop() {
        switch (this.currentPage) {
            case MENUPAGE:
                System.out.println(getMenu());
                currentPage = intInput(1, 4);
                break;
            case LAUNCHGAMEPAGE:
                System.out.println(getGameMenu());
                int mode = intInput(1, 3);
                switch (mode) {
                    case 1:
                        this.playerWType = false;
                        this.playerBType = false;
                        break;
                    case 2:
                        this.playerWType = false;
                        this.playerBType = true;
                        break;
                    case 3:
                        this.playerWType = true;
                        this.playerBType = true;
                        break;
                }
                this.currentPage = GAMEPAGE;
                break;
            case RULESPAGE:
                System.out.println(getRules());
                intInput(1, 1);
                currentPage = GAMEPAGE;
                break;
            case SAVEPAGE:
                System.out.println(getSaveMenu(false));
                int choice = intInput(1, 3);
                if (choice == 3) {
                    currentPage = MENUPAGE;
                } else {
                    ArrayList<File> list = this.saveManager.getSavesList();
                    System.out.println("1 - Quitter");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(i + 2 + " -\t" + list.get(i).getName());
                    }
                    int saveChoice = intInput(1, list.size() + 1);
                    if (saveChoice != 1) {
                        switch (choice) {
                            case 1:
                                saveManager.loadSave(saveChoice - 2, this);
                                break;
                            case 2:
                                saveManager.removeSave(saveChoice - 2);
                                break;
                        }
                    }
                }
                break;
            case ENDPAGE:
                this.setToStop(true);
                break;
            case GAMEPAGE:
                currentPlayer = Math.random() > 0.5 ? Types.WHITE : Types.BLACK;
                this.manager = new BoardManager();
                boolean sortie = false;
                boolean egalite = false;
                do {
                    boolean currentType = currentPlayer == Types.WHITE ? playerWType : playerBType;
                    System.out.println("Joueur " + currentPlayer);
                    if (currentType) {
                        System.out.println(getBoard());
                        AutoPlayer.autoShot(this.manager, this.currentPlayer);
                        this.switchCurrent();
                    } else {
                        System.out.println(getBoard());

                        int[][] input = gameInput();
                        switch (input.length) {
                            case 0:
                                currentPage = MENUPAGE;
                                sortie = true;
                                break;
                            case 1:
                                System.out.println("Solutions possibles pour " + (char) (input[0][0] + 65) + ":" + input[0][1]);
                                ArrayList<int[]> solutions = this.manager.getSolutions(input[0][0], input[0][1]);

                                for (int[] value : solutions) {
                                    System.out.println("\t" + (char) (value[1] + 65) + ":" + value[0]);
                                }
                                sortie = false;
                                break;
                            case 2:
                                this.manager.movePawn(input[0][1], input[0][0], input[1][1], input[1][0]);
                                this.switchCurrent();
                                break;
                            case 3:
                                System.out.println(getSaveMenu(true));
                                int choice2 = intInput(1, 4);
                                if (choice2 == 4) {
                                    currentPage = MENUPAGE;
                                } else {
                                    if (choice2 != 2) {
                                        ArrayList<File> list = this.saveManager.getSavesList();
                                        for (int i = 0; i < list.size(); i++) {
                                            System.out.println(i + 2 + " -\t" + list.get(i).getName());
                                        }
                                        int saveChoice = intInput(1, list.size() + 1);
                                        if (saveChoice != 1) {
                                            switch (choice2) {
                                                case 1:
                                                    saveManager.loadSave(saveChoice - 2, this);
                                                    break;
                                                case 3:
                                                    saveManager.removeSave(saveChoice - 2);
                                                    break;
                                            }
                                        }
                                    } else {
                                        saveManager.createNewSave(this);
                                    }
                                }
                        }
                    }
                    if (this.manager.gameEnded(currentPlayer)) egalite = true;
                    if (this.manager.gameEnded(currentPlayer) && this.manager.gameEnded(currentPlayer == Types.WHITE ? Types.BLACK : Types.WHITE))egalite = true;
                } while (!this.manager.gameEnded(currentPlayer == Types.WHITE ? Types.BLACK : Types.WHITE) && !sortie && !egalite);
                System.out.println(getBoard());
                if (egalite) {
                    System.out.println("EGALITEE");
                } else {
                    Types opposite = currentPlayer == Types.WHITE ? Types.BLACK : Types.WHITE;
                    System.out.println("PARTIE TERMINEE GAGNANT :" + opposite);
                }

                currentPage = MENUPAGE;
        }
    }

    /**
     * Stop the game
     */
    @Override
    public void stop() {

    }

    /**
     * Return the header of the game to be displayed.
     *
     * @return the header of the game as a String
     */
    private String getHeader() {
        return "                                                                                                                     \n" +
                "=====================================================================================================================\n" +
                "=====================================================================================================================\n" +
                "=====================================================================================================================\n" +
                "                       _____                         __   _    _             _    __     _     __                    \n" +
                "                      /__  /     ___     ____       / /  ( )  (_)  ____     (_)  / /_   (_)  _/_/                    \n" +
                "                        / /     / _ \\   / __ \\     / /   |/  / /  / __ \\   / /  / __/  / /  / _ \\                    \n" +
                "                       / /__   /  __/  / / / /    / /       / /  / / / /  / /  / /_   / /  /  __/                    \n" +
                "                      /______  \\___/  /_/ /_/    /_/       /_/  /_/ /_/  /_/   \\__/  /_/   \\___/                     \n" +
                "                                                                                                                     \n" +
                "=====================================================================================================================\n" +
                "=====================================================================================================================\n" +
                "=====================================================================================================================\n";
    }

    /**
     * Return the menu of the game to be displayed.
     *
     * @return the menu of the game as a string
     */
    private String getMenu() {
        return "Choissisez : \n" +
                "1 - Lancer une partie\n" +
                "2 - Règles du jeux\n" +
                "3 - Charger\n" +
                "4 - Quitter\n";
    }

    /**
     * Return the save menu of the game to be displayed.
     *
     * @return the save menu as a String
     * @param save if the save menu allow to save or not
     */
    private String getSaveMenu(boolean save) {
        String str = "Choissisez : \n" +
                "1 - Charger une sauvegarde\n";
        if (save) {
            str += "2 - Sauvegarder\n" +
                    "3 - Effacer une sauvegarde\n" +
                    "4 - Quitter\n";
        } else {
            str += "2 - Effacer une sauvegarde\n" +
                    "3 - Quitter\n";

        }
        return str;
    }

    /**
     * Return the menu to launch a game to be displayed
     *
     * @return the menu to launch a game as a String
     */
    private String getGameMenu() {
        return "Choissisez : \n" +
                "1 - Joueur vs Joueurs\n" +
                "2 - Joueur vs Ordinateur\n" +
                "3 - Ordinateur vs Ordinateur\n";
    }

    @Override
    public String getRules() {
        return super.getRules() + "\n 1 - Retour";
    }

    /**
     * Return the board of the game to be displayed
     *
     * @return the board of the game as a String
     */
    private String getBoard() {
        String out = "                                |/ A / B / C / D / E / F / G / H / I / J / K /|\n";
        for (Types[] types : this.manager.getBoard()) {
            String line = "                                |/";
            for (Types type : types) {
                if (type != null) {
                    line += " " + type.getSymbol() + " /";
                } else {
                    line += " " + "-" + " /";
                }
            }
            line += "|\n";
            out += line;
        }
        return out;
    }

    /**
     * Ask an input to the player between the two specified values
     *
     * @return the int specified by the player.
     */
    private int[][] gameInput() {
        Scanner sc = new Scanner(System.in);
        String input = "";
        int[][] value = new int[0][0];
        boolean leave = false;
        do {
            System.out.println("Entrez un mouvement avec la forme 'A0:A0' ou demander les solutions " +
                    "d'un coup avec 'solution A0' ou sauvegardez avec 'sauvegarder' ou quittez avec " +
                    "'quitter'");
            input = sc.nextLine();
            if (input.matches("^[A-K]([0-9]|10):[A-K]([0-9]|10)$")) {
                String[] tab = input.split(":");
                int selectX = tab[0].charAt(0) - 65;
                int selectY = Integer.valueOf(tab[0].substring(1));
                int placeX = tab[1].charAt(0) - 65;
                int placeY = Integer.valueOf(tab[1].substring(1));

                ArrayList<int[]> solutions = this.manager.getSolutions(selectX, selectY);
                boolean validPos = false;
                for (int[] data : solutions) {
                    if (data[1] == placeX && data[0] == placeY) validPos = true;
                }
                Types select = this.manager.getBoard()[selectY][selectX];
                if (select != currentPlayer) validPos = false;

                if (validPos) {
                    if (select == Types.ZEN) {
                        this.manager.setLastZenPos(placeX, placeY);
                    }
                    value = new int[][]{{selectX, selectY}, {placeX, placeY}};
                    leave = true;
                } else {
                    System.out.println("Entrée incorrecte");
                    leave = false;
                }
            } else if (input.toUpperCase().equals("QUITTER")) {
                value = new int[0][0];
                leave = true;
            } else if (input.toUpperCase().equals("SAUVEGARDER")) {
                value = new int[3][0];
                leave = true;
            } else if (input.split(" ")[0].equals("solution") && input.split(" ").length == 2 && this.manager.getBoard()[Integer.valueOf(String.valueOf(input.split(" ")[1].substring(1)))][input.split(" ")[1].charAt(0) - 65] != null) {
                String[] tab = input.split(" ");
                value = new int[][]{{((tab[1].charAt(0)) - 65), Integer.valueOf(tab[1].substring(1))}};
                leave = true;
            } else {
                System.out.println("Entrée incorrecte");
                leave = false;
            }
        } while (!leave);
        return value;
    }

    /**
     * Ask an input to the player between the two specified values
     *
     * @param min the minimum value to ask
     * @param max the maximum value to ask
     * @return the int specified by the player.
     */
    private int intInput(int min, int max) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        int value = 0;
        boolean leave = false;
        do {
            System.out.println("Entrez un nombre entre " + min + " et " + max);
            input = sc.next();
            if (input.toUpperCase().equals("SAUVEGARDER")) {
                this.currentPage = SAVEPAGE;
                leave = true;
            }
            if (input.toUpperCase().equals("QUITTER")) {
                this.currentPage = ENDPAGE;
                leave = true;
            } else {
                value = Integer.valueOf(input);
            }
        } while (!leave && (value < min || value > max));
        return value;
    }

}
