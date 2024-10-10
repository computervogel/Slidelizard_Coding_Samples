package othello.events;

import othello.model.OthelloGame;
import othello.model.OthelloState;
import othello.model.Pos;
import othello.model.Stone;

import java.util.EventObject;

public class OthelloEvent extends EventObject {
    private final OthelloState state;
    private final Pos pos;
    private final Stone stone;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public OthelloEvent(OthelloGame source, OthelloState state, Pos pos, Stone stone) {
        super(source);
        this.state = state;
        this.pos = pos;
        this.stone = stone;
    }

    public OthelloState getState() {
        return this.state;
    }

    public Pos getPos() {
        return this.pos;
    }

    public Stone getStone() {
        return this.stone;
    }
}
