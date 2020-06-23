package test;

import console.GameConsole;
import game.BoardManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Used to test the GameConsole class
 */
public class GameConsoleTest {

    GameConsole console;

    /**
     * Setup the environment for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.console = new GameConsole();
    }

    /**
     * Clear the environment at the end of the thest
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        this.console = null;
    }

    /**
     * Test if the constructor of the class does not return null
     */
    @Test
    public void testGameConsole() {
        assertNotNull(this.console);
    }

}