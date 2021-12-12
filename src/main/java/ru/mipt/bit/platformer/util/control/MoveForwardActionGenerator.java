package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public final class MoveForwardActionGenerator implements ActionGenerator {
    @Override
    public Action getRecommendation(AbstractMovable movable) {
        return movable.getActionByRotation(movable.getRotation());
    }

    @Override
    public void setNewGameState(Level level) {}
}
