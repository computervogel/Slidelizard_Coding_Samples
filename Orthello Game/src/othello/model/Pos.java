package othello.model;

/**
 * Enumeration class for positions on a chessboard.
 * <p>
 * Enumeration values range from <code>A_1</code> to <code>H_8</code>.
 * Each position value stores its column and row index.
 * Contains a set of static methods, e.g., for retrieving position values
 * and testing indices for validity.
 * </p>
 */
public enum Pos {
    A_1('A', 1), B_1('B', 1), C_1('C', 1), D_1('D', 1), E_1('E', 1), F_1('F', 1), G_1('G', 1), H_1('H', 1),
    A_2('A', 2), B_2('B', 2), C_2('C', 2), D_2('D', 2), E_2('E', 2), F_2('F', 2), G_2('G', 2), H_2('H', 2),
    A_3('A', 3), B_3('B', 3), C_3('C', 3), D_3('D', 3), E_3('E', 3), F_3('F', 3), G_3('G', 3), H_3('H', 3),
    A_4('A', 4), B_4('B', 4), C_4('C', 4), D_4('D', 4), E_4('E', 4), F_4('F', 4), G_4('G', 4), H_4('H', 4),
    A_5('A', 5), B_5('B', 5), C_5('C', 5), D_5('D', 5), E_5('E', 5), F_5('F', 5), G_5('G', 5), H_5('H', 5),
    A_6('A', 6), B_6('B', 6), C_6('C', 6), D_6('D', 6), E_6('E', 6), F_6('F', 6), G_6('G', 6), H_6('H', 6),
    A_7('A', 7), B_7('B', 7), C_7('C', 7), D_7('D', 7), E_7('E', 7), F_7('F', 7), G_7('G', 7), H_7('H', 7),
    A_8('A', 8), B_8('B', 8), C_8('C', 8), D_8('D', 8), E_8('E', 8), F_8('F', 8), G_8('G', 8), H_8('H', 8);

    // static -----------------------------------------------------------------

    /**
     * Number of rows in board.
     */
    public static final int N_ROWS = 8;

    /**
     * Number of rows in board.
     */
    public static final int N_COLS = 8;

    /**
     * Provides access to all position values.
     */
    public static final Pos[] ALL = values();

    /**
     * Gets the index number for the character representing the column
     *
     * @param col the character representing the column
     * @return the index number
     * @throws IllegalArgumentException if the column does not exist
     */
    public static int getColIdx(char col) {
        if (col < 'A' || col > 'H') {
            throw new IllegalArgumentException("Invalid column name: " + col);
        }
        return col - 'A' + 1;
    }

    /**
     * Gets the position value for the given row and column numbers.
     *
     * @param row    the row index
     * @param colIdx the number representing the column
     * @return the position value if row and colIdx represent a valid position, null otherwise.
     */
    public static Pos getPos(int row, int colIdx) {
        if (isValidPos(row, colIdx)) {
            return ALL[N_ROWS * (row - 1) + (colIdx - 1)];
        } else {
            return null;
        }
    }


    /**
     * Tests if the provided row and column index numbers represent a valid position in the chessboard.
     *
     * @param row    the row index
     * @param colIdx the number representing the column
     * @return true if row and column index number represent a valid position on the chessboard
     */
    public static boolean isValidPos(int row, int colIdx) {
        return row > 0 && row <= N_ROWS && colIdx > 0 && colIdx <= N_COLS;
    }


    // object -----------------------------------------------------------------

    /**
     * The character representing the column index for the position.
     * Values are from 'A' to 'H'.
     */
    public final char col;
    /**
     * The integer representing the row index for the position.
     * Values are from 1 to 8.
     */
    public final int row;

    /**
     * Constructor initializing column and row indices.
     *
     * @param col the character representing the column index
     * @param row the integer value representing the row index
     */
    Pos(char col, int row) {
        this.col = col;
        this.row = row;
    }

    /**
     * Computes the position next to this position in the given direction.
     *
     * @param direction the direction for the next position
     * @return the next position in given direction;
     * {@code null} when no further position in this direction exists.
     */
    public Pos next(Direction direction) {
        return getPos(this.row + direction.dRow, getColIdx(this.col) + direction.dCol);
    }
}
