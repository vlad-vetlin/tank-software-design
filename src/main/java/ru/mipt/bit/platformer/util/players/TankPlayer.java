package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.ICanMove;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.players.moveStrategies.IMoveStrategy;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;


public final class TankPlayer extends AbstractPlayer implements ICanMove {
    private GridPoint2 destinationCoordinates;

    private float rotation;

    private IMoveStrategy moveStrategy;

    private float movementProgress = 1f;

    private final TileMovement tileMovement;

    public TankPlayer(Batch batch, Texture texture, GridPoint2 coordinates, TileMovement tileMovement) {
        super(batch, texture, coordinates);

        destinationCoordinates = coordinates;
        rotation = 0f;
        this.tileMovement = tileMovement;
    }

    public void setMoveStrategy(IMoveStrategy strategy) {
        moveStrategy = strategy;
    }

    @Override
    public void processMoveToDestination(float deltaTime, float speed) {
        moveStrategy.processMoveToDestination(this, deltaTime, speed);
    }

    @Override
    public void moveUp() {
        moveStrategy.moveUp(this);
    }

    @Override
    public void moveRight() {
        moveStrategy.moveRight(this);
    }

    @Override
    public void moveLeft() {
        moveStrategy.moveLeft(this);
    }

    @Override
    public void moveDown() {
        moveStrategy.moveDown(this);
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public void setDestinationCoordinates(GridPoint2 point) {
        destinationCoordinates = point;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public void setMovementProgress(float progress) {
        this.movementProgress = progress;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void render() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }
}
