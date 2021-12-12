package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;

/**
 * Port в системе портов и адаптеров
 * Application layer
 */
public interface ActionGenerator {
    Action getRecommendation(AbstractMovable movable);

    void setNewGameState(Level level);
}
