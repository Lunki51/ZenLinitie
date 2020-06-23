package game;

import java.util.ArrayList;

/**
 * Used to represent a player controlled by the computer
 *
 * @author Q.Cosnier
 */
public class AutoPlayer {

    /**
     * Make the auto player play a shot
     * @param manager the board manager where the auto player will play
     * @param side the side of the autoplayer
     */
    public static void autoShot(BoardManager manager, Types side) {
        ArrayList<int[]> myPawns = manager.getMyPawns(side);
        int[] selectedPawn;
        int[] choosenMove;
        if (myPawns.size() != 0) {
            boolean validPos;
            do {
                validPos = true;
                ArrayList<int[]> solution;
                do{
                    selectedPawn = myPawns.get((int) (Math.random() * myPawns.size()));
                    solution = manager.getSolutions(selectedPawn[0], selectedPawn[1]);
                }while(solution.size()==0);

                choosenMove = solution.get((int) (Math.random() * solution.size()));
                int selectX = selectedPawn[0];
                int selectY = selectedPawn[1];
                Types select = manager.getBoard()[selectY][selectX];
                if (select != side) validPos = false;
            } while (!validPos);
            manager.movePawn(selectedPawn[1], selectedPawn[0], choosenMove[0], choosenMove[1]);
            manager.setLastZenPos(choosenMove[0], choosenMove[1]);
        }

    }

}
