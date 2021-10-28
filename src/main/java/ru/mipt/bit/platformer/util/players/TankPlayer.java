package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.Movable;
import ru.mipt.bit.platformer.util.RenderableObject;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class TankPlayer implements Movable, RenderableObject {
    private GridPoint2 destinationCoordinates;

    private float rotation;

    private final MoveStrategy moveStrategy;

    private float movementProgress = 1f;

    private GridPoint2 coordinates;

    public TankPlayer(GridPoint2 coordinates, MoveStrategy moveStrategy) {
        this.coordinates = coordinates;
        this.moveStrategy = moveStrategy;

        destinationCoordinates = coordinates;
        rotation = 0f;
    }

    @Override
    public void processMoveToDestination(float deltaTime, float speed) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        if (isEqual(movementProgress, 1f)) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    private void move(GridPoint2 point) {
        // check potential player destination for collision with obstacles
        if (moveStrategy.canMove(this, point)) {
            destinationCoordinates = point;
            movementProgress = 0f;
        }
    }

    private void rotateAndMove(GridPoint2 point, float rotation) {
        if (isEqual(movementProgress, 1f)) {
            move(point);
            this.rotation = rotation;
        }
    }

    @Override
    public void move(Action action) {
        switch (action) {
            case MoveNorth:
                moveUp();
                break;
            case MoveWest:
                moveLeft();
                break;
            case MoveEast:
                moveRight();
                break;
            case MoveSouth:
                moveDown();
                break;
        }
    }

    private void moveUp() {
        rotateAndMove(incrementedY(coordinates), 90f);
    }

    private void moveRight() {
        rotateAndMove(incrementedX(coordinates), 0f);
    }

    private void moveLeft() {
        rotateAndMove(decrementedX(coordinates), -180f);
    }

    private void moveDown() {
        rotateAndMove(decrementedY(coordinates), -90f);
    }

    public float getRotation() {
        return rotation;
    }
}
