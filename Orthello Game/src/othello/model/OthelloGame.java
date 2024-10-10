package othello.model;

import othello.events.OthelloEvent;
import othello.events.OthelloListener;

import java.util.*;

import static othello.model.OthelloState.BLACK_NEXT;
import static othello.model.OthelloState.BLACK_WINS;
import static othello.model.OthelloState.DRAW;
import static othello.model.OthelloState.WHITE_NEXT;
import static othello.model.OthelloState.WHITE_WINS;
import static othello.model.Pos.D_4;
import static othello.model.Pos.D_5;
import static othello.model.Pos.E_4;
import static othello.model.Pos.E_5;
import static othello.model.Stone.BLACK;
import static othello.model.Stone.FREE;
import static othello.model.Stone.WHITE;

/**
 * Implementation of <a href="https://de.wikipedia.org/wiki/Othello_(Spiel)">Othello</a>, a classic board game for
 * two players on a 8x8 board.
 */
public class OthelloGame {
    /**
     * Captures the stones on the board.
     * While it is implemented via a {@link Map} it essentially represents a grid where each {@link Pos}
     * contains a {@link Stone} or is not assigned ({@link Stone#FREE}).
     */
    private final Map<Pos, Stone> board;

    /**
     * Represents the current state of the game.
     */
    private OthelloState state;

    /**
     * Stores the number of times no move was possible. If both players cannot make a move anymore, the game is over.
     */
    private int unableToMove = 0;

    // DONE: add field to store listeners
    private final List<OthelloListener> listeners;

    /**
     * Creates a new {@link OthelloGame} instance by creating the {@link #board},
     * initializing the {@link #state} and placing the initial four player stones:
     * <ul>
     *     <li>{@link Pos#D_4 D4}: {@link Stone#WHITE white}</li>
     *     <li>{@link Pos#E_4 E4}: {@link Stone#BLACK black}</li>
     *     <li>{@link Pos#E_5 E5}: {@link Stone#WHITE white}</li>
     *     <li>{@link Pos#D_5 D5}: {@link Stone#BLACK black}</li>
     * </ul>
     */
    public OthelloGame() {
        board = new HashMap<>();
        board.put(D_4, WHITE);
        board.put(E_4, BLACK);
        board.put(E_5, WHITE);
        board.put(D_5, BLACK);
        state = BLACK_NEXT;
        this.listeners = new ArrayList<>();
    }

    /**
     * @return the current state of the game
     */
    public OthelloState getState() {
        return state;
    }

    /**
     * Retrieves the stone at the given position.
     *
     * @param pos The target position
     * @return the stone at the given position or {@link Stone#FREE} if the position is still unassigned ({@code null}).
     * @throws NullPointerException if the given position is null
     */
    public Stone getStone(Pos pos) {
        if (pos == null) {
            throw new NullPointerException("Invalid pos");
        }
        Stone stone = board.get(pos);
        return stone == null ? FREE : stone;
    }

    /**
     * Places the {@link #nextStone() next stone} on the given position.
     *
     * @param pos The target position
     * @throws OthelloException if no stone can be placed at the given position
     */
    public void setNextStone(Pos pos) throws OthelloException {
        Stone stone = nextStone();
        if (pos == null) {
            throw new OthelloException("Invalid pos", null, stone);
        }
        setStone(pos, stone);
        updateGameState();
        // Done: fire "stone set" event
        fireStoneSet(pos);
    }

    /**
     * Places the given stone at the given position by first checking whether this represents a
     * {@link #isValidMove(Pos, Stone) valid move}, updating the {@link #board} and
     * {@link #turnStonesFor(Pos) turning the affected stones}.
     *
     * @param pos   The target position
     * @param stone The target stone
     * @throws OthelloException if the stone cannot be placed at the given position
     */
    public void setStone(Pos pos, Stone stone) throws OthelloException {
        if (isValidMove(pos, stone)) {
            board.put(pos, stone);
            turnStonesFor(pos);
        } else {
            throw new OthelloException("Invalid move", pos, stone);
        }
    }

