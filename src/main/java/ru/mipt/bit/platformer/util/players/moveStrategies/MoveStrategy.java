package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Movable;

public interface MoveStrategy {
    boolean canMove(Movable movable, GridPoint2 destination);
}
