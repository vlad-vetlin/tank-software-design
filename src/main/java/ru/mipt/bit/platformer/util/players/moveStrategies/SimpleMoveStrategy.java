package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Movable;
import ru.mipt.bit.platformer.util.levels.Level;

public final class SimpleMoveStrategy implements MoveStrategy {

    private final Level level;

    public SimpleMoveStrategy(Level level) {
        this.level = level;
    }

    @Override
    public boolean canMove(Movable movable, GridPoint2 destination) {
        if (
                destination.x < 0 || destination.x >= level.getWidth() ||
                destination.y < 0 || destination.y >= level.getHeight()
        ) {
            return false;
        }

        int distance = Math.abs(destination.x - movable.getCoordinates().x) +
                Math.abs(destination.y - movable.getCoordinates().y);
        return distance == 1 && !level.hasObject(destination);
    }
}
