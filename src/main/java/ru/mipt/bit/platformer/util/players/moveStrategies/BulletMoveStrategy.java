package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Movable;
import ru.mipt.bit.platformer.util.levels.Level;

public class BulletMoveStrategy implements MoveStrategy {
    private final Level level;

    public BulletMoveStrategy(Level level) {
        this.level = level;
    }

    @Override
    public boolean canMove(Movable movable, GridPoint2 destination) {
        return !(destination.x < 0 || destination.x >= level.getWidth() ||
            destination.y < 0 || destination.y >= level.getHeight());
    }
}
