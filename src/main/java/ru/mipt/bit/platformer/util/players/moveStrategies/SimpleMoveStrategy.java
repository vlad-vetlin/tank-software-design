package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.ICanMove;
import ru.mipt.bit.platformer.util.levels.Map;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class SimpleMoveStrategy implements IMoveStrategy {

    private Map map;

    public SimpleMoveStrategy(Map map) {
        this.map = map;
    }

    private void move(ICanMove player, GridPoint2 point) {
        // check potential player destination for collision with obstacles
        if (!map.hasObstacle(point)) {
            player.setDestinationCoordinates(point);
            player.setMovementProgress(0f);
        }
    }

    @Override
    public void moveUp(ICanMove player) {
        if (isEqual(player.getMovementProgress(), 1f)) {
            move(player, incrementedY(player.getCoordinates()));
            player.setRotation(90f);
        }
    }

    @Override
    public void moveRight(ICanMove player) {
        if (isEqual(player.getMovementProgress(), 1f)) {
            move(player, incrementedX(player.getCoordinates()));
            player.setRotation(0f);
        }
    }

    @Override
    public void moveLeft(ICanMove player) {
        if (isEqual(player.getMovementProgress(), 1f)) {
            move(player, decrementedX(player.getCoordinates()));
            player.setRotation(-180f);
        }
    }

    @Override
    public void moveDown(ICanMove player) {
        if (isEqual(player.getMovementProgress(), 1f)) {
            move(player, decrementedY(player.getCoordinates()));
            player.setRotation(-90f);
        }
    }
}
