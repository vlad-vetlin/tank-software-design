package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.Movable;
import ru.mipt.bit.platformer.util.levels.Level;

/**
 * Adapter в системе портов и адаптеров
 * ApplicationLayer
 */
public interface MoveStrategy {
    boolean canMove(AbstractMovable movable, GridPoint2 destination);

    void setLevel(Level level);
}
