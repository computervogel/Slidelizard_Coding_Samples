package othello.model;

/**
 * Base class for errors related to {@link OthelloGame}.
 */
public class OthelloException extends Exception {
    /**
     * The affected {@link Pos position}.
     */
    private final Pos pos;
    /**
     * The placed {@link Stone stone}.
     */
    private final Stone stone;

    /**
     * Creates a new exception with the given message and properties.
     *
     * @param msg   The exception message
     * @param pos   The affected position
     * @param stone The placed stone
     */
    public OthelloException(String msg, Pos pos, Stone stone) {
        super(msg);
        this.pos = pos;
        this.stone = stone;
    }

    /**
     * @return the affected {@link Pos position}
     */
    public Pos getPos() {
        return pos;
    }

    /**
     * @return the placed {@link Stone stone}
     */
    public Stone getStone() {
        return stone;
    }

}
