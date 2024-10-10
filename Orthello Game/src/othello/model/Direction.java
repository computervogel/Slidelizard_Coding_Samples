package othello.model;

/**
 * Enumeration class for directions in a chessboard.
 * Each direction value stores the change of indices to go
 * from a position one step into that direction.
 */
public enum Direction {
    /**
     * North.
     */
    N(-1, 0),
    /**
     * Northwest.
     */
    NW(-1, -1),
    /**
     * West.
     */
    W(0, -1),
    /**
     * Southwest.
     */
    SW(1, -1),
    /**
     * South.
     */
    S(1, 0),
    /**
     * Southeast.
     */
    SE(1, 1),
    /**
     * East.
     */
    E(0, 1),
    /**
     * Northeast.
     */
    NE(-1, 1);

    public static final Direction[] ALL = values();

    /**
     * change of row index
     */
    public final int dRow;
    /**
     * change of column index
     */
    public final int dCol;

    /**
     * Constructor setting change of row and column index
     *
     * @param dRow the change of row index
     * @param dCol the change of column index
     */
    Direction(int dRow, int dCol) {
        this.dRow = dRow;
        this.dCol = dCol;
    }

}
