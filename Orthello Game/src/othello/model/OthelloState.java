package othello.model;

/**
 * Captures the current state of {@link OthelloGame}.
 * The different enum values denote whose turn it is ({@link #WHITE_NEXT}, {@link #BLACK_NEXT}) and
 * whether the game is over ({@link #WHITE_WINS}, {@link #BLACK_WINS}, {@link #DRAW}).
 */
public enum OthelloState {
    WHITE_NEXT,
    BLACK_NEXT,
    WHITE_WINS,
    BLACK_WINS,
    DRAW
}

