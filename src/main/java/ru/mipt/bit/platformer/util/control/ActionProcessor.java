package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.CanFire;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.players.Action;

/**
 * Adapter в системе портов и адаптеров
 * Application layer
 */
public final class ActionProcessor {
    public static void processAction(AbstractMovable movable) {
        Action action = movable.getActionGenerator().getRecommendation(movable);

        switch (action) {
            case MoveEast:
            case MoveNorth:
            case MoveSouth:
            case MoveWest:
                movable.move(action);
                break;
            case Shoot:
                if (movable instanceof CanFire) {
                    ((CanFire) movable).fire();
                }
        }
    }
}
