package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Represent the board of a game and every action that can be done to the pawns on this board
 *
 * @author Q.Cosnier
 */
public class BoardManager implements Serializable {
    private Types[][] board;
    private int[] lastZenPos;

    /**
     * Create a new board
     */
    public BoardManager() {
        this.board = new Types[11][11];
        initializeBoard();
    }

    public BoardManager(Types[][] board, int[] lastZenPos) {
        this.board = board;
        this.lastZenPos = lastZenPos;
    }

    /**
     * Return the array of Types that represent the board. Each position of the array that contain null represent
     * an empty square
     *
     * @return the two dimensional array of Types
     */
    public Types[][] getBoard() {
        return board;
    }

    /**
     * Used to calculate the solutions that a pawn have at a precise position and with a certain type of pawn
     *
     * @param posX the x position where to search the solutions
     * @param posY the y position where to search the solutions
     * @return an two dimensional array of int that represent every solutions by is x and y component.
     */
    public ArrayList<int[]> getSolutions(int posX, int posY) {
        int detOne = posX - posY;
        int detTwo = getBoard().length - posX - posY - 1;
        int[] diagLeft = detOne >= 0 ? new int[]{0, detOne} : new int[]{-detOne, 0};
        int[] diagRight = detTwo >= 0 ? new int[]{10 - detTwo, 0} : new int[]{10, -detTwo};
        int numberSIDE = 0, numberUP = 0, numberLEFT = 0, numberRIGHT = 0;
        for (int i = 0; i < getBoard().length; i++) {

            if (getBoard()[i][posX] != null) numberUP++;
            if (getBoard()[posY][i] != null) numberSIDE++;
            if (diagLeft[0] < getBoard().length && diagLeft[1] < getBoard().length){
                if(getBoard()[diagLeft[0]][diagLeft[1]] != null) numberLEFT++;
            }
            if (diagRight[0] >= 0 && diagRight[1] < getBoard().length ){
                if(getBoard()[diagRight[0]][diagRight[1]] != null)numberRIGHT++;
            }
            diagLeft[0]++;
            diagLeft[1]++;
            diagRight[0]--;
            diagRight[1]++;
        }
        ArrayList<int[]> solutions = new ArrayList<>();
        if (posX - numberSIDE >= 0) solutions.add(new int[]{posY, posX - numberSIDE});
        if (posX + numberSIDE < getBoard().length) solutions.add(new int[]{posY, posX + numberSIDE});
        if (posY - numberUP >= 0) solutions.add(new int[]{posY - numberUP, posX});
        if (posY + numberUP < getBoard().length) solutions.add(new int[]{posY + numberUP, posX});
        if (posX - numberLEFT >= 0 && posY - numberLEFT >= 0)
            solutions.add(new int[]{posY - numberLEFT, posX - numberLEFT});
        if (posX + numberLEFT < getBoard().length && posY + numberLEFT < getBoard().length)
            solutions.add(new int[]{posY + numberLEFT, posX + numberLEFT});
        if (posX - numberRIGHT >= 0 && posY + numberRIGHT < getBoard().length)
            solutions.add(new int[]{posY + numberRIGHT, posX - numberRIGHT});
        if (posX + numberRIGHT < getBoard().length && posY - numberRIGHT >= 0)
            solutions.add(new int[]{posY - numberRIGHT, posX + numberRIGHT});


        Iterator<int[]> iterator = solutions.iterator();
        while (iterator.hasNext()) {
            int[] pos = iterator.next();
            if (pos[1] == posX && pos[0] == posY) {
                iterator.remove();
            } else if (getBoard()[posY][posX] == Types.ZEN) {
                if (pos[1] == getLastZenPos()[0] && pos[0] == getLastZenPos()[1]) {
                    iterator.remove();
                } else {
                    boolean validPos = false;
                    for (int i = pos[1] - 1; i <= pos[1] + 1; i++) {
                        for (int j = pos[0] - 1; j <= pos[0] + 1; j++) {
                            if ((i != 0 || j != 0) && i >= 0 && i < getBoard().length && j >= 0 && j < getBoard().length) {
                                if (getBoard()[i][j] != null)
                                    validPos = true;
                            }
                        }
                    }
                    if (!validPos) iterator.remove();
                }

            } else if (getBoard()[posY][posX] == getBoard()[pos[0]][pos[1]]) {
                iterator.remove();
            } else {
                int valX = pos[1] - posX ;
                int valY = pos[0] - posY ;
                boolean removed = false;
                while ((valX != 0 || valY != 0) && !removed) {
                    int valXSign = valX == 0 ? 0 : (valX / Math.abs(valX));
                    int valYSign = valY == 0 ? 0 : (valY / Math.abs(valY));
                    valX -= valXSign;
                    valY -= valYSign;
                    Types pawn = getBoard()[posY + valY][posX + valX];
                    if (pawn != getBoard()[posY][posX] && pawn != null) {
                        iterator.remove();
                        removed = true;
                    }

                }
            }
        }

        return solutions;
    }

