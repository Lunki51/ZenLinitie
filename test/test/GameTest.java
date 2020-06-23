package test;

import console.GameConsole;
import game.BoardManager;
import game.Game;
import game.Types;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Used to test the Game class
 */
public class GameTest {

    Game game;

    /**
     * Setup the environment for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.game = new Game() {
            @Override
            public void start() {

            }

            @Override
            public void loop() {

            }

            @Override
            public void stop() {

            }
        };
    }

    /**
     * Clear the environment at the end of the thest
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        this.game = null;
    }

    /**
     * Test if the constructor of the class work well
     */
    @Test
    public void testGame() {
        //assertNotNull(this.game);
    }

    /**
     * Test if the getRules method does not return an null String
     */
    @Test
    public void testGetRules() {
        System.out.println(this.game);
        assertNotNull(this.game.getRules());
    }

    /**
     * Test if the switchCurrent method actually change the current player
     */
    @Test
    public void testSwitchCurrent() {
        Types current = this.game.getCurrentPlayer();
        this.game.switchCurrent();
        assertNotEquals(current, this.game.getCurrentPlayer());
    }

    /**
     * Test if the hasToStop method return true in the good conditions.
     */
    @Test
    public void testHasToStop() {
        this.game.setToStop(true);
        assertTrue(this.game.hasToStop());
    }

}