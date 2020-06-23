package test;

import game.BoardManager;
import game.Types;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Used to test the BoardManager class
 */
public class BoardManagerTest {

    BoardManager manager;

    /**
     * Setup the environment for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.manager = new BoardManager();
    }

    /**
     * Clear the environment at the end of the thest
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        this.manager = null;
    }

    /**
     * Test the constructor of the class
     */
    @Test
    public void testBoardManager() {
        assertNotNull(this.manager);
    }

    /**
     * Test if the manager of the board is not null
     */
    @Test
    public void testGetBoard() {
        Types[][] board = this.manager.getBoard();
        assertNotNull(board);
    }

    /**
     * Test if the getSolutions method of the class return an not null object and if the array size is 2 by X
     * X being the number of solutions
     */
    @Test
    public void testGetSolutions() {
        ArrayList<int[]> solutions = this.manager.getSolutions(0, 0);
        assertNotNull(solutions);
        assertEquals(solutions.size(), 3);
    }

    /**
     * Test if the movePawn method work well.
     */
    @Test
    public void testMovePawn() {
        this.manager.movePawn(0, 0, 1, 0);
        assertNull(this.manager.getBoard()[0][0]);
        assertNotNull(this.manager.getBoard()[1][0]);
    }

    /**
     * Test if the gameEnded method return true only in the good conditions.
     */
    @Test
    public void testGameEnded() {
        assertFalse(this.manager.gameEnded(Types.BLACK));
        this.manager.movePawn(10, 10, 1, 0);
        this.manager.movePawn(10, 5, 0, 1);
        this.manager.movePawn(0, 5, 1, 1);
        this.manager.movePawn(2, 3, 2, 0);
        this.manager.movePawn(2, 7, 0, 2);
        this.manager.movePawn(4, 1, 2, 2);
        this.manager.movePawn(4, 9, 3, 0);
        this.manager.movePawn(8, 3, 0, 3);
        this.manager.movePawn(8, 7, 3, 3);
        this.manager.movePawn(6, 1, 2, 1);
        this.manager.movePawn(6, 9, 1, 2);
        assertTrue(this.manager.gameEnded(Types.BLACK));
    }
}