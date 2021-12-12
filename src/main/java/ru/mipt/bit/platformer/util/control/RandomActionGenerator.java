package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;

import java.util.Random;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public final class RandomActionGenerator implements ActionGenerator {
    private static final Random random = new Random();

    @Override
    public Action getRecommendation(AbstractMovable movable) {
        int action = random.nextInt(5);

        switch (action) {
            case 0:
                return Action.MoveNorth;
            case 1:
                return Action.MoveEast;
            case 2:
                return Action.MoveSouth;
            case 3:
                return Action.MoveWest;
            case 4:
            default:
                return Action.Shoot;
        }
    }

    @Override
    public void setNewGameState(Level level) {}
}