    /**
     * Move a pawn at a specified location to another
     *
     * @param posX1 the x pos where to take the pawn to move
     * @param posY1 the y location where to take the pawn to move
     * @param posX2 the x location where to place the taken pawn
     * @param posY2 the y location where to place the taken pawn
     * @return true if moving the pawn destroyed another one, false if not.
     */
    public boolean movePawn(int posX1, int posY1, int posX2, int posY2) {
        boolean eaten = false;
        if (board[posX2][posY2] != null) eaten = true;
        board[posX2][posY2] = board[posX1][posY1];
        board[posX1][posY1] = null;
        return eaten;
    }

    /**
     * Check if the specified player have won
     *
     * @param player the player to test
     * @return true if the actual player have won,false if not
     */
    public boolean gameEnded(Types player) {
        ArrayList<int[]> toCheck = new ArrayList<>();
        ArrayList<int[]> checked = new ArrayList<>();
        boolean sortie = false;
        for (int i = 0; i < getBoard().length && !sortie; i++) {
            for (int j = 0; j < getBoard().length && !sortie; j++) {
                if (getBoard()[i][j] == player) {
                    toCheck.add(new int[]{i, j});
                    sortie = true;
                }
            }
        }
        ListIterator<int[]> iterator = toCheck.listIterator();
        boolean containZen = false;
        while (iterator.hasNext()) {
            int[] value = iterator.next();
            boolean validCheck = true;
            for(int[] data : checked){
                if(value[0]==data[0] && value[1]==data[1])validCheck=false;
            }
            if(validCheck){
                checked.add(new int[]{value[0],  value[1]});
                for (int i = value[0] - 1; i <= value[0] + 1; i++) {
                    for (int j = value[1] - 1; j <= value[1] + 1; j++) {
                        boolean valid = true;
                        for (int[] data : checked) {
                            if (data[0] == i && data[1] == j){
                                valid = false;
                            }
                        }
                        if(valid){
                            if (i >= 0 && j >= 0 && i < getBoard().length && j < getBoard().length && (getBoard()[i][j] == player || getBoard()[i][j]==Types.ZEN)) {
                                if(getBoard()[i][j]==Types.ZEN)containZen=true;
                                iterator.add(new int[]{i, j});
                                iterator.previous();
                            }
                        }
                    }
                }
            }
        }
        if (getMyPawns(player).size() == (checked.size()-(containZen?1:0))) return true;
        return false;
    }

    /**
     * Return the last position of the zen pawn
     *
     * @return a two dimensional int array that represent the last position of the zen pawn
     */
    public int[] getLastZenPos() {
        return lastZenPos;
    }

    /**
     * Set the last zen location
     *
     * @param posX the x position of the zen
     * @param posY the y position of the zen
     */
    public void setLastZenPos(int posX, int posY) {
        this.lastZenPos = new int[]{posX, posY};
    }

    /**
     * Initialize the board for the start of the game
     *
     */
    private void initializeBoard() {
        board[0][0] = Types.BLACK;
        board[10][10] = Types.BLACK;
        board[0][10] = Types.WHITE;
        board[10][0] = Types.WHITE;

        board[5][0] = Types.WHITE;
        board[0][5] = Types.BLACK;
        board[10][5] = Types.BLACK;
        board[5][10] = Types.WHITE;

        board[4][1] = Types.BLACK;
        board[6][1] = Types.BLACK;
        board[1][4] = Types.WHITE;
        board[1][6] = Types.WHITE;
        board[9][4] = Types.WHITE;
        board[9][6] = Types.WHITE;
        board[4][9] = Types.BLACK;
        board[6][9] = Types.BLACK;

        board[8][3] = Types.BLACK;
        board[8][7] = Types.BLACK;
        board[3][8] = Types.WHITE;
        board[7][8] = Types.WHITE;

        board[3][2] = Types.WHITE;
        board[7][2] = Types.WHITE;
        board[2][3] = Types.BLACK;
        board[2][7] = Types.BLACK;

        board[5][5] = Types.ZEN;
        lastZenPos = new int[]{5, 5};
    }

    /**
     * Return a list of all the pawns position for a given side
     *
     * @param side the side to search the pawn
     * @return an array of int that contains the location of every pawn
     */
    public ArrayList<int[]> getMyPawns(Types side) {
        ArrayList<int[]> myPawns = new ArrayList<>();
        for (int i = 0; i < this.getBoard().length; i++) {
            for (int j = 0; j < this.getBoard().length; j++) {
                if (this.getBoard()[i][j] == side) myPawns.add(new int[]{j, i});
            }
        }
        return myPawns;
    }

}
