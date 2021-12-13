package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.actions.*;
import ru.mipt.bit.platformer.util.control.ActionGenerator;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedY;

public abstract class AbstractMovable extends AbstractObjectWithCoordinates implements Movable, RenderableInMoveObject {
    /**
     * fraction of one
     */
    private float movementProgress = 1f;

    protected final MoveStrategy moveStrategy;

    private final ActionGenerator actionGenerator;

    /**
     * Destination point in which player is moving
     */
    private GridPoint2 destinationCoordinates;

    public AbstractMovable(
            GridPoint2 coordinates,
            float rotation,
            MoveStrategy moveStrategy,
            ActionGenerator actionGenerator
    ) {
        super(coordinates, rotation);
        this.moveStrategy = moveStrategy;

        destinationCoordinates = coordinates;
        this.actionGenerator = actionGenerator;
    }

    public ActionCommand getActionByRotation(float rotation) {
        if (floatEquals(rotation, 90f)) {
            return new MoveUpCommand(this);
        }

        if (floatEquals(rotation, -90f)) {
            return new MoveDownCommand(this);
        }

        if (floatEquals(rotation, 0f)) {
            return new MoveRightCommand(this);
        }

        return new MoveLeftCommand(this);
    }

    @Override
    public void processMoveToDestination(float deltaTime, float speed) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        if (isStopped()) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public void processOneTick(float deltaTime, float speed) {
        generateAction();
        processMoveToDestination(deltaTime, speed);
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    public boolean isStopped() {
        return isEqual(movementProgress, 1f);
    }

    private void generateAction() {
        if (actionGenerator != null) {
            getActionGenerator().getRecommendation(this).exec();
        }
    }

    public ActionGenerator getActionGenerator() {
        return actionGenerator;
    }

    public boolean moveUp() {
        return rotateAndMove(incrementedY(coordinates), 90f);
    }

    public boolean moveRight() {
        return rotateAndMove(incrementedX(coordinates), 0f);
    }

    public boolean moveLeft() {
        return rotateAndMove(decrementedX(coordinates), -180f);
    }

    public boolean moveDown() {
        return rotateAndMove(decrementedY(coordinates), -90f);
    }

    private boolean rotateAndMove(GridPoint2 point, float rotation) {
        if (isEqual(movementProgress, 1f)) {
            this.rotation = rotation;
            return move(point);
        }

        // Если он просто не подвинулся, так как время не пришло, то все хорошо. Проблема именно в невозможности
        // движения в точку
        return true;
    }

    private boolean move(GridPoint2 point) {
        // check potential player destination for collision with obstacles
        if (moveStrategy.canMove(this, point)) {
            destinationCoordinates = point;
            movementProgress = 0f;

            return true;
        }

        return false;
    }
}
