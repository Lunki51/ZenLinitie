package game;

/**
 * An enumeration that represent the different types of pawn that can be placed on a board
 *
 * @author Q.Cosnier
 */
public enum Types {
    BLACK('X'),
    WHITE('O'),
    ZEN('Z');

    private char symbol;

    /**
     * +
     * Create a new type of pawn that can be placed on the board
     *
     * @param symbol the char symbol used to wrote the pawn
     */
    Types(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Return the char symbol used to write this type
     *
     * @return the char symbol of the type
     */
    public char getSymbol() {
        return symbol;
    }
}
