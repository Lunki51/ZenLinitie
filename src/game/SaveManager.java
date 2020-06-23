package game;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class that represent a file that store saves
 *
 * @author Q.Cosnier
 */
public class SaveManager {
    private File savefile;

    /**
     * Create a new save manager
     *
     * @param savefile the path where to store the saves
     * @throws IOException if there is an error with reading the specified file
     */
    public SaveManager(String savefile) throws IOException {
        this.savefile = new File(savefile);
        this.savefile.mkdir();
    }

    /**
     * Return a string that represent all the saves that this file contain
     *
     * @return the string that contain the list of files
     */
    public ArrayList<File> getSavesList() {
        ArrayList<File> savesList = new ArrayList<>();
        for (File f : this.savefile.listFiles()) {
            if (f.isFile() && f.canRead() && f.exists()) savesList.add(f);
        }
        return savesList;
    }

    /**
     * Remove a save from the file
     *
     * @param slot the save slot where to remove the save
     */
    public void removeSave(int slot) {
        ArrayList<File> savesList = getSavesList();
        savesList.get(slot).delete();
    }

    /**
     * Create a new save file
     *
     * @param game the instance of game to save
     */
    public void createNewSave(Game game) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        File file = new File(this.savefile.getPath() + "/" + format.format(new Date()));
        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            DataOutputStream writer = new DataOutputStream(fileStream);
            writer.writeInt(game.getCurrentPlayer() == Types.BLACK ? 0 : 1);
            writer.writeInt(game.playerBType ? 1 : 0);
            writer.writeInt(game.playerWType ? 1 : 0);
            for (Types[] t : game.getManager().getBoard()) {
                for (Types t1 : t) {
                    if (t1 == Types.BLACK) {
                        writer.writeInt(-1);
                    } else if (t1 == Types.WHITE) {
                        writer.writeInt(1);
                    } else {
                        writer.writeInt(0);
                    }
                }
            }
            writer.writeInt(game.getManager().getLastZenPos()[0]);
            writer.writeInt(game.getManager().getLastZenPos()[1]);
            writer.close();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new save on an existent file
     *
     * @param game the instance of game to save
     * @param slot the slot where to save the data
     */
    public void overrideSave(Game game, int slot) {
        ArrayList<File> saves = getSavesList();
        File file = saves.get(slot);
        try {
            file.delete();
            file.createNewFile();
            FileOutputStream fileStream = new FileOutputStream(file);
            DataOutputStream writer = new DataOutputStream(fileStream);
            writer.writeInt(game.getCurrentPlayer() == Types.BLACK ? 0 : 1);
            writer.writeInt(game.playerBType ? 1 : 0);
            writer.writeInt(game.playerWType ? 1 : 0);
            for (Types[] t : game.getManager().getBoard()) {
                for (Types t1 : t) {
                    if (t1 == Types.BLACK) {
                        writer.writeInt(-1);
                    } else if (t1 == Types.WHITE) {
                        writer.writeInt(1);
                    } else {
                        writer.writeInt(0);
                    }
                }
            }
            writer.writeInt(game.getManager().getLastZenPos()[0]);
            writer.writeInt(game.getManager().getLastZenPos()[1]);
            writer.close();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a save at a specified slot
     *
     * @param slot the save slot where to load the save
     * @param to the game where to load the save
     */
    public void loadSave(int slot, Game to) {
        ArrayList<File> saves = getSavesList();
        File file = saves.get(slot);
        try {
            FileInputStream fileStream = new FileInputStream(file);
            DataInputStream reader = new DataInputStream(fileStream);
            Types currentPlayer = reader.readInt() == 0 ? Types.BLACK : Types.WHITE;
            boolean playerBType = reader.readInt() == 1 ? true : false;
            boolean playerWType = reader.readInt() == 1 ? true : false;
            Types[][] board = new Types[11][11];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    Types t;
                    int read = reader.readInt();
                    if (read == 0) {
                        t = null;
                    } else if (read == 1) {
                        t = Types.WHITE;
                    } else {
                        t = Types.BLACK;
                    }
                    board[i][j] = t;
                }
            }
            int[] lastZenPos = new int[]{reader.readInt(), reader.readInt()};

            to.setCurrentPlayer(currentPlayer);
            to.manager = new BoardManager(board, lastZenPos);
            to.playerBType = playerBType;
            to.playerWType = playerWType;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
