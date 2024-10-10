package othello.events;

import java.util.EventListener;

@FunctionalInterface
public interface OthelloListener extends EventListener {
    void stoneSet(OthelloEvent oEvent);
}