    /**
     * Starting from the stone at the given position,
     * checks all directions whether stones have to be turned.
     *
     * @param pos The position to check
     */
    private void turnStonesFor(Pos pos) {
        if (pos == null) {
            throw new NullPointerException("Invalid pos");
        }
        Stone stone = getStone(pos);
        for (Direction dir : Direction.ALL) {
            Pos enclosing = enclosingPos(pos, stone, dir);
            if (enclosing != null) {
                Iterator<Pos> it = getDirectionIterator(pos, dir, enclosing);
                while (it.hasNext()) {
                    Pos nextPos = it.next();
                    turnStone(nextPos);
                }
            }
        }
    }

    /**
     * Checks whether the given position is free, i.e., whether it does not contain a player stone.
     *
     * @param pos The position to check
     * @return {@code true} if the position does not contain a stone; {@code false} otherwise
     * @throws NullPointerException if the given position is null
     */
    public boolean isFree(Pos pos) {
        if (pos == null) {
            throw new NullPointerException("Invalid pos");
        }
        return getStone(pos) == FREE;
    }

    /**
     * Checks if the whole board is full (i.e. {@link Pos#ALL all positions} contain stones}).
     *
     * @return {@code true} if the board is full; {@code false} otherwise
     */
    public boolean isBoardFull() {
        for (Pos pos : Pos.ALL) {
            if (isFree(pos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether placing the given stone at the target position represents a valid move.
     * A move is invalid if
     * <ul>
     *     <li>the position is {@code null},</li>
     *     <li>the stone is {@code null},</li>
     *     <li>the stone is not a player stone ({@link Stone#FREE}),</li>
     *     <li>the position is not free, or</li>
     *     <li>starting from the given position, there is no {@link #enclosingPos(Pos, Stone, Direction) enclosing stone} in any direction.</li>
     * </ul>
     * A move is valid if there is at least one {@link #enclosingPos(Pos, Stone, Direction) enclosing stone} in any direction.
     *
     * @param pos   The position where the stone should be placed
     * @param stone The stone that should be placed
     * @return {@code true} if the move is valid; {@code false} if the move is invalid
     */
    public boolean isValidMove(Pos pos, Stone stone) {
        if (pos == null || !isFree(pos) || stone == null || stone == FREE) {
            return false;
        }
        // Is there a direction with enclosed stones of other color?
        for (Direction dir : Direction.ALL) {
            if (enclosingPos(pos, stone, dir) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether there exists any valid move for the given stone by iterating over all positions.
     *
     * @param stone The stone to check
     * @return {@code true} if a move with the given stone is possible; {@code false} otherwise
     */
    public boolean existsValidMoveFor(Stone stone) {
        for (Pos pos : Pos.ALL) {
            if (isValidMove(pos, stone)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Determines the next stone to place based on the current state.
     *
     * @return {@link Stone#BLACK} if {@link OthelloState#BLACK_NEXT black is next} or
     * {@link Stone#WHITE} if {@link OthelloState#WHITE_NEXT white is next}
     * @throws IllegalStateException if the game is in any other state
     */
    public Stone nextStone() {
        if (state == BLACK_NEXT) {
            return BLACK;
        } else if (state == WHITE_NEXT) {
            return WHITE;
        } else {
            throw new IllegalStateException("No next stone if game is over");
        }
    }

    /**
     * Determines whether the previous player was unable to make a move.
     *
     * @return {@code true} if the previous player could not make a move; {@code false} otherwise
     */
    public boolean lastWasUnable() {
        return unableToMove > 0;
    }

    /**
     * Updates the state of the game:
     * <ol>
     *    <li>Checks if the {@link #isBoardFull() board is full}.
     *    If so, the game is over and the {@link #getWinner() winner} is determined and set ({@link #state}).</li>
     *    <li>{@link #switchPlayer() Switches} the player if the board is <b>not</b> full.</li>
     *    <li>Checks if a move is possible.</li>
     *    <li>If {@link #existsValidMoveFor(Stone) a move is possible}, the {@link #unableToMove missed move count} is reset to 0.</li>
     *    <li>If no move is possible, the {@link #unableToMove missed move count} is incremented.</li>
     *    <li>If the {@link #unableToMove missed move count} is 2, the game ends and the {@link #getWinner() winner} is determined and set ({@link #state}).</li>
     *    <li>If the {@link #unableToMove missed move count} is below 2, the player is {@link #switchPlayer() switched} again.</li>
     * </ol>
     */
    private void updateGameState() {
        if (isBoardFull()) {
            state = getWinner();
        } else {
            switchPlayer();
            if (existsValidMoveFor(nextStone())) {
                unableToMove = 0;
            } else {
                unableToMove++;
                if (unableToMove == 2) {
                    state = getWinner();
                } else {
                    switchPlayer();
                }
            }
        }
    }

    /**
     * Determines the winner by counting the stones on all positions.
     * The player with more stones wins, otherwise it is a {@link OthelloState#DRAW}.
     *
     * @return the resulting state
     */
    private OthelloState getWinner() {
        int nWhite = 0;
        int nBlack = 0;
        for (Pos pos : Pos.ALL) {
            Stone stone = getStone(pos);
            if (stone == WHITE) {
                nWhite++;
            } else {
                nBlack++;
            }
        }
        if (nWhite == nBlack) {
            return DRAW;
        } else if (nWhite > nBlack) {
            return WHITE_WINS;
        } else {
            return BLACK_WINS;
        }
    }

    /**
     * Toggles the current player.
     */
    private void switchPlayer() {
        if (state == WHITE_NEXT) {
            state = BLACK_NEXT;
        } else {
            state = WHITE_NEXT;
        }
    }

    /**
     * Turns the stone at the given position.
     *
     * @param pos The position to turn
     * @throws NullPointerException if the position is null
     */
    public void turnStone(Pos pos) {
        if (pos == null) {
            throw new NullPointerException("Invalid pos");
        }
        if (!isFree(pos)) {
            Stone turnedStone = getStone(pos).other();
            board.put(pos, turnedStone);
            // DONE: fire "stone set" event
            fireStoneSet(pos);
        }
    }

    /**
     * Retrieves the other position that - starting from the stone at the given position in the given direction - encloses stones of the opposing player.
     *
     * @param pos   The starting position
     * @param stone The stone at the starting position
     * @param dir   The direction to check
     * @return the next stone of the same player that encloses other stones; {@code null} if no such stone exists
     */
    private Pos enclosingPos(Pos pos, Stone stone, Direction dir) {
        Pos nextPos = pos.next(dir);
        if (nextPos != null) {
            Iterator<Pos> it = getDirectionIterator(nextPos, dir, null);
            while (it.hasNext() && stone.isOther(getStone(nextPos))) {
                nextPos = it.next();
                if (getStone(nextPos) == stone) {
                    return nextPos;
                }
            }
        }
        return null;
    }

    /**
     * Creates an {@link Iterator} that - starting from the given position - yields all the stones
     * in the given direction up until the target stone. If the target stone is {@code null}
     *
     * @param start     The starting position
     * @param direction The direction in which to iterate
     * @param until     The target stone
     * @return a new iterator
     * @throws NullPointerException if the starting position or direction are null
     */
    private Iterator<Pos> getDirectionIterator(final Pos start, final Direction direction, final Pos until) {
        if (start == null || direction == null) {
            throw new NullPointerException("Starting position and direction must not be null");
        }
        return new Iterator<>() {
            private Pos next = start.next(direction);

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public Pos next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Pos current = next;
                next = next.next(direction);
                if (next == null || next.equals(until)) {
                    next = null;
                }
                return current;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove not supported for iterator");
            }
        };
    }

    /**
     * This enables directly setting the properties of the game.
     * <b>Should only be used for testing!</b>
     *
     * @param state        The state to set
     * @param unableToMove The unable moves up until now
     * @param board        The board positions
     */
    void setStateInternal(OthelloState state, int unableToMove, Map<Pos, Stone> board) {
        this.state = state;
        this.unableToMove = unableToMove;
        // since board is final, we just empty the board and add the new positions
        this.board.clear();
        this.board.putAll(board);
    }

    // DONE: add methods to add/remove listener
    public void addGameListener(OthelloListener l){
        listeners.add(l);
    }

    public void removeGameListener(OthelloListener l){
        listeners.remove(l);
    }

    // DONE: add method to fire a "stone set" event
    private void fireStoneSet(Pos pos){
        OthelloEvent event = new OthelloEvent(this, getState(), pos, getStone(pos));

        for (OthelloListener l : listeners) {
            l.stoneSet(event);
        }
    }
}
