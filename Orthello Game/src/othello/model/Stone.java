package othello.model;

/**
 * Possible states of a position.
 */
public enum Stone {
    /**
     * Position is free.
     */
    FREE,
    /**
     * Positions occupied by first player.
     */
    WHITE,
    /**
     * Position occupied by second player.
     */
    BLACK;

    /**
     * Returns the corresponding other player's stone.
     *
     * @return if this stone is a player stone ({@link #BLACK}, {@link #WHITE}),
     * it returns the corresponding other player's stone; otherwise {@link #FREE} is returned
     */
    public Stone other() {
        if (this == WHITE) {
            return BLACK;
        } else if (this == BLACK) {
            return WHITE;
        } else {
            return FREE;
        }
    }

    /**
     * Checks whether this stone is from a different player than the provided stone.
     * If any of the stones is {@link #FREE}, {@code false} is returned.
     *
     * @param other The other stone to check
     * @return {@code true} if this stone and the provided stone are not {@link #FREE} and from different players;
     * {@code false} otherwise
     */
    public boolean isOther(Stone other) {
        if (this == WHITE) {
            return other == BLACK;
        } else if (this == BLACK) {
            return other == WHITE;
        } else {
            return false;
        }
    }

}
