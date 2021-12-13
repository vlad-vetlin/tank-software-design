package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.actions.ActionCommand;
import ru.mipt.bit.platformer.util.levels.Level;

/**
 * Port в системе портов и адаптеров
 * Application layer
 */
public interface ActionGenerator {
    ActionCommand getRecommendation(AbstractMovable movable);

    void setNewGameState(Level level);
}
