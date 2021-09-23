package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.players.moveStrategies.IMoveStrategy;

public interface ICanMove {
    void moveUp();

    void moveRight();

    void moveLeft();

    void moveDown();

    float getMovementProgress();

    void setDestinationCoordinates(GridPoint2 point);

    void setCoordinates(GridPoint2 point);

    GridPoint2 getCoordinates();

    GridPoint2 getDestinationCoordinates();

    void setMovementProgress(float progress);

    void setRotation(float rotation);

    void setMoveStrategy(IMoveStrategy strategy);
}
