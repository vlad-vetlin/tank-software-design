package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.ICanMove;
import ru.mipt.bit.platformer.util.levels.Map;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class SimpleMoveStrategy implements IMoveStrategy {

    private final Map map;

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

    private void rotateAndMove(ICanMove player, GridPoint2 point, float rotation) {
        if (isEqual(player.getMovementProgress(), 1f)) {
            move(player, point);
            player.setRotation(rotation);
        }
    }

    @Override
    public void moveUp(ICanMove player) {
        rotateAndMove(player, incrementedY(player.getCoordinates()), 90f);
    }

    @Override
    public void moveRight(ICanMove player) {
        rotateAndMove(player, incrementedX(player.getCoordinates()), 0f);
    }

    @Override
    public void moveLeft(ICanMove player) {
        rotateAndMove(player, decrementedX(player.getCoordinates()), -180f);
    }

    @Override
    public void moveDown(ICanMove player) {
        rotateAndMove(player, decrementedY(player.getCoordinates()), -90f);
    }

    @Override
    public void processMoveToDestination(ICanMove player, float deltaTime, float speed) {
        float progress = continueProgress(player.getMovementProgress(), deltaTime, speed);
        player.setMovementProgress(progress);
        if (isEqual(progress, 1f)) {
            // record that the player has reached his/her destination
            player.setCoordinates(player.getDestinationCoordinates());
        }
    }
}
