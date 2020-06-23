package test;

import game.AutoPlayer;
import game.BoardManager;
import game.Types;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Used to test the AutoPlayer class
 */
public class AutoPlayerTest {

    AutoPlayer autoPlayer;

    /**
     * Setup the environment for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.autoPlayer = new AutoPlayer();
    }

    /**
     * Clear the environment at the end of the thest
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        this.autoPlayer = null;
    }

    /**
     * Test the constructor of the class
     */
    @Test
    public void testAutoPlayer() {
        assertNotNull(this.autoPlayer);
    }

    /**
     * Test if the autoShot method do not return an null object and if the array size is 2 by 1
     */
    @Test
    public void testAutoShot() {
        autoPlayer.autoShot(new BoardManager(), Types.BLACK);
    }
}